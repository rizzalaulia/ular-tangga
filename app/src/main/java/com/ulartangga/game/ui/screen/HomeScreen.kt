package com.ulartangga.game.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Layar utama — menu awal game.
 * Navigasi antar mode (2 tombol + pengaturan + cara main).
 */
@Composable
fun HomeScreen(
    onMainBareng: () -> Unit,
    onMainVsKomputer: () -> Unit,
    onPengaturan: () -> Unit,
    onCaraBermain: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "\uD83D\uDC0D Ular Tangga \uD83E\uDE9C",
                style = MaterialTheme.typography.displayLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Game ular tangga seru untuk anak Indonesia!",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Tombol Main Bareng (Multiplayer)
            Button(
                onClick = onMainBareng,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    text = "\uD83D\uDC65 Main Bareng",
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol Main vs Komputer
            Button(
                onClick = onMainVsKomputer,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    text = "\uD83E\uDD16 Main vs Komputer",
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Tombol Pengaturan
            TextButton(onClick = onPengaturan) {
                Text("\u2699\uFE0F Pengaturan", fontSize = 16.sp)
            }

            // Tombol Cara Bermain
            TextButton(onClick = onCaraBermain) {
                Text("\u2753 Cara Bermain", fontSize = 16.sp)
            }
        }
    }
}
