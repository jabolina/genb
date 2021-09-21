package com.jabolina.genb.net.client

import com.jabolina.genb.net.TransportConfiguration
import com.jabolina.genb.net.definition.Message
import com.jabolina.genb.util.configuration.Configurable
import com.jabolina.genb.util.contract.Managed

interface ClientTransport : Configurable<TransportConfiguration>, Managed {
    suspend fun <I: Message> send(message: I)
}
