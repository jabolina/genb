package com.jabolina.genb.util.contract

import kotlin.coroutines.CoroutineContext

interface Context : Managed, CoroutineContext {
    fun isActive(): Boolean
}
