package net.cordaes.ledger.event

import net.corda.core.identity.Party

/**
 * Marks the event as belonging to a contract, i.e it has one or more participants
 */
interface ContractStateEvent {
    val parties: List<Party>
}