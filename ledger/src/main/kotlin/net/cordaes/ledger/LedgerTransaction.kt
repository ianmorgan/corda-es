package net.cordaes.ledger

import net.cordaes.crypto.SecureHash
import net.cordaes.event.Event

/**
 * A ledger transaction simply maps a single commands
 * into a resulting set of signed events. Each event must be individually
 * signed and entire transaction (the list) also signed
 */
interface LedgerTransaction {

    val hash: SecureHash

    val command: Command

    val events: List<Event>

}

class SimpleTransactionBuilder {
    data class State(val request: Command)

    //fun build
}

class SimpleLedgerTransaction(override val hash: SecureHash = SecureHash("nothing".toByteArray()),
                               override val command: Command = NoopCommand(),
                               override val events: List<Event> = emptyList()) : LedgerTransaction {

}