package net.cordaes.ledger.event.viewers

import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.Party
import net.cordaes.ledger.event.IssuingEvent
import net.cordaes.ledger.event.LedgerEvent
import net.cordaes.ledger.event.TypedPayloadEvent
import java.io.File
import java.lang.StringBuilder


class CanvasViewer {

    fun render(events: List<LedgerEvent>): String {

        val sb = StringBuilder()
        var index = 0
        events.forEach {
            sb.append(eventToDrawEventBox(it, index++))
            sb.append("\n")
        }

        return canvas +
                "<script>" +
                constants +
                roundedRect +
                drawEventBox +
                drawPage +
                sb.toString() +
                "</script>"
    }

    private fun eventToDrawEventBox(ev: LedgerEvent, index: Int): String {
        val sb = StringBuilder()
        val parties = ev.participants
                .sortedBy { it.name.organisation }
                .reversed()
                .joinToString { "\"${it.name.organisation}\"" }

        sb.append("drawEventBox(ctx, ${index}, \"${ev.javaClass.simpleName}\", [ $parties], [[\"amount\", 100]]);\n")

        return sb.toString()
    }

    val canvas = """
  <canvas id="myCanvas" width="2000" height="500" style="border:1px solid #000000;"></canvas>
    """.trimIndent()

    val constants = """
        /* make a constant for everything */ 
        var border = "#808080";
        var font = "16px Arial";
        var borderWidth = 3;
        
        var participantFont = "12px Arial"
        var eventBlockWidth = 400;
        var eventBlockSpacing = 50;
    """.trimIndent()

    val drawEventBox = """
        function eventBlockX(index) {
           return (eventBlockWidth+eventBlockSpacing)*index + 50;
        }
        
        function drawEventBox(ctx, index, name, participants, attributes) {
            var x = eventBlockX(index);
            var y = 100;
            ctx.strokeStyle = border;
            ctx.lineWidth = borderWidth;
            ctx.strokeRect(x, y, eventBlockWidth, 200);

            ctx.font = font;
            ctx.fillStyle = "green";
            ctx.textAlign = "left";
            ctx.fillText(name, x + 20, y + 30);
            
            for (i = 0, len = participants.length, text = ""; i < len; i++) {
                var px = x + eventBlockWidth - 100 - (i * 100);
                var py = y + 5;
                ctx.fillStyle = "#AAA";
                //ctx.fillRect(px , py, 90, 30);
                 
                ctx.beginPath();
                ctx.roundedRectangle(px, py, 90, 30, 3);
                ctx.stroke();
                ctx.fill();
                
                ctx.font = participantFont;
                ctx.fillStyle = "black";
                ctx.textAlign = "left";
                ctx.fillText(participants[i], px + 5, py + 15);
            }
        }
    """.trimIndent()

    val drawPage = """
        var canvas = document.getElementById("myCanvas");
        var ctx = canvas.getContext("2d");

        ctx.strokeStyle=border;

        ctx.font = font;
        ctx.moveTo(220,220);
    """.trimIndent()

    val drawEvents = """
 
        drawEventBox(ctx, 0, "IOUIssued", ["Alice","Bob"], [["amount", 100]]);
        drawEventBox(ctx, 1, "IOUPaymentRecorded", ["Alice","Bob"],[]);

    """.trimIndent()

    // from https://newfivefour.com/javascript-canvas-rounded-rectangle.html
    val roundedRect = """
        CanvasRenderingContext2D.prototype.roundedRectangle = function(x, y, width, height, rounded) {
            const radiansInCircle = 2 * Math.PI
            const halfRadians = (2 * Math.PI)/2
            const quarterRadians = (2 * Math.PI)/4  
  
            // top left arc
            this.arc(rounded + x, rounded + y, rounded, -quarterRadians, halfRadians, true)
              
            // line from top left to bottom left
            this.lineTo(x, y + height - rounded)
            
            // bottom left arc  
            this.arc(rounded + x, height - rounded + y, rounded, halfRadians, quarterRadians, true)  
  
            // line from bottom left to bottom right
            this.lineTo(x + width - rounded, y + height)

            // bottom right arc
            this.arc(x + width - rounded, y + height - rounded, rounded, quarterRadians, 0, true)  
  
            // line from bottom right to top right
            this.lineTo(x + width, y + rounded)  

            // top right arc
            this.arc(x + width - rounded, y + rounded, rounded, 0, -quarterRadians, true)  
  
            // line from top right to top left
            this.lineTo(x + rounded, y)  
        };
    """.trimIndent()

    companion object {
        fun renderToHtlm(f: File, events: List<LedgerEvent>) {
            val f = File("demo.html")

            val header = """
                <!DOCTYPE html>
                <html>
                <body>""".trimIndent()

            val footer = """
                </body>
                </html>""".trimIndent()

            f.writeText(header + CanvasViewer().render(events) + footer)
        }
    }

}

data class IOU(val amount: Long = 100, val lender: Party = Party("Alice"), val borrower: Party = Party("Bob"))
class IOUIssued(linearId: UniqueIdentifier = UniqueIdentifier(),
                private val iou: IOU) : LedgerEvent, IssuingEvent,
        TypedPayloadEvent<IOU> {

    override val contactInstanceId: ByteArray = linearId.toString().toByteArray()

    override val participants: List<Party> = listOf(iou.lender, iou.borrower)

    override fun payload(): IOU {
        return iou
    }
}

data class IOUPayment(val amount: Long = 100, val payee: Party = Party("Bob"), val recipient: Party = Party("Alice"))
class IOUPaid(linearId: UniqueIdentifier = UniqueIdentifier(),
              private val payment: IOUPayment) : LedgerEvent, IssuingEvent,
        TypedPayloadEvent<IOUPayment> {

    override val contactInstanceId: ByteArray = linearId.toString().toByteArray()

    override val participants: List<Party> = listOf(payment.payee, payment.recipient)

    override fun payload(): IOUPayment {
        return payment
    }
}

data class IOUTransfer(val currentLender: Party = Party("Alice"), val newLender: Party = Party("Charlie"))
class IOUTransfered(linearId: UniqueIdentifier = UniqueIdentifier(),
                    private val transfer: IOUTransfer) : LedgerEvent, IssuingEvent,
        TypedPayloadEvent<IOUTransfer> {

    override val contactInstanceId: ByteArray = linearId.toString().toByteArray()

    override val participants: List<Party> = listOf(transfer.currentLender, transfer.newLender)

    override fun payload(): IOUTransfer {
        return transfer
    }
}

fun main(args: Array<String>) {
    val f = File("demo.html")
    val events = listOf(IOUIssued(iou = IOU()),
            IOUPaid(payment = IOUPayment()),
            IOUTransfered(transfer = IOUTransfer())) as List<LedgerEvent>
    CanvasViewer.renderToHtlm(f, events)
}

