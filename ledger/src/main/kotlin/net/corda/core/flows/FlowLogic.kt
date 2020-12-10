package net.corda.core.flows

abstract class FlowLogic<T> {
    abstract fun call(): T
}