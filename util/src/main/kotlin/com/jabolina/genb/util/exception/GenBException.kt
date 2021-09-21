package com.jabolina.genb.util.exception

open class GenBException(
    message: String?,
    e: Throwable? = null,
) : RuntimeException(message, e)
