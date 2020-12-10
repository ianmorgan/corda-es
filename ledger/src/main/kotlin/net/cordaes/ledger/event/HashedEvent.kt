package net.cordaes.ledger.event

import net.cordaes.crypto.SecureHash

// An event that has hash
interface HashedEvent {
    fun hash(): SecureHash
}

