package net.cordaes.event

import net.cordaes.crypto.SecureHash

interface HashedEvent {
    fun hash() : SecureHash
}