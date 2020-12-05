package net.cordaes.ledger

interface Contract {
    fun verify (txn : LedgerTransaction)
}