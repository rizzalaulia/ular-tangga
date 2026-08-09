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
            UlarTanggaTheme { MainApp(gameViewModel = gameViewModel) }
        }
    }
}

@Composable
fun MainApp(gameViewModel: GameViewModel) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
    val gameState by gameViewModel.gameState.collectAsState()
    val playersSetup by gameViewModel.playersSetup.collectAsState()

    when (currentScreen) {
        Screen.Home -> HomeScreen(
            onMainBareng = {
                gameViewModel.configurePlayers(2, 0)
                currentScreen = Screen.Setup(false)
            },
            onMainVsKomputer = {
                gameViewModel.configurePlayers(2, 1)
                currentScreen = Screen.Setup(true)
            },
            onPengaturan = { currentScreen = Screen.Settings },
            onCaraBermain = { currentScreen = Screen.HowToPlay }
        )
        is Screen.Setup -> {
            val s = currentScreen as Screen.Setup
            SetupScreen(
                isVsAI = s.isVsAI, playersSetup = playersSetup,
                onUpdatePlayerName = { i, n -> gameViewModel.updatePlayerName(i, n) },
                onUpdatePlayerToken = { i, t -> gameViewModel.updatePlayerToken(i, t) },
                onConfigurePlayers = { c, a -> gameViewModel.configurePlayers(c, a) },
                onStartGame = { gameViewModel.startGame(); currentScreen = Screen.Game },
                onBack = { gameViewModel.resetGame(); currentScreen = Screen.Home }
            )
        }
        Screen.Game -> gameState?.let { state ->
            GameScreen(
                gameState = state,
                isCurrentPlayerAI = state.players[state.currentPlayerIndex].isAI,
                onRollDice = { gameViewModel.rollDice() },
                onRestart = { gameViewModel.startGame() },
                onQuit = { gameViewModel.resetGame(); currentScreen = Screen.Home }
            )
        } ?: run { LaunchedEffect(Unit) { currentScreen = Screen.Home } }
        Screen.HowToPlay -> HowToPlayScreen(onBack = { currentScreen = Screen.Home })
        Screen.Settings -> SettingsScreen(
            soundEnabled = true, onSoundToggle = {},
            bgmEnabled = true, onBgmToggle = {},
            volume = 80, onVolumeChange = {},
            onBack = { currentScreen = Screen.Home }
        )
    }
}

sealed class Screen {
    data object Home : Screen()
    data class Setup(val isVsAI: Boolean) : Screen()
    data object Game : Screen()
    data object HowToPlay : Screen()
    data object Settings : Screen()
}
