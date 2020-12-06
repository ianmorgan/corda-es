package net.corda.core.node

import net.corda.core.identity.Party

class NodeInfo(party: Party) {
    val legalIdentities: List<Party> = listOf(party)
}