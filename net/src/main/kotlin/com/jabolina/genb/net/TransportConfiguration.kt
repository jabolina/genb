package com.jabolina.genb.net

import com.jabolina.genb.util.configuration.Configuration

class TransportConfiguration(
    val server: ServerConfiguration,
    val client: ClientConfiguration,
) : Configuration

data class ServerConfiguration(
    val port: Int,
) : Configuration

data class ClientConfiguration(
    val poolSize: Int,
) : Configuration
