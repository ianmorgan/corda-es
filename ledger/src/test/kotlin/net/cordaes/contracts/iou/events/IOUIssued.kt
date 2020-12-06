package net.cordaes.contracts.iou.events

import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.Party
import net.cordaes.contracts.iou.states.IOU
import net.cordaes.ledger.event.ContractStateEvent
import net.cordaes.ledger.event.LedgerEvent
import net.cordaes.ledger.event.LinearStateEvent
import net.cordaes.ledger.event.TypedPayloadEvent

/**
 * Represents issuance of an IOU to the ledger.
 */
class IOUIssued(private val linearId: UniqueIdentifier,
                private val iou: IOU) : LedgerEvent, ContractStateEvent, LinearStateEvent, TypedPayloadEvent<IOU> {

    override fun linearId(): UniqueIdentifier {
        return linearId
    }

    override fun parties(): List<Party> {
        return listOf(iou.lender, iou.borrower)
    }

    override fun payload(): IOU {
        return iou
    }

}