package com.ulartangga.game.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Layar pengaturan — volume, sound toggle, BGM toggle.
 * TODO: Integrasi dengan SettingsViewModel setelah DI tersedia.
 */
@Composable
fun SettingsScreen(
    soundEnabled: Boolean,
    onSoundToggle: (Boolean) -> Unit,
    bgmEnabled: Boolean,
    onBgmToggle: (Boolean) -> Unit,
    volume: Int,
    onVolumeChange: (Int) -> Unit,
    onBack: () -> Unit
) {
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
                text = "\u2699\uFE0F Pengaturan",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Sound Effects Toggle
            Row(modifier = Modifier.fillMaxWidth()) {
                Text("\uD83D\uDD0A Efek Suara", modifier = Modifier.weight(1f))
                Switch(checked = soundEnabled, onCheckedChange = onSoundToggle)
            }

            // Background Music Toggle
            Row(modifier = Modifier.fillMaxWidth()) {
                Text("\uD83C\uDFB5 Musik Latar", modifier = Modifier.weight(1f))
                Switch(checked = bgmEnabled, onCheckedChange = onBgmToggle)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Volume Slider
            Text("Volume: $volume%")
            Slider(
                value = volume.toFloat(),
                onValueChange = { onVolumeChange(it.toInt()) },
                valueRange = 0f..100f,
                steps = 19
            )

            Spacer(modifier = Modifier.weight(1f))

            TextButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text("⬅ Kembali")
            }
        }
    }
}
