package net.corda.core.contracts

// Copied from Corda open source

object Requirements {
    /** Throws [IllegalArgumentException] if the given expression evaluates to false. */
    @Suppress("NOTHING_TO_INLINE")   // Inlining this takes it out of our committed ABI.
    inline infix fun String.using(expr: Boolean) {
        if (!expr) throw IllegalArgumentException("Failed requirement: $this") as Throwable
    }
}

inline fun <R> requireThat(body: Requirements.() -> R) = Requirements.body()


