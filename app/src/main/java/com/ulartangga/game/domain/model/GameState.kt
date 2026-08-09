package com.ulartangga.game.domain.model

/**
 * Status game saat ini — immutable state.
 */
data class GameState(
    val phase: GamePhase,
    val players: List<Player>,
    val currentPlayerIndex: Int,
    val turnStartPosition: Int = 0,
    val diceResult: Int? = null,
    val event: GameEvent? = null,
    val winner: Player? = null,
    val message: String = "",
    /** Posisi intermediate untuk animasi stepping (inclusive start → exclusive end) */
    val stepPath: List<Int> = emptyList(),
    /** Index terakhir yang sudah dianimasi di stepPath (-1 = belum mulai) */
    val stepIndex: Int = -1
)

enum class GamePhase {
    SETUP,
    ROLLING,
    ANIMATING,          // Pion sedang animasi step-by-step
    EXTRA_TURN,
    GAME_OVER
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
