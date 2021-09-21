package com.jabolina.genb.net.server

import com.jabolina.genb.net.TransportConfiguration
import com.jabolina.genb.net.definition.Message
import com.jabolina.genb.util.configuration.Configurable
import com.jabolina.genb.util.contract.Managed

interface ServerTransport : Configurable<TransportConfiguration>, Managed {
    suspend fun subscribe(block: suspend (Message) -> Unit)
}
