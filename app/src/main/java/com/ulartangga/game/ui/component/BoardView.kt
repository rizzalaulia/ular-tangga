package com.ulartangga.game.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp
import com.ulartangga.game.domain.model.Board
import com.ulartangga.game.domain.model.Player
import com.ulartangga.game.domain.model.TokenType
import com.ulartangga.game.ui.theme.*

@Composable
fun BoardView(
    board: Board,
    players: List<Player>,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = modifier.aspectRatio(1f)) {
        val tileW = size.width / 10
        val tileH = size.height / 10

        fun tileX(col: Int) = col * tileW
        fun tileY(row: Int) = (9 - row) * tileH
        fun tileCX(col: Int) = tileX(col) + tileW / 2
        fun tileCY(row: Int) = tileY(row) + tileH / 2

        // ─── KOTAK PAPAN ───
        board.tiles.forEach { tile ->
            val x = tileX(tile.col); val y = tileY(tile.row)
            drawRect(color = if (tile.number % 2 == 0) TileEven else TileOdd,
                topLeft = Offset(x, y), size = Size(tileW, tileH))
            drawRect(color = Color.Gray.copy(alpha = 0.25f), topLeft = Offset(x, y),
                size = Size(tileW, tileH), style = Stroke(width = 0.5f))
            val numText = textMeasurer.measure("${tile.number}", TextStyle(fontSize = 7.sp))
            drawText(numText, topLeft = Offset(x + 2f, y + 1f))
        }

        // ─── TANGGA ───
        board.ladders.forEach { ladder ->
            val bottom = board.tileAt(ladder.bottom)
            val top = board.tileAt(ladder.top)
            val bx = tileCX(bottom.col); val by = tileCY(bottom.row)
            val tx = tileCX(top.col); val ty = tileCY(top.row)
            val dx = tx - bx; val dy = ty - by
            val dist = kotlin.math.sqrt(dx * dx + dy * dy)
            if (dist < 1f) return@forEach
            val px = -dy / dist; val py = dx / dist
            val hw = tileW * 0.16f
            val stemW = tileW * 0.045f
            val rungs = (dist / (tileW * 0.4f)).coerceIn(3.0f, 15.0f).toInt()

            drawLine(LadderBrown, Offset(bx - px * hw, by - py * hw),
                Offset(tx - px * hw, ty - py * hw), stemW)
            drawLine(LadderBrown, Offset(bx + px * hw, by + py * hw),
                Offset(tx + px * hw, ty + py * hw), stemW)
            for (i in 1 until rungs) {
                val t = i.toFloat() / rungs
                val rx = bx + dx * t; val ry = by + dy * t
                drawLine(LadderBrown, Offset(rx - px * hw, ry - py * hw),
                    Offset(rx + px * hw, ry + py * hw), stemW)
            }
        }

        // ─── ULAR CARTOON ───
        board.snakes.forEach { snake ->
            drawCartoonSnake(snake.head, snake.tail, tileW, tileH, board)
        }

        // ─── TOKEN PEMAIN (emoji) ───
        players.forEach { player ->
            if (player.position in 1..100) {
                val t = board.tileAt(player.position)
                val cx = tileCX(t.col); val cy = tileCY(t.row)
                val sameTile = players.count { it.position == player.position }
                val myIdx = players.filter { it.position == player.position }.indexOf(player)
                val offX = when { sameTile == 1 -> 0f; myIdx == 0 -> -tileW*0.2f; myIdx == 1 -> tileW*0.2f; myIdx == 2 -> -tileW*0.2f; else -> tileW*0.2f }
                val offY = if (sameTile <= 2) -tileH*0.2f else tileH*0.2f

                val emoji = TokenType.entries[player.tokenIndex].emoji
                val toDraw = if (player.isAI) "${emoji}\uD83E\uDD16" else emoji
                val tokenText = textMeasurer.measure(toDraw, TextStyle(fontSize = (tileW / size.width * 50).sp))
                drawText(tokenText, topLeft = Offset(
                    cx - tokenText.size.width / 2 + offX,
                    cy - tokenText.size.height / 2 + offY
                ))
            }
        }
    }
}

