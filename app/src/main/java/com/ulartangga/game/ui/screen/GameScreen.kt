package com.ulartangga.game.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulartangga.game.domain.model.*
import com.ulartangga.game.ui.component.BoardView
import com.ulartangga.game.ui.component.DiceView

@Composable
fun GameScreen(
    gameState: GameState,
    isCurrentPlayerAI: Boolean,
    onRollDice: () -> Unit,
    onRestart: () -> Unit,
    onQuit: () -> Unit
) {
    val state = gameState
    val currentPlayer = state.players[state.currentPlayerIndex]

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

            // ─── TOOLBAR ───
            Row(Modifier.fillMaxWidth(), Arrangement.End, Alignment.CenterVertically) {
                IconButton(onClick = onRestart) { Text("\uD83D\uDD04", fontSize = 18.sp) }
                IconButton(onClick = onQuit) { Text("\uD83C\uDFE0", fontSize = 18.sp) }
            }

            // ─── INFO PEMAIN ───
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
                state.players.forEachIndexed { index, player ->
                    val active = index == state.currentPlayerIndex
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(TokenType.entries[player.tokenIndex].emoji, fontSize = if (active) 22.sp else 16.sp)
                        Text("${player.name.take(6)}", fontSize = 10.sp,
                            fontWeight = if (active) FontWeight.Bold else FontWeight.Normal)
                        Text("Kotak ${player.position}", fontSize = 9.sp)
                    }
                }
            }

            Spacer(Modifier.height(4.dp))

            // ─── PAPAN ───
            Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                BoardView(board = Board(), players = state.players)
            }

            Spacer(Modifier.height(4.dp))

            // ─── PESAN ───
            if (state.message.isNotEmpty()) {
                Text(state.message, Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                    fontSize = 12.sp, textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground)
                Spacer(Modifier.height(4.dp))
            }

            // ─── KONTROL ───
            when (state.phase) {
                GamePhase.ROLLING, GamePhase.EXTRA_TURN -> {
                    if (isCurrentPlayerAI) {
                        state.diceResult?.let { DiceView(value = it, size = 60.dp) }
                        Spacer(Modifier.height(4.dp))
                    }
                    Button(onClick = onRollDice,
                        modifier = Modifier.fillMaxWidth().height(60.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                    ) {
                        Text(if (isCurrentPlayerAI) "\uD83E\uDD16 Lihat Hasil AI"
                             else "\uD83C\uDFB2  LEMPAR DADU",
                            fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                }
                GamePhase.ANIMATING -> {
                    // Animasi transisi — tampilkan dadu hasil
                    state.diceResult?.let { DiceView(value = it, size = 60.dp) }
                    Spacer(Modifier.height(4.dp))
                    Text("Pion bergerak...", fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                }
                GamePhase.GAME_OVER -> {
                    Text("\uD83C\uDFC6 ${state.winner?.name ?: "?"} MENANG! \uD83C\uDFC6",
                        fontSize = 22.sp, fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary)
                    Spacer(Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(onClick = onRestart,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                        ) { Text("\uD83D\uDD04 Main Lagi") }
                        Button(onClick = onQuit) { Text("\uD83C\uDFE0 Menu") }
                    }
                }
                GamePhase.SETUP -> {}
            }

            Spacer(Modifier.height(4.dp))
        }
    }
}
