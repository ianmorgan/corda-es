package net.cordaes.contracts.iou.states

import net.corda.core.identity.Party

data class IOU(val lender: Party, val borrower: Party, val amount: Long)