private fun DrawScope.drawCartoonSnake(
    headNum: Int, tailNum: Int,
    tileW: Float, tileH: Float,
    board: Board
) {
    val headTile = board.tileAt(headNum)
    val tailTile = board.tileAt(tailNum)

    val hx = headTile.col * tileW + tileW / 2
    val hy = (9 - headTile.row) * tileH + tileH / 2
    val tx = tailTile.col * tileW + tileW / 2
    val ty = (9 - tailTile.row) * tileH + tileH / 2

    val dx = tx - hx; val dy = ty - hy
    val dist = kotlin.math.sqrt(dx * dx + dy * dy)
    if (dist < 1f) return

    val perpX = -dy / dist; val perpY = dx / dist
    val halfWidth = tileW * 0.12f

    // Badan zigzag
    val bodyPath = Path()
    val segCount = (dist / (tileW * 0.6f)).coerceIn(4.0f, 10.0f).toInt()
    bodyPath.moveTo(hx, hy)
    for (i in 1..segCount) {
        val t = i.toFloat() / segCount
        val bx = hx + dx * t; val by = hy + dy * t
        val wave = if (i % 2 == 0) halfWidth * 0.6f else -halfWidth * 0.6f
        bodyPath.lineTo(bx + perpX * wave, by + perpY * wave)
    }
    bodyPath.lineTo(tx, ty)

    drawPath(bodyPath, color = Color(0xFFBF360C).copy(alpha = 0.3f),
        style = Stroke(width = halfWidth * 2.2f, cap = StrokeCap.Round, join = StrokeJoin.Round))
    drawPath(bodyPath, color = SnakeRed,
        style = Stroke(width = halfWidth * 1.8f, cap = StrokeCap.Round, join = StrokeJoin.Round))
    drawPath(bodyPath, color = Color(0xFFD84315),
        style = Stroke(width = halfWidth * 0.6f, cap = StrokeCap.Round, join = StrokeJoin.Round))

    // Kepala
    val headAngle = kotlin.math.atan2(dy, dx).toFloat()
    val headR = halfWidth * 1.3f
    val headCX = hx + kotlin.math.cos(headAngle) * headR * 0.5f
    val headCY = hy + kotlin.math.sin(headAngle) * headR * 0.5f
    drawCircle(SnakeRed, headR, Offset(headCX, headCY))

    // Mata
    val eyeOff = headR * 0.6f
    val ea1 = headAngle + 0.7f; val ea2 = headAngle - 0.7f
    val eyeR = headR * 0.3f
    drawCircle(Color.White, eyeR, Offset(headCX + kotlin.math.cos(ea1)*eyeOff, headCY + kotlin.math.sin(ea1)*eyeOff))
    drawCircle(Color.White, eyeR, Offset(headCX + kotlin.math.cos(ea2)*eyeOff, headCY + kotlin.math.sin(ea2)*eyeOff))
    val pR = eyeR * 0.5f
    drawCircle(Color.Black, pR, Offset(headCX + kotlin.math.cos(ea1)*eyeOff*1.15f, headCY + kotlin.math.sin(ea1)*eyeOff*1.15f))
    drawCircle(Color.Black, pR, Offset(headCX + kotlin.math.cos(ea2)*eyeOff*1.15f, headCY + kotlin.math.sin(ea2)*eyeOff*1.15f))

    // Lidah bercabang
    val tongueLen = headR * 0.8f
    val tipX = headCX + kotlin.math.cos(headAngle) * (headR + tongueLen)
    val tipY = headCY + kotlin.math.sin(headAngle) * (headR + tongueLen)
    val baseX = headCX + kotlin.math.cos(headAngle) * headR
    val baseY = headCY + kotlin.math.sin(headAngle) * headR
    drawLine(Color(0xFFE53935), Offset(baseX, baseY), Offset(tipX, tipY), 1.5f)
    val forkLen = tongueLen * 0.5f
    val f1 = headAngle + 0.35f; val f2 = headAngle - 0.35f
    drawLine(Color(0xFFE53935), Offset(tipX, tipY),
        Offset(tipX + kotlin.math.cos(f1)*forkLen, tipY + kotlin.math.sin(f1)*forkLen), 1.5f)
    drawLine(Color(0xFFE53935), Offset(tipX, tipY),
        Offset(tipX + kotlin.math.cos(f2)*forkLen, tipY + kotlin.math.sin(f2)*forkLen), 1.5f)
}
