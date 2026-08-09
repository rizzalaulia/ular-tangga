package com.ulartangga.game.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.ulartangga.game.domain.engine.AIPlayer
import com.ulartangga.game.domain.engine.GameEngine
import com.ulartangga.game.domain.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel utama game — menjembatani GameEngine dengan UI.
 * UI hanya membaca [gameState] dan memanggil action di sini.
 */
class GameViewModel : ViewModel() {

    private val engine = GameEngine()

    private val _gameState = MutableStateFlow<GameState?>(null)
    val gameState: StateFlow<GameState?> = _gameState.asStateFlow()

    private val _playersSetup = MutableStateFlow<List<PlayerConfig>>(emptyList())
    val playersSetup: StateFlow<List<PlayerConfig>> = _playersSetup.asStateFlow()

    /** Setup jumlah pemain + AI */
    fun configurePlayers(playerCount: Int, aiCount: Int) {
        val configs = mutableListOf<PlayerConfig>()
        var botIndex = 0

        for (i in 0 until playerCount) {
            val isAI = i >= (playerCount - aiCount)
            val name = if (isAI) {
                AIPlayer.nextAvailableName(configs.map { it.name }, botIndex++)
            } else {
                "Pemain ${i + 1}"
            }
            configs.add(
                PlayerConfig(
                    name = name,
                    tokenIndex = i % 5,
                    isAI = isAI
                )
            )
        }
        _playersSetup.value = configs
    }

    /** Update nama pemain manusia */
    fun updatePlayerName(index: Int, name: String) {
        val configs = _playersSetup.value.toMutableList()
        if (index < configs.size && !configs[index].isAI) {
            configs[index] = configs[index].copy(name = name)
            _playersSetup.value = configs
        }
    }

    /** Pilih token untuk player tertentu */
    fun updatePlayerToken(index: Int, tokenIndex: Int) {
        val configs = _playersSetup.value.toMutableList()
        if (index < configs.size) {
            configs[index] = configs[index].copy(tokenIndex = tokenIndex)
        }
        _playersSetup.value = configs
    }

    /** Mulai game */
    fun startGame() {
        val config = GameConfig(players = _playersSetup.value)
        _gameState.value = engine.startGame(config)
    }

    /** Lempar dadu — dipanggil dari UI saat player tap */
    fun rollDice() {
        val state = _gameState.value ?: return
        _gameState.value = engine.processDiceRoll(state)
    }

    /** Reset game ke setup */
    fun resetGame() {
        _gameState.value = null
        _playersSetup.value = emptyList()
    }
}
