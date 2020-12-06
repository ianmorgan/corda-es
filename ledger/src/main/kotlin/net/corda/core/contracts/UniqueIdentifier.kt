package net.corda.core.contracts

import java.util.*

// Copied from Corda open source
data class UniqueIdentifier @JvmOverloads constructor(val externalId: String? = null, val id: UUID = UUID.randomUUID()) : Comparable<UniqueIdentifier> {
    override fun toString(): String = if (externalId != null) "${externalId}_$id" else id.toString()

    companion object {
        /** Helper function for unit tests where the UUID needs to be manually initialised for consistency. */
        fun fromString(name: String): UniqueIdentifier = UniqueIdentifier(null, UUID.fromString(name))
    }

    override fun compareTo(other: UniqueIdentifier): Int = id.compareTo(other.id)

    override fun equals(other: Any?): Boolean {
        return if (other is UniqueIdentifier)
            id == other.id
        else
            false
    }

    override fun hashCode(): Int = id.hashCode()
}