package net.cordaes.ledger.event

import net.corda.core.identity.Party

/**
 * Marks the event as belonging to a contract, i.e it has one or more parties
 */
interface ContractStateEvent {
    fun parties(): List<Party>
}