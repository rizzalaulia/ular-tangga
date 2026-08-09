package com.ulartangga.game.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulartangga.game.domain.model.Board
import com.ulartangga.game.domain.model.Player
import com.ulartangga.game.domain.model.TokenType
import com.ulartangga.game.ui.theme.*

/**
 * Papan permainan ular tangga 10×10.
 * Zigzag layout: baris bawah kiri→kanan, baris berikutnya kanan→kiri, dst.
 */
@Composable
fun BoardView(
    board: Board,
    players: List<Player>,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = modifier.aspectRatio(1f)) {
        val tileWidth = size.width / 10
        val tileHeight = size.height / 10

        // Gambar setiap kotak
        board.tiles.forEach { tile ->
            val x = tile.col * tileWidth
            val y = (9 - tile.row) * tileHeight  // Baris 0 di bawah

            // Warna kotak (genap/ganjil)
            val tileColor = if (tile.number % 2 == 0) TileEven else TileOdd

            // Draw tile background
            drawRect(
                color = tileColor,
                topLeft = Offset(x, y),
                size = androidx.compose.ui.geometry.Size(tileWidth, tileHeight)
            )

            // Draw tile border
            drawRect(
                color = Color.Gray.copy(alpha = 0.3f),
                topLeft = Offset(x, y),
                size = androidx.compose.ui.geometry.Size(tileWidth, tileHeight),
                style = Stroke(width = 0.5f)
            )

            // Draw tile number (kiri atas, kecil)
            val numberText = textMeasurer.measure(
                text = "${tile.number}",
                style = androidx.compose.ui.text.TextStyle(fontSize = 8.sp)
            )
            drawText(
                textLayoutResult = numberText,
                topLeft = Offset(x + 2, y + 1)
            )

            // Draw snake head marker (🟡 di kotak kepala ular)
            if (tile.number in board.snakes.map { it.head }) {
                drawCircle(
                    color = SnakeRed.copy(alpha = 0.5f),
                    radius = tileWidth * 0.15f,
                    center = Offset(x + tileWidth * 0.5f, y + tileHeight * 0.3f)
                )
            }

            // Draw ladder bottom marker (🟤 di kaki tangga)
            if (tile.number in board.ladders.map { it.bottom }) {
                drawRect(
                    color = LadderBrown.copy(alpha = 0.5f),
                    topLeft = Offset(x + tileWidth * 0.35f, y + tileHeight * 0.2f),
                    size = androidx.compose.ui.geometry.Size(tileWidth * 0.3f, tileHeight * 0.3f)
                )
            }
        }

        // Gambar player tokens
        players.forEach { player ->
            if (player.position > 0 && player.position <= 100) {
                val tile = board.tileAt(player.position)
                val x = tile.col * tileWidth
                val y = (9 - tile.row) * tileHeight

                val tokenColor = TokenColors[player.tokenIndex]
                val offsetX = when {
                    // Kalau ada multiple player di kotak sama, offset biar gak tabrakan
                    player.position > 0 -> {
                        val playersAtSameTile = players.count { it.position == player.position && it.position > 0 }
                        val idx = players.filter { it.position == player.position }.indexOf(player)
                        if (playersAtSameTile > 1) {
                            when (idx) {
                                0 -> tileWidth * 0.3f
                                1 -> tileWidth * 0.7f
                                2 -> tileWidth * 0.3f
                                3 -> tileWidth * 0.7f
                                else -> tileWidth * 0.5f
                            }
                        } else {
                            tileWidth * 0.5f
                        }
                    }
                    else -> tileWidth * 0.5f
                }

                val offsetY = if (player.position > 0) {
                    val playersAtSameTile = players.count { it.position == player.position && it.position > 0 }
                    val idx = players.filter { it.position == player.position }.indexOf(player)
                    if (playersAtSameTile > 1 && idx >= 2) {
                        tileHeight * 0.35f
                    } else {
                        tileHeight * 0.15f
                    }
                } else {
                    tileHeight * 0.25f
                }

                drawCircle(
                    color = tokenColor,
                    radius = tileWidth * 0.12f,
                    center = Offset(x + offsetX, y + offsetY)
                )

                // Border token
                drawCircle(
                    color = Color.White,
                    radius = tileWidth * 0.12f,
                    center = Offset(x + offsetX, y + offsetY),
                    style = Stroke(width = 1f)
                )
            }
        }

        // Gambar garis ular (simplified — garis lurus kepala ke ekor)
        board.snakes.forEach { snake ->
            val headTile = board.tileAt(snake.head)
            val tailTile = board.tileAt(snake.tail)

            val headX = headTile.col * tileWidth + tileWidth * 0.5f
            val headY = (9 - headTile.row) * tileHeight + tileHeight * 0.3f
            val tailX = tailTile.col * tileWidth + tileWidth * 0.5f
            val tailY = (9 - tailTile.row) * tileHeight + tileHeight * 0.5f

            val path = Path().apply {
                moveTo(headX, headY)
                // Simple curve head → tail
                val midX = (headX + tailX) / 2
                val midY = (headY + tailY) / 2 + 20f
                quadraticBezierTo(midX, midY, tailX, tailY)
            }

            drawPath(
                path = path,
                color = SnakeRed.copy(alpha = 0.4f),
                style = Stroke(width = 3f)
            )
        }

        // Gambar garis tangga (garis putus-putus kaki ke puncak)
        board.ladders.forEach { ladder ->
            val bottomTile = board.tileAt(ladder.bottom)
            val topTile = board.tileAt(ladder.top)

            val bx = bottomTile.col * tileWidth + tileWidth * 0.5f
            val by = (9 - bottomTile.row) * tileHeight + tileHeight * 0.5f
            val tx = topTile.col * tileWidth + tileWidth * 0.5f
            val ty = (9 - topTile.row) * tileHeight + tileHeight * 0.5f

            // Gambar tangga sebagai garis tebal putus-putus
            val steps = 8
            for (i in 0 until steps) {
                val t1 = i.toFloat() / steps
                val t2 = (i + 0.5f) / steps
                val x1 = bx + (tx - bx) * t1
                val y1 = by + (ty - by) * t1
                val x2 = bx + (tx - bx) * t2
                val y2 = by + (ty - by) * t2

                drawLine(
                    color = LadderBrown.copy(alpha = 0.5f),
                    start = Offset(x1, y1),
                    end = Offset(x2, y2),
                    strokeWidth = 4f
                )
            }
        }
    }
}
