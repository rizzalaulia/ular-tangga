package com.ulartangga.game.domain.model

/**
 * Status game saat ini — immutable state.
 * UI membaca dari sini dan gak bisa mengubah langsung.
 */
data class GameState(
    val phase: GamePhase,
    val players: List<Player>,
    val currentPlayerIndex: Int,
    val diceResult: Int? = null,
    val event: GameEvent? = null,
    val winner: Player? = null,
    val message: String = ""
)

enum class GamePhase {
    SETUP,              // Pilih mode, pemain, nama
    ROLLING,            // Menunggu pemain lempar dadu / AI "berpikir"
    MOVING,             // Pion sedang bergerak (animasi)
    EXTRA_TURN,         // Dapat 6 → giliran tambahan
    GAME_OVER           // Ada pemenang
}

sealed class GameEvent {
    data object Normal : GameEvent()
    data class ClimbedLadder(val from: Int, val to: Int) : GameEvent()
    data class SlidDownSnake(val from: Int, val to: Int) : GameEvent()
    data object ExtraTurnOnSix : GameEvent()
    data object ThreeSixesBusted : GameEvent()
    data object Overshoot : GameEvent()
    data class Won(val player: Player) : GameEvent()
}
