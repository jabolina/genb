package com.jabolina.genb.net.client

import com.jabolina.genb.net.TransportConfiguration
import com.jabolina.genb.net.definition.Message
import com.jabolina.genb.net.exception.TransportException
import com.jabolina.genb.util.async.mapAsync
import io.vertx.core.buffer.Buffer
import io.vertx.core.net.NetSocket
import io.vertx.core.net.SocketAddress
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.await
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue
import kotlinx.coroutines.coroutineScope

class Client(override val configuration: TransportConfiguration) : ClientTransport, CoroutineVerticle() {
    private val pool = ConcurrentHashMap<SocketAddress, ConcurrentLinkedQueue<NetSocket>>()

    private suspend fun connect(address: SocketAddress): NetSocket {
        return vertx.createNetClient()
            .connect(address)
            .onFailure { TransportException("Failed connecting $address", it) }
            .await()
    }

    private suspend fun resolveConnection(address: SocketAddress, block: suspend (NetSocket) -> Unit) {
        val connection = pool[address]?.poll() ?: connect(address)
        block(connection)
        pool.getOrPut(address) { ConcurrentLinkedQueue<NetSocket>() }.apply {
            if (size < configuration.client.poolSize) {
                add(connection)
            }
        }
    }

    override suspend fun <I : Message> send(message: I) {
        resolveConnection(message.destination) { sock ->
            sock.write(Buffer.buffer(message.content))
        }
    }

    override suspend fun close(): Unit = coroutineScope {
        pool.entries.mapAsync(this) { (_, sockets) ->
            sockets.mapAsync(this) { it.close().await() }
        }
    }
}
