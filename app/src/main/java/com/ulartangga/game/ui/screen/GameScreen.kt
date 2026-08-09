package com.ulartangga.game.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulartangga.game.domain.model.*
import com.ulartangga.game.ui.component.BoardView
import com.ulartangga.game.ui.component.DiceView

/**
 * Layar utama saat game berlangsung.
 * Menampilkan papan, info pemain, dadu, dan log.
 */
@Composable
fun GameScreen(
    gameState: GameState,
    isCurrentPlayerAI: Boolean,
    onRollDice: () -> Unit,
    onQuit: () -> Unit
) {
    val state = gameState
    val currentPlayer = state.players[state.currentPlayerIndex]

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Info Pemain (compact, atas layar) ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                state.players.forEachIndexed { index, player ->
                    val tokenEmoji = TokenType.entries[player.tokenIndex].emoji
                    val isActive = index == state.currentPlayerIndex
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = if (isActive) "$tokenEmoji ⬅" else tokenEmoji,
                            fontSize = if (isActive) 22.sp else 18.sp
                        )
                        Text(
                            text = "${player.name.take(6)}",
                            fontSize = 11.sp,
                            color = if (isActive)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Kotak ${player.position}",
                            fontSize = 10.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // --- Papan Permainan ---
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                BoardView(
                    board = Board(),
                    players = state.players
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // --- Pesan / Event ---
            if (state.message.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Text(
                        text = state.message,
                        modifier = Modifier.padding(12.dp),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // --- Dadu & Tombol ---
            when (state.phase) {
                GamePhase.ROLLING, GamePhase.EXTRA_TURN -> {
                    Text(
                        text = "\uD83C\uDFB2 Giliran: ${currentPlayer.name}",
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (isCurrentPlayerAI) {
                        // AI — tampilkan tombol "auto", atau auto-roll dengan delay
                        Text("${currentPlayer.name} sedang berpikir...", fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = onRollDice,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            Text("Lihat Hasil Dadu AI")
                        }
                    } else {
                        Button(
                            onClick = onRollDice,
                            modifier = Modifier.size(100.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            Text("\uD83C\uDFB2\nLEMPAR", fontSize = 16.sp, textAlign = TextAlign.Center)
                        }
                    }

                    // Tampilkan hasil dadu sebelumnya
                    state.diceResult?.let { result ->
                        Spacer(modifier = Modifier.height(8.dp))
                        DiceView(value = result)
                    }
                }

                GamePhase.MOVING -> {
                    Text("Pion bergerak...", fontSize = 14.sp)
                    // TODO: Animasi gerak pion
                }

                GamePhase.GAME_OVER -> {
                    Text(
                        text = "\uD83C\uDFC6 ${state.winner?.name ?: "?"} MENANG! \uD83C\uDFC6",
                        fontSize = 28.sp,
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = onQuit) {
                        Text("🏠 Kembali ke Menu")
                    }
                }

                GamePhase.SETUP -> {
                    // Seharusnya gak sampai sini
                    Text("Setup...")
                }
            }
        }
    }
}
