package net.cordaes.ledger

import net.corda.core.contracts.UniqueIdentifier
import net.cordaes.ledger.event.LedgerEvent


interface VaultEventQueries {

    data class LinearStateQuery(val linearState: UniqueIdentifier)

    fun queryEvents(linearStateQuery: LinearStateQuery): List<LedgerEvent>
}

interface VaultServices : VaultEventQueries {
}

class DefaultVaultServices : VaultServices {

    override fun queryEvents(linearStateQuery: VaultEventQueries.LinearStateQuery): List<LedgerEvent> {
        return emptyList()
    }

}