package com.jabolina.genb.net.exception

import com.jabolina.genb.util.exception.GenBException

open class TransportException(
    message: String?,
    e: Throwable? = null,
) : GenBException(message, e)
