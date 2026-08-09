package com.ulartangga.game.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.ulartangga.game.domain.engine.AIPlayer
import com.ulartangga.game.domain.engine.GameEngine
import com.ulartangga.game.domain.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {
    private val engine = GameEngine()

    private val _gameState = MutableStateFlow<GameState?>(null)
    val gameState: StateFlow<GameState?> = _gameState.asStateFlow()

    private val _playersSetup = MutableStateFlow<List<PlayerConfig>>(emptyList())
    val playersSetup: StateFlow<List<PlayerConfig>> = _playersSetup.asStateFlow()

    fun configurePlayers(playerCount: Int, aiCount: Int) {
        val configs = mutableListOf<PlayerConfig>()
        var botIndex = 0
        for (i in 0 until playerCount) {
            val isAI = i >= (playerCount - aiCount)
            configs.add(PlayerConfig(
                name = if (isAI) AIPlayer.nextAvailableName(configs.map { it.name }, botIndex++)
                else "Pemain ${i + 1}",
                tokenIndex = i % 5, isAI = isAI))
        }
        _playersSetup.value = configs
    }

    fun updatePlayerName(index: Int, name: String) {
        val configs = _playersSetup.value.toMutableList()
        if (index < configs.size && !configs[index].isAI) {
            configs[index] = configs[index].copy(name = name)
            _playersSetup.value = configs
        }
    }

    fun updatePlayerToken(index: Int, tokenIndex: Int) {
        val configs = _playersSetup.value.toMutableList()
        if (index < configs.size) {
            configs[index] = configs[index].copy(tokenIndex = tokenIndex)
            _playersSetup.value = configs
        }
    }

    fun startGame() {
        _gameState.value = engine.startGame(GameConfig(players = _playersSetup.value))
    }

    fun rollDice() {
        val state = _gameState.value ?: return
        _gameState.value = engine.processDiceRoll(state)
    }

    fun resetGame() {
        _gameState.value = null
        _playersSetup.value = emptyList()
    }
}
