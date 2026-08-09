package com.ulartangga.game.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Tampilan dadu sederhana (tanpa animasi 3D — cukup visual statis).
 * TODO: Tambah animasi rotasi dadu saat lempar.
 */
@Composable
fun DiceView(
    value: Int,
    size: Dp = 80.dp,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Background dadu (rounded square)
            drawRoundRect(
                color = Color.White,
                cornerRadius = CornerRadius(12f, 12f),
                size = Size(size.toPx(), size.toPx())
            )

            // Border
            drawRoundRect(
                color = Color.Gray,
                cornerRadius = CornerRadius(12f, 12f),
                size = Size(size.toPx(), size.toPx()),
                style = Stroke(width = 2f)
            )

            // Angka dadu
            val valueText = textMeasurer.measure(
                text = "$value",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = (size.value / 2).sp,
                    textAlign = TextAlign.Center
                )
            )
            drawText(
                textLayoutResult = valueText,
                topLeft = Offset(
                    (size.toPx() - valueText.size.width) / 2,
                    (size.toPx() - valueText.size.height) / 2
                )
            )

            // Dot pattern sesuai angka dadu (opsional, untuk dadu realistik)
            val cx = size.toPx() / 2
            val cy = size.toPx() / 2

            when (value) {
                1 -> drawDiceDot(cx, cy, 5f)
                2 -> {
                    drawDiceDot(cx - 12f, cy - 12f, 5f)
                    drawDiceDot(cx + 12f, cy + 12f, 5f)
                }
                3 -> {
                    drawDiceDot(cx - 12f, cy - 12f, 4f)
                    drawDiceDot(cx, cy, 4f)
                    drawDiceDot(cx + 12f, cy + 12f, 4f)
                }
                4 -> {
                    drawDiceDot(cx - 12f, cy - 12f, 4f)
                    drawDiceDot(cx + 12f, cy - 12f, 4f)
                    drawDiceDot(cx - 12f, cy + 12f, 4f)
                    drawDiceDot(cx + 12f, cy + 12f, 4f)
                }
                5 -> {
                    drawDiceDot(cx - 12f, cy - 12f, 4f)
                    drawDiceDot(cx + 12f, cy - 12f, 4f)
                    drawDiceDot(cx, cy, 4f)
                    drawDiceDot(cx - 12f, cy + 12f, 4f)
                    drawDiceDot(cx + 12f, cy + 12f, 4f)
                }
                6 -> {
                    drawDiceDot(cx - 12f, cy - 12f, 4f)
                    drawDiceDot(cx + 12f, cy - 12f, 4f)
                    drawDiceDot(cx - 12f, cy, 4f)
                    drawDiceDot(cx + 12f, cy, 4f)
                    drawDiceDot(cx - 12f, cy + 12f, 4f)
                    drawDiceDot(cx + 12f, cy + 12f, 4f)
                }
            }
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawDiceDot(
    x: Float,
    y: Float,
    radius: Float
) {
    drawCircle(
        color = Color.Black,
        radius = radius,
        center = Offset(x, y)
    )
}
