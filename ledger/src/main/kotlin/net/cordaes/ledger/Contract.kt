package net.cordaes.ledger

interface Contract {
    fun verify(ctx: LedgerCtx, txn: LedgerTransaction)
}