function drawEvent(ctx, index, name, participants, attributes) {
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