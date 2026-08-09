package com.ulartangga.game.domain.model

data class GameState(
    val phase: GamePhase,
    val players: List<Player>,
    val currentPlayerIndex: Int,
    val turnStartPosition: Int = 0,
    val diceResult: Int? = null,
    val event: GameEvent? = null,
    val winner: Player? = null,
    val message: String = "",
    /** Posisi yang sedang dianimasi (-1 = tidak ada animasi) */
    val animatingPosition: Int = -1
)

enum class GamePhase {
    SETUP,
    ROLLING,
    ANIMATING,
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
