package com.ulartangga.game.domain.engine

import com.ulartangga.game.domain.model.*

/**
 * Core engine — mengelola semua aturan permainan ular tangga.
 * Immutable approach: setiap action return GameState baru.
 */
class GameEngine(
    private val dice: Dice = Dice()
) {
    /**
     * Memulai game baru dari config.
     */
    fun startGame(config: GameConfig): GameState {
        val players = config.players.map { cfg ->
            Player(
                name = if (cfg.isAI) cfg.aiName else cfg.name,
                tokenIndex = cfg.tokenIndex,
                isAI = cfg.isAI,
                position = 0,
                consecutiveSixes = 0,
                hasEnteredBoard = false
            )
        }
        return GameState(
            phase = GamePhase.ROLLING,
            players = players,
            currentPlayerIndex = 0,
            message = "Giliran ${players.first().name}!"
        )
    }

    /**
     * Memproses lempar dadu untuk current player.
     * Return:
     * - GameState baru dengan posisi terupdate
     * - null jika game sudah selesai
     */
    fun processDiceRoll(state: GameState): GameState {
        val roll = dice.roll()
        val player = state.players[state.currentPlayerIndex]

        // 1. Dadu dapat angka
        val sixCount = if (roll == 6) player.consecutiveSixes + 1 else 0

        // Jika 3x 6 berturut-turut → balik ke posisi awal giliran
        if (sixCount >= 3) {
            val originalPosition = player.position
            val resetPlayer = player.copy(consecutiveSixes = 0)
            val newPlayers = state.players.toMutableList()
            newPlayers[state.currentPlayerIndex] = resetPlayer

            val nextIndex = nextPlayerIndex(state.currentPlayerIndex, state.players.size)
            return state.copy(
                phase = GamePhase.MOVING,
                players = newPlayers,
                diceResult = roll,
                event = GameEvent.ThreeSixesBusted,
                message = "${player.name} dapat 6 tiga kali! Giliran batal.",
                currentPlayerIndex = nextIndex
            )
        }

        // 2. Pion bergerak
        val newPosition = player.position + roll

        // Cek overshoot > 100 → tetep di posisi lama
        if (newPosition > Board().size) {
            val newPlayers = state.players.toMutableList()
            newPlayers[state.currentPlayerIndex] = player.copy(consecutiveSixes = sixCount)

            val nextIndex = if (roll == 6) state.currentPlayerIndex else nextPlayerIndex(state.currentPlayerIndex, state.players.size)
            return state.copy(
                phase = GamePhase.MOVING,
                players = newPlayers,
                diceResult = roll,
                event = GameEvent.Overshoot,
                message = "Harus tepat di 100! ${player.name} tetap di ${player.position}.",
                currentPlayerIndex = nextIndex
            )
        }

        // 3. Cek menang (tepat di 100)
        if (newPosition == Board().size) {
            val winner = player.copy(position = newPosition)
            val newPlayers = state.players.toMutableList()
            newPlayers[state.currentPlayerIndex] = winner
            return state.copy(
                phase = GamePhase.GAME_OVER,
                players = newPlayers,
                diceResult = roll,
                event = GameEvent.Won(winner),
                winner = winner,
                message = "🎉 ${player.name} MENANG!"
            )
        }

        // 4. Cek ular / tangga
        val board = Board()
        val afterSnakeOrLadder = board.checkSnakeOrLadder(newPosition)

        val (finalPosition, event, msg) = if (afterSnakeOrLadder != null) {
            if (afterSnakeOrLadder > newPosition) {
                Triple(afterSnakeOrLadder, GameEvent.ClimbedLadder(newPosition, afterSnakeOrLadder),
                    "🪜 ${player.name} naik tangga! ${newPosition} → ${afterSnakeOrLadder}")
            } else {
                Triple(afterSnakeOrLadder, GameEvent.SlidDownSnake(newPosition, afterSnakeOrLadder),
                    "🐍 ${player.name} digigit ular! ${newPosition} → ${afterSnakeOrLadder}")
            }
        } else {
            Triple(newPosition, GameEvent.Normal, "${player.name} maju ke kotak $newPosition")
        }

        val movedPlayer = player.copy(position = finalPosition, consecutiveSixes = sixCount)
        val newPlayers = state.players.toMutableList()
        newPlayers[state.currentPlayerIndex] = movedPlayer

        // 5. Dapat 6 → giliran lagi
        val (phase, nextIndex, extraMsg) = if (roll == 6) {
            Triple(GamePhase.EXTRA_TURN, state.currentPlayerIndex, "\n🎲 DAPAT 6! Lempar lagi!")
        } else {
            Triple(GamePhase.ROLLING, nextPlayerIndex(state.currentPlayerIndex, state.players.size), "")
        }

        return state.copy(
            phase = phase,
            players = newPlayers,
            currentPlayerIndex = nextIndex,
            diceResult = roll,
            event = event,
            message = msg + extraMsg
        )
    }

    /** Hitung pemain berikutnya (clockwise). */
    private fun nextPlayerIndex(current: Int, totalPlayers: Int): Int {
        return (current + 1) % totalPlayers
    }
}
