package net.cordaes.ledger.event

import net.corda.core.contracts.UniqueIdentifier

interface LinearStateEvent {
    fun linearId(): UniqueIdentifier
}