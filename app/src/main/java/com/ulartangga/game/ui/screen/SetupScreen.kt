package com.ulartangga.game.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulartangga.game.domain.model.PlayerConfig
import com.ulartangga.game.domain.model.TokenType

@OptIn(ExperimentalMaterial3Api::class)

/**
 * Layar setup game — pilih jumlah pemain, nama, token, lawan AI.
 */
@Composable
fun SetupScreen(
    isVsAI: Boolean,
    playersSetup: List<PlayerConfig>,
    onUpdatePlayerName: (Int, String) -> Unit,
    onUpdatePlayerToken: (Int, Int) -> Unit,
    onConfigurePlayers: (Int, Int) -> Unit,
    onStartGame: () -> Unit,
    onBack: () -> Unit
) {
    var playerCount by remember { mutableIntStateOf(2) }
    var aiCount by remember { mutableIntStateOf(if (isVsAI) 1 else 0) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Header
            Text(
                text = "\u2699\uFE0F Setup Permainan",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Pilih jumlah pemain
            Text("Jumlah Pemain:", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                for (count in 2..5) {
                    FilterChip(
                        selected = playerCount == count,
                        onClick = {
                            playerCount = count
                            val actualAi = if (isVsAI) count - 1 else 0
                            onConfigurePlayers(count, actualAi)
                        },
                        label = { Text("$count") }
                    )
                }
            }

            if (isVsAI) {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Jumlah AI Lawan:", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (ai in 1..4) {
                        FilterChip(
                            selected = aiCount == ai,
                            onClick = {
                                aiCount = ai
                                val humanCount = playerCount - ai
                                if (humanCount >= 1) {
                                    onConfigurePlayers(playerCount, ai)
                                }
                            },
                            label = { Text("$ai") },
                            enabled = ai < playerCount  // Gak boleh AI = total pemain
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Daftar pemain
            playersSetup.forEachIndexed { index, player ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Token picker
                    Text(
                        text = TokenType.entries[player.tokenIndex].emoji,
                        fontSize = 28.sp
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        if (player.isAI) {
                            Text(
                                text = "${player.name} \uD83E\uDD16",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        } else {
                            OutlinedTextField(
                                value = player.name,
                                onValueChange = { onUpdatePlayerName(index, it) },
                                label = { Text("Pemain ${index + 1}") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Tombol mulai
            Button(
                onClick = onStartGame,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("🎲 MULAI MAIN!", fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text("⬅ Kembali")
            }
        }
    }
}
