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
    isAnimating: Boolean,
    onRollDice: () -> Unit,
    onRestart: () -> Unit,
    onQuit: () -> Unit
) {
    val state = gameState
    val currentPlayer = state.players[state.currentPlayerIndex]
    var muteSound by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

            // ─── TOOLBAR POJOK KANAN ATAS ───
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { muteSound = !muteSound }) {
                    Text(if (muteSound) "\uD83D\uDD07" else "\uD83D\uDD0A", fontSize = 20.sp)
                }
                IconButton(onClick = onRestart) {
                    Text("\uD83D\uDD04", fontSize = 20.sp)
                }
                IconButton(onClick = onQuit) {
                    Text("\uD83C\uDFE0", fontSize = 20.sp)
                }
            }

            // ─── INFO PEMAIN ───
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly) {
                state.players.forEachIndexed { index, player ->
                    val isActive = index == state.currentPlayerIndex
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(TokenType.entries[player.tokenIndex].emoji,
                            fontSize = if (isActive) 24.sp else 18.sp)
                        Text("${player.name.take(6)}", fontSize = 11.sp,
                            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                            color = if (isActive) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onBackground)
                        Text("Kotak ${player.position}", fontSize = 10.sp)
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
                Card(Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                    Text(state.message, Modifier.padding(10.dp), fontSize = 13.sp, textAlign = TextAlign.Center)
                }
                Spacer(Modifier.height(4.dp))
            }

            // ─── KONTROL BAWAH ───
            when (state.phase) {
                GamePhase.ROLLING, GamePhase.EXTRA_TURN -> {
                    Text("\uD83C\uDFB2 Giliran: ${currentPlayer.name}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(4.dp))

                    if (isCurrentPlayerAI || isAnimating) {
                        state.diceResult?.let { DiceView(value = it, size = 70.dp) }
                        Spacer(Modifier.height(4.dp))
                        Text(if (isAnimating) "Pion bergerak..." else "AI melempar...", fontSize = 13.sp)
                    } else {
                        Button(
                            onClick = onRollDice,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp).height(68.dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp, pressedElevation = 2.dp)
                        ) {
                            Text("\uD83C\uDFB2  LEMPAR DADU", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                GamePhase.ANIMATING -> {
                    state.diceResult?.let { DiceView(value = it, size = 70.dp) }
                    Spacer(Modifier.height(4.dp))
                    Text("\uD83C\uDFB2 Dadu: ${state.diceResult ?: "?"}", fontSize = 14.sp)
                    Text("Pion bergerak selangkah demi selangkah...", fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f))
                }

                GamePhase.GAME_OVER -> {
                    Text("\uD83C\uDFC6 ${state.winner?.name ?: "?"} MENANG! \uD83C\uDFC6",
                        fontSize = 26.sp, fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary)
                    Spacer(Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(onClick = onRestart,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                        ) { Text("\uD83D\uDD04 Main Lagi") }
                        Button(onClick = onQuit) { Text("\uD83C\uDFE0 Menu") }
                    }
                }

                GamePhase.SETUP -> { Text("Setup...") }
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}
