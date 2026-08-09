package com.ulartangga.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import com.ulartangga.game.ui.screen.*
import com.ulartangga.game.ui.theme.UlarTanggaTheme
import com.ulartangga.game.ui.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {

    private val gameViewModel = GameViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            UlarTanggaTheme {
                MainApp(gameViewModel = gameViewModel)
            }
        }
    }
}

/**
 * Root composable — navigasi antar screen (tanpa Navigation library,
 * pakai state sederhana biar ringan).
 */
@Composable
fun MainApp(gameViewModel: GameViewModel) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

    val gameState by gameViewModel.gameState.collectAsState()
    val playersSetup by gameViewModel.playersSetup.collectAsState()
    val isAnimating by gameViewModel.isAnimating.collectAsState()

    when (currentScreen) {
        Screen.Home -> {
            HomeScreen(
                onMainBareng = {
                    gameViewModel.configurePlayers(playerCount = 2, aiCount = 0)
                    currentScreen = Screen.Setup(isVsAI = false)
                },
                onMainVsKomputer = {
                    gameViewModel.configurePlayers(playerCount = 2, aiCount = 1)
                    currentScreen = Screen.Setup(isVsAI = true)
                },
                onPengaturan = { currentScreen = Screen.Settings },
                onCaraBermain = { currentScreen = Screen.HowToPlay }
            )
        }

        is Screen.Setup -> {
            val setupScreen = currentScreen as Screen.Setup
            SetupScreen(
                isVsAI = setupScreen.isVsAI,
                playersSetup = playersSetup,
                onUpdatePlayerName = { index, name ->
                    gameViewModel.updatePlayerName(index, name)
                },
                onUpdatePlayerToken = { index, tokenIndex ->
                    gameViewModel.updatePlayerToken(index, tokenIndex)
                },
                onConfigurePlayers = { count, aiCount ->
                    gameViewModel.configurePlayers(count, aiCount)
                },
                onStartGame = {
                    gameViewModel.startGame()
                    currentScreen = Screen.Game
                },
                onBack = {
                    gameViewModel.resetGame()
                    currentScreen = Screen.Home
                }
            )
        }

        Screen.Game -> {
            gameState?.let { state ->
                val currentPlayer = state.players[state.currentPlayerIndex]
                GameScreen(
                    gameState = state,
                    isCurrentPlayerAI = currentPlayer.isAI,
                    isAnimating = isAnimating,
                    onRollDice = { gameViewModel.rollDice() },
                    onRestart = {
                        gameViewModel.resetGame()
                        // Restart dengan konfigurasi yang sama: langsung start game baru
                        // TODO: simpan last config
                    },
                    onQuit = {
                        gameViewModel.resetGame()
                        currentScreen = Screen.Home
                    }
                )
            } ?: run {
                // Fallback: kembali ke home kalau state null
                LaunchedEffect(Unit) { currentScreen = Screen.Home }
            }
        }

        Screen.HowToPlay -> {
            HowToPlayScreen(onBack = { currentScreen = Screen.Home })
        }

        Screen.Settings -> {
            SettingsScreen(
                soundEnabled = true,
                onSoundToggle = { /* TODO */ },
                bgmEnabled = true,
                onBgmToggle = { /* TODO */ },
                volume = 80,
                onVolumeChange = { /* TODO */ },
                onBack = { currentScreen = Screen.Home }
            )
        }
    }
}

sealed class Screen {
    data object Home : Screen()
    data class Setup(val isVsAI: Boolean) : Screen()
    data object Game : Screen()
    data object HowToPlay : Screen()
    data object Settings : Screen()
}
