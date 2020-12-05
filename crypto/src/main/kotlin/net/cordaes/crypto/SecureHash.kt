package net.cordaes.crypto

// should mirror the SecureHash in Corda as closely as possible?

class SecureHash(private val hash: ByteArray) {

    fun hash(): ByteArray {
        return hash
    }

    override fun toString(): String {
        return "aaa"
    }

    companion object {
        fun hash(data: String): SecureHash {
            return SecureHash(data.toByteArray())
        }
    }
}