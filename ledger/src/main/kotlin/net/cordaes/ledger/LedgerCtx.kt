package net.cordaes.ledger

import net.corda.core.node.ServiceHub

/**
 * Inject in one "context" classes with everything we need wired up
 */
interface LedgerCtx {
    fun vaultServices(): VaultServices
    fun serviceHub(): ServiceHub
}

class DefaultLedgerCtx(private val vaultServices: VaultServices,
                       private val serviceHub: ServiceHub) : LedgerCtx {
    override fun serviceHub(): ServiceHub {
        return serviceHub
    }

    override fun vaultServices(): VaultServices {
        return vaultServices
    }
}