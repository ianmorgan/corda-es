package net.cordaes.ledger.event.viewers

import net.cordaes.ledger.event.LedgerEvent
import java.io.File


class CanvasViewer {

    fun render(events: List<LedgerEvent>): String {

        return canvas + square
    }

    val canvas = """
  <canvas id="myCanvas" width="2000" height="500" style="border:1px solid #000000;"></canvas>
    """.trimIndent()

    val square = """
        <script>
var canvas = document.getElementById("myCanvas");
var ctx = canvas.getContext("2d");
ctx.fillStyle = "#FF0000";
ctx.fillRect(0,0,150,75);

        var border = "#808080";
        var font = "16px Arial";
        var borderWidth = 3;
        
        var eventBlockWidth = 400;
        var eventBlockSpacing = 50;


        ctx.strokeStyle=border;
        //ctx.lineWidth = 5;
        //ctx.strokeRect(200,200,300,200);
        
        ctx.font = font;
        ctx.moveTo(220,220);
        //ctx.fillText("Hello World", 220, 220);
        
        function eventBlockX(index) {
           return (eventBlockWidth+eventBlockSpacing)*index + 50;
        }
        
        function drawEvent(ctx, index, name, participants) {
            var x = eventBlockX(index);
            var y = 100;
            ctx.strokeStyle = border;
            ctx.lineWidth = borderWidth;
            ctx.strokeRect(x, y, eventBlockWidth, 200);

            ctx.font = font;
            ctx.fillStyle = "black";
            ctx.textAlign = "left";
            ctx.fillText(name, x + 20, y + 30);
            
            for (i = 0, len = participants.length, text = ""; i < len; i++) {
            
                  ctx.fillStyle = "#FF0000";
                  ctx.fillRect(x + eventBlockWidth - 100 - (i * 100), y + 5, 90, 30);

            }
        }
        
        drawEvent(ctx, 0, "IOUIssued", ["Alice","Bob"]);
        drawEvent(ctx, 1, "IOUPaymentRecorded", ["Alice","Bob"]);

</script>
    """.trimIndent()


}

fun main(args: Array<String>) {
    val f = File("demo.html")

    val header = """
        <!DOCTYPE html>
<html>
<body>

    """.trimIndent()

    val footer = """
        </body>
</html>
    """.trimIndent()

    f.writeText(header + CanvasViewer().render(emptyList()) + footer)
}

