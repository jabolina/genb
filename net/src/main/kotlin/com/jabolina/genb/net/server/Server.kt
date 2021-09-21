package com.jabolina.genb.net.server

import com.jabolina.genb.net.TransportConfiguration
import com.jabolina.genb.net.definition.Message
import com.jabolina.genb.net.exception.TransportException
import com.jabolina.genb.util.async.Scheduled
import com.jabolina.genb.util.async.mapAsync
import io.vertx.kotlin.coroutines.CoroutineVerticle
import java.util.concurrent.ConcurrentLinkedQueue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

typealias Subscriber = suspend (Message) -> Unit

@ExperimentalCoroutinesApi
class Server(override val configuration: TransportConfiguration) : ServerTransport, CoroutineVerticle() {
    private val channel = Channel<Message>()
    private val listeners = ConcurrentLinkedQueue<Subscriber>()
    private val scheduled = Scheduled { act() }

    override suspend fun start() {
        vertx.createNetServer()
            .connectHandler { sock ->
                sock.handler { buff ->
                    launch { channel.send(Message(buff.bytes)) }
                }
            }
            .listen(configuration.server.port) { server ->
                if (!server.succeeded()) {
                    throw TransportException("Failed starting server", server.cause())
                }

                scheduled.schedule(MAX_BATCH_SIZE)
            }
    }

    override suspend fun subscribe(block: Subscriber) {
        listeners.add(block)
    }

    override suspend fun close() {
        scheduled.cancel()
        channel.close()
    }

    private suspend fun act() {
        val message = channel.receive()
        coroutineScope {
            listeners.mapAsync(this) { listener ->
                listener.invoke(message)
            }
        }
    }

    companion object {
        private const val MAX_BATCH_SIZE = 10L
    }
}
