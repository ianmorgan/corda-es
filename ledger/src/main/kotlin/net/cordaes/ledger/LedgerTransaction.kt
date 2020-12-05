package net.cordaes.ledger

import net.cordaes.crypto.SecureHash
import net.cordaes.event.Event

/**
 * A ledger transaction simply maps a single commands
 * into a resulting set of signed events. Each event must be individually
 * signed and entire transaction (the list) also signed
 */
interface LedgerTransaction {

    fun hash(): SecureHash

    fun request(): Command

    fun events(): List<Event>

}