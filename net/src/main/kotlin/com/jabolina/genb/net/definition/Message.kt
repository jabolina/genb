package com.jabolina.genb.net.definition

import io.vertx.core.net.SocketAddress

open class Message(
    val content: ByteArray,
    val destination: SocketAddress,
)
