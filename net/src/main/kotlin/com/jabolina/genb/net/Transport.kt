package com.jabolina.genb.net

import com.jabolina.genb.net.client.ClientTransport
import com.jabolina.genb.net.server.ServerTransport
import com.jabolina.genb.util.contract.Managed

interface Transport : Managed, ServerTransport, ClientTransport {
}
