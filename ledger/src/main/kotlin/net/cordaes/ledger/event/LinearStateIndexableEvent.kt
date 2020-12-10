package net.cordaes.ledger.event

import net.corda.core.contracts.UniqueIdentifier

/**
 * Indicates that this event stream is indexable
 */
interface LinearStateIndexableEvent {
    val linearId: UniqueIdentifier
}