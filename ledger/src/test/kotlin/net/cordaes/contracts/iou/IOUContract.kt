package net.cordaes.contracts.iou

import net.cordaes.contracts.iou.states.IOU
import net.cordaes.ledger.Command
import net.cordaes.ledger.Contract
import net.cordaes.ledger.LedgerTransaction

class IOUContract : Contract {
    override fun verify(txn: LedgerTransaction) {

        when (txn.request()) {
            is IssueCommand -> {
                // check no one has issued this
                // create the event




            }
        }


    }

}

/*
 Issue a new IOU
 */
class IssueCommand(val iou: IOU) : Command

/*
 Transfer ownership
 */