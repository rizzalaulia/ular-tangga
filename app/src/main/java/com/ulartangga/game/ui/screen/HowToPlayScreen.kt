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
 * Layar cara bermain — penjelasan aturan ular tangga sederhana.
 */
@Composable
fun HowToPlayScreen(onBack: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = "\u2753 Cara Bermain",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Aturan dalam bentuk kartu
            val rules = listOf(
                "\uD83C\uDFB2 **Lempar Dadu** — Setiap giliran kamu lempar dadu 1-6. Pion maju sesuai angka dadu.",
                "\uD83E\uDE9C **Tangga** — Mendarat di kaki tangga? Naik ke atas! Kaki tangga = kotak rendah, puncak = kotak tinggi.",
                "\uD83D\uDC0D **Ular** — Mendarat di kepala ular? Turun ke ekor! Kepala = kotak tinggi, ekor = kotak rendah.",
                "\uD83C\uDF89 **Dapat 6** — Kalau dapat 6, kamu dapat giliran lempar lagi!",
                "\u26A0\uFE0F **3x 6** — Kalau 3 kali berturut-turut dapat 6, giliran batal & pion tetap di posisi awal.",
                "\uD83C\uDFC6 **Menang** — Siapa yang duluan tepat di kotak 100, dialah pemenangnya!",
                "\uD83D\uDC65 **Main Bareng** — 2-5 orang main bergantian di HP yang sama. Seru bareng keluarga!",
                "\uD83E\uDD16 **Vs Komputer** — Main sendiri? Lawan AI! AI main fair, dadu acak seperti kamu."
            )

            rules.forEach { rule ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Text(
                        text = rule,
                        modifier = Modifier.padding(12.dp),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            TextButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text("⬅ Kembali")
            }
        }
    }
}
