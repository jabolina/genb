package com.jabolina.genb.util.async

import kotlinx.coroutines.*
import java.util.concurrent.Executors

class Scheduled(val block: suspend () -> Unit) : CoroutineScope {
    private val job = Job()
    private val executor = Executors.newSingleThreadExecutor()
    override val coroutineContext = job + executor.asCoroutineDispatcher()

    fun cancel() {
        job.cancel()
        executor.shutdown()
    }

    fun schedule(delay: Long) = launch {
        while (isActive) {
            block()
            delay(delay)
        }
    }
}