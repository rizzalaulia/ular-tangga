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
import com.ulartangga.game.ui.theme.*

/**
 * Papan permainan ular tangga 10x10 — zigzag layout.
 * Ular: badan tebal zigzag + kepala segitiga + mata.
 * Tangga: dua tiang vertikal + anak tangga horizontal.
 */
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
        fun tileY(row: Int) = (9 - row) * tileH  // row 0 = bawah
        fun tileCX(col: Int) = tileX(col) + tileW / 2
        fun tileCY(row: Int) = tileY(row) + tileH / 2

        // ─── KOTAK PAPAN ───
        board.tiles.forEach { tile ->
            val x = tileX(tile.col)
            val y = tileY(tile.row)

            drawRect(
                color = if (tile.number % 2 == 0) TileEven else TileOdd,
                topLeft = Offset(x, y),
                size = Size(tileW, tileH)
            )
            drawRect(
                color = Color.Gray.copy(alpha = 0.25f),
                topLeft = Offset(x, y),
                size = Size(tileW, tileH),
                style = Stroke(width = 0.5f)
            )
            // Nomor kotak
            val numText = textMeasurer.measure("${tile.number}", TextStyle(fontSize = 7.sp))
            drawText(numText, topLeft = Offset(x + 2f, y + 1f))
        }

        // ─── ULAR (badan zigzag tebal + kepala segitiga + mata) ───
        board.snakes.forEach { snake ->
            val head = board.tileAt(snake.head)
            val tail = board.tileAt(snake.tail)

            val hx = tileCX(head.col)
            val hy = tileCY(head.row)
            val tx = tileCX(tail.col)
            val ty = tileCY(tail.row)

            val dx = tx - hx
            val dy = ty - hy
            val dist = kotlin.math.sqrt(dx * dx + dy * dy)

            if (dist < 1f) return@forEach

            // Badan ular: path zigzag tebal
            val bodyPath = Path()
            val perpX = -dy / dist  // perpendicular untuk zigzag
            val perpY = dx / dist
            val amplitude = tileW * 0.25f  // lebar zigzag
            val segments = (dist / (tileW * 0.5f)).coerceIn(3f, 12f).toInt()

            bodyPath.moveTo(hx, hy)
            for (i in 1..segments) {
                val t = i.toFloat() / segments
                val bx = hx + dx * t
                val by = hy + dy * t
                val zigzag = if (i % 2 == 0) amplitude else -amplitude
                bodyPath.lineTo(bx + perpX * zigzag, by + perpY * zigzag)
            }
            bodyPath.lineTo(tx, ty)

            // Bayangan badan
            drawPath(bodyPath, color = SnakeRed.copy(alpha = 0.25f),
                style = Stroke(width = tileW * 0.18f, cap = StrokeCap.Round, join = StrokeJoin.Round))
            // Badan utama
            drawPath(bodyPath, color = SnakeRed,
                style = Stroke(width = tileW * 0.12f, cap = StrokeCap.Round, join = StrokeJoin.Round))
            // Garis tengah (detail)
            drawPath(bodyPath, color = SnakeRed.copy(alpha = 0.5f),
                style = Stroke(width = tileW * 0.04f, cap = StrokeCap.Round, join = StrokeJoin.Round))

            // Kepala ular — segitiga di ujung atas
            val headSize = tileW * 0.22f
            val headAngle = kotlin.math.atan2(dy, dx)
            val headPath = Path().apply {
                // Titik puncak segitiga (arah maju)
                val tipX = hx + kotlin.math.cos(headAngle).toFloat() * headSize * 0.4f
                val tipY = hy + kotlin.math.sin(headAngle).toFloat() * headSize * 0.4f
                // Dua sisi segitiga
                val a1 = headAngle + Math.PI.toFloat() * 0.6f
                val a2 = headAngle - Math.PI.toFloat() * 0.6f
                moveTo(tipX, tipY)
                lineTo(
                    hx + kotlin.math.cos(a1.toDouble()).toFloat() * headSize,
                    hy + kotlin.math.sin(a1.toDouble()).toFloat() * headSize
                )
                lineTo(
                    hx + kotlin.math.cos(a2.toDouble()).toFloat() * headSize,
                    hy + kotlin.math.sin(a2.toDouble()).toFloat() * headSize
                )
                close()
            }

            drawPath(headPath, color = SnakeRed)
            drawPath(headPath, color = Color.White.copy(alpha = 0.3f),
                style = Stroke(width = 1.5f))

            // Mata ular (dua titik putih di sisi kepala)
            val eyeOffset = headSize * 0.5f
            val eyeA1 = headAngle + Math.PI.toFloat() * 0.35f
            val eyeA2 = headAngle - Math.PI.toFloat() * 0.35f
            val eyeR = headSize * 0.15f
            drawCircle(Color.White, eyeR, Offset(
                hx + kotlin.math.cos(eyeA1.toDouble()).toFloat() * eyeOffset,
                hy + kotlin.math.sin(eyeA1.toDouble()).toFloat() * eyeOffset
            ))
            drawCircle(Color.White, eyeR, Offset(
                hx + kotlin.math.cos(eyeA2.toDouble()).toFloat() * eyeOffset,
                hy + kotlin.math.sin(eyeA2.toDouble()).toFloat() * eyeOffset
            ))
            // Pupil
            val pupilR = eyeR * 0.5f
            drawCircle(Color.Black, pupilR, Offset(
                hx + kotlin.math.cos(eyeA1.toDouble()).toFloat() * eyeOffset * 1.1f,
                hy + kotlin.math.sin(eyeA1.toDouble()).toFloat() * eyeOffset * 1.1f
            ))
            drawCircle(Color.Black, pupilR, Offset(
                hx + kotlin.math.cos(eyeA2.toDouble()).toFloat() * eyeOffset * 1.1f,
                hy + kotlin.math.sin(eyeA2.toDouble()).toFloat() * eyeOffset * 1.1f
            ))
        }

        // ─── TANGGA (dua tiang + anak tangga) ───
        board.ladders.forEach { ladder ->
            val bottom = board.tileAt(ladder.bottom)
            val top = board.tileAt(ladder.top)

            val bx = tileCX(bottom.col)
            val by = tileCY(bottom.row)
            val tx = tileCX(top.col)
            val ty = tileCY(top.row)

            val dx = tx - bx
            val dy = ty - by
            val dist = kotlin.math.sqrt(dx * dx + dy * dy)
            if (dist < 1f) return@forEach

            // Vektor tegak lurus (untuk lebar tangga)
            val px = -dy / dist
            val py = dx / dist
            val halfWidth = tileW * 0.18f  // lebar tangga
            val rungCount = (dist / (tileW * 0.35f)).coerceIn(4f, 15f).toInt()

            // Dua tiang vertikal
            val stemWidth = tileW * 0.04f
            drawLine(LadderBrown, Offset(bx - px * halfWidth, by - py * halfWidth),
                Offset(tx - px * halfWidth, ty - py * halfWidth), stemWidth)
            drawLine(LadderBrown, Offset(bx + px * halfWidth, by + py * halfWidth),
                Offset(tx + px * halfWidth, ty + py * halfWidth), stemWidth)

            // Anak tangga (rungs)
            for (i in 1 until rungCount) {
                val t = i.toFloat() / rungCount
                val rx = bx + dx * t
                val ry = by + dy * t
                drawLine(LadderBrown,
                    Offset(rx - px * halfWidth, ry - py * halfWidth),
                    Offset(rx + px * halfWidth, ry + py * halfWidth),
                    stemWidth)
            }

            // Penanda kaki tangga (kotak kecil coklat di bawah)
            drawRect(
                color = LadderBrown.copy(alpha = 0.3f),
                topLeft = Offset(bx - halfWidth, by - halfWidth * 0.5f),
                size = Size(halfWidth * 2, halfWidth)
            )
        }

        // ─── TOKEN PEMAIN ───
        players.forEach { player ->
            if (player.position in 1..100) {
                val t = board.tileAt(player.position)
                val cx = tileCX(t.col)
                val cy = tileCY(t.row)

                val sameTile = players.count { it.position == player.position && it.position > 0 }
                val myIdx = players.filter { it.position == player.position }.indexOf(player)

                val offX = when {
                    sameTile == 1 -> 0f
                    myIdx == 0 -> -tileW * 0.2f
                    myIdx == 1 -> tileW * 0.2f
                    myIdx == 2 -> -tileW * 0.2f
                    else -> tileW * 0.2f
                }
                val offY = when {
                    sameTile <= 2 -> -tileH * 0.25f
                    myIdx >= 2 -> tileH * 0.25f
                    else -> -tileH * 0.25f
                }

                val tokenR = tileW * 0.16f
                val tokenColor = TokenColors[player.tokenIndex]

                // Bayangan token (buat depth)
                drawCircle(Color.Black.copy(alpha = 0.2f), tokenR,
                    Offset(cx + offX + 2f, cy + offY + 2f))
                // Token utama
                drawCircle(tokenColor, tokenR, Offset(cx + offX, cy + offY))
                // Border putih
                drawCircle(Color.White, tokenR, Offset(cx + offX, cy + offY),
                    style = Stroke(width = 2f))
                // Highlight (gradient effect — simple white arc top-left)
                drawCircle(Color.White.copy(alpha = 0.3f), tokenR * 0.6f,
                    Offset(cx + offX - tokenR * 0.2f, cy + offY - tokenR * 0.2f))
            }
        }
    }
}
