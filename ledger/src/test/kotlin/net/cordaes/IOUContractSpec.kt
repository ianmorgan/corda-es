package net.cordaes


import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.Party
import net.corda.core.node.DefaultServiceHub
import net.cordaes.iou.contracts.IOUContract
import net.cordaes.iou.contracts.IssueCommand
import net.cordaes.iou.contracts.states.IOU
import net.cordaes.ledger.DefaultLedgerCtx
import net.cordaes.ledger.DefaultVaultServices
import net.cordaes.ledger.LedgerTransaction
import net.cordaes.ledger.SimpleLedgerTransaction
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe


@RunWith(JUnitPlatform::class)
object IOUContractSpec : Spek({
    val alice = Party("Alice")
    val bob = Party("Bob")

    describe("the IOU contract ") {

        it("should do something") {
            val contract = IOUContract()
            val vaultServices = DefaultVaultServices()
            val serviceHub = DefaultServiceHub(alice)
            val ledgerCtx = DefaultLedgerCtx(vaultServices, serviceHub)

            val cmd = IssueCommand(UniqueIdentifier(), IOU(lender = alice, borrower = bob, amount = 10))
            val txn = SimpleLedgerTransaction(command = cmd)

            contract.verify(ledgerCtx, txn)
        }

    }
})