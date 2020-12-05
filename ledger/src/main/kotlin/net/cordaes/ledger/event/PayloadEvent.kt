package net.cordaes.ledger.event


interface PayloadEvent

interface GenericPayloadEvent : PayloadEvent {
    fun payload () : Map<String,Any?>
}

interface TypedPayloadEvent<T> : PayloadEvent {
    fun payload () : T
}