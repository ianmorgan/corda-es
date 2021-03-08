package net.cordaes.iou.contracts.events

import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.Party
import net.cordaes.hashing.Hasher
import net.cordaes.iou.contracts.states.IOU
import net.cordaes.ledger.event.*

/**
 * Represents issuance of an IOU to the ledger.
 */
class IOUIssued(linearId: UniqueIdentifier,
                private val iou: IOU) : LedgerEvent, ContractStateEvent, IssuingEvent,
        TypedPayloadEvent<IOU> {

    override val contactInstanceId: ByteArray = linearId.toString().toByteArray()

    override val participants: List<Party>
        get() {
            return listOf(iou.lender, iou.borrower)
        }

    override fun payload(): IOU {
        return iou
    }

    override val hash: String = Hasher().hash(payload())
}