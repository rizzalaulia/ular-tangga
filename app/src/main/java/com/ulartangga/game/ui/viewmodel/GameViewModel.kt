package com.ulartangga.game.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulartangga.game.domain.engine.AIPlayer
import com.ulartangga.game.domain.engine.GameEngine
import com.ulartangga.game.domain.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    private val engine = GameEngine()

    private val _gameState = MutableStateFlow<GameState?>(null)
    val gameState: StateFlow<GameState?> = _gameState.asStateFlow()

    private val _playersSetup = MutableStateFlow<List<PlayerConfig>>(emptyList())
    val playersSetup: StateFlow<List<PlayerConfig>> = _playersSetup.asStateFlow()

    private val _isAnimating = MutableStateFlow(false)
    val isAnimating: StateFlow<Boolean> = _isAnimating.asStateFlow()

    fun configurePlayers(playerCount: Int, aiCount: Int) {
        val configs = mutableListOf<PlayerConfig>()
        var botIndex = 0
        for (i in 0 until playerCount) {
            val isAI = i >= (playerCount - aiCount)
            val name = if (isAI) AIPlayer.nextAvailableName(configs.map { it.name }, botIndex++)
            else "Pemain ${i + 1}"
            configs.add(PlayerConfig(name = name, tokenIndex = i % 5, isAI = isAI))
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
        }
        _playersSetup.value = configs
    }

    fun startGame() {
        val config = GameConfig(players = _playersSetup.value)
        _gameState.value = engine.startGame(config)
    }

    /**
     * Lempar dadu → jalankan animasi stepping otomatis.
     */
    fun rollDice() {
        val state = _gameState.value ?: return
        if (_isAnimating.value) return

        _isAnimating.value = true

        // Phase 1: hitung hasil dadu
        val afterRoll = engine.processDiceRoll(state)
        _gameState.value = afterRoll

        // Phase 2: animasi step-by-step
        viewModelScope.launch {
            var current = afterRoll
            while (current.phase == GamePhase.ANIMATING && current.stepIndex < current.stepPath.lastIndex) {
                delay(150) // 150ms per langkah
                current = engine.advanceStep(current)
                _gameState.value = current
            }
            _isAnimating.value = false
        }
    }

    fun resetGame() {
        _gameState.value = null
        _playersSetup.value = emptyList()
        _isAnimating.value = false
    }
}
