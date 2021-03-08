package net.cordaes.ledger.event

import net.cordaes.hashing.Hasher


interface PayloadEvent

interface GenericPayloadEvent : PayloadEvent {
    fun payload(): Map<String, Any?>
}

interface TypedPayloadEvent<T> : PayloadEvent {
    fun payload(): T

    val hash: String
        get() = Hasher().hash(payload() as Any)
}