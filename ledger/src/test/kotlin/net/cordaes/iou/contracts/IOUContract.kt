package net.cordaes.iou.contracts

import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.contracts.requireThat
import net.cordaes.iou.contracts.events.IOUIssued
import net.cordaes.iou.contracts.states.IOU
import net.cordaes.ledger.*

class IOUContract : Contract {
    override fun verify(ctx: LedgerCtx, txn: LedgerTransaction) {
        val cmd = txn.command
        val validator = IOUContactValidator(ctx, txn)

        when (cmd) {
            is IssueCommand -> {
                requireThat {
                    "There cannot be an IOU with this linear ID" using validator.noExistingIOU(cmd)
                    "I am the lender" using validator.isLenderParty(cmd)
                }

                val ev = IOUIssued(cmd.linearId, cmd.iou)

                // sign the event
                // store the signature
            }
        }

    }
}

class IOUContactValidator(private val ctx: LedgerCtx, private val txn: LedgerTransaction) {
    fun noExistingIOU(cmd: IssueCommand): Boolean {
        return ctx.vaultServices().queryEvents(VaultEventQueries.LinearStateQuery(cmd.linearId)).isEmpty()
    }

    fun isLenderParty(cmd: IssueCommand): Boolean {
        return ctx.serviceHub().myInfo.legalIdentities.contains(cmd.iou.lender)
    }
}

/*
 Issue a new IOU
 */
class IssueCommand(val linearId: UniqueIdentifier, val iou: IOU) : Command

/*
 Transfer ownership
 */