package net.cordaes.iou.contracts.states

import net.corda.core.identity.Party

data class IOU(val lender: Party, val borrower: Party, val amount: Long)
