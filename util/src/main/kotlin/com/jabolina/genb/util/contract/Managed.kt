package com.jabolina.genb.util.contract

interface Managed {
    suspend fun close()
}
