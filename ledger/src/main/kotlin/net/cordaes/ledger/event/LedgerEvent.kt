package net.cordaes.ledger.event

import net.corda.core.identity.Party

// everything is a ledger event
interface LedgerEvent {
    val participants: List<Party>
}