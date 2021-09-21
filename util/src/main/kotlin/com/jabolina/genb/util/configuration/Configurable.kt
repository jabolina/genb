package com.jabolina.genb.util.configuration

interface Configurable<C : Configuration> {
    val configuration: C
}
