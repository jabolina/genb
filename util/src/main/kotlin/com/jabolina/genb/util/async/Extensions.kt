package com.jabolina.genb.util.async

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

fun <E, R> Collection<E>.mapAsync(scope: CoroutineScope, map: suspend (E) -> R): List<Deferred<R>> =
    map { scope.async { map(it) } }
