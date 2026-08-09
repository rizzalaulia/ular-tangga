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
            turnStartPosition = 0,
            message = "Giliran ${players.first().name}!"
        )
    }

    /**
     * Memproses lempar dadu untuk current player.
     * Semua aturan permainan diterapkan di sini.
     */
    fun processDiceRoll(state: GameState): GameState {
        val roll = dice.roll()
        val player = state.players[state.currentPlayerIndex]
        val sixCount = if (roll == 6) player.consecutiveSixes + 1 else 0

        // --- ATURAN: 3x6 berturut-turut ---
        // Pion kembali ke posisi AWAL GILIRAN (bukan posisi setelah 2x6)
        if (sixCount >= 3) {
            val resetPlayer = player.copy(
                position = state.turnStartPosition,
                consecutiveSixes = 0
            )
            val newPlayers = state.players.toMutableList()
            newPlayers[state.currentPlayerIndex] = resetPlayer
            val nextIndex = nextPlayerIndex(state.currentPlayerIndex, state.players.size)

            return state.copy(
                phase = GamePhase.MOVING,
                players = newPlayers,
                currentPlayerIndex = nextIndex,
                turnStartPosition = newPlayers[nextIndex].position,
                diceResult = roll,
                event = GameEvent.ThreeSixesBusted,
                message = "${player.name} dapat 6 tiga kali! Giliran batal. Kembali ke kotak ${state.turnStartPosition}."
            )
        }

        // --- ATURAN: Pion bergerak maju ---
        val newPosition = player.position + roll

        // --- ATURAN: Overshoot kotak 100 ---
        if (newPosition > Board().size) {
            val newPlayers = state.players.toMutableList()
            newPlayers[state.currentPlayerIndex] = player.copy(consecutiveSixes = sixCount)

            val nextIndex = if (roll == 6) state.currentPlayerIndex
                           else nextPlayerIndex(state.currentPlayerIndex, state.players.size)

            return state.copy(
                phase = GamePhase.MOVING,
                players = newPlayers,
                currentPlayerIndex = nextIndex,
                turnStartPosition = newPlayers[nextIndex].position,
                diceResult = roll,
                event = GameEvent.Overshoot,
                message = "Harus tepat di 100! ${player.name} tetap di ${player.position}."
            )
        }

        // --- ATURAN: Mendarat tepat di 100 → MENANG ---
        if (newPosition == Board().size) {
            val winner = player.copy(position = newPosition)
            val newPlayers = state.players.toMutableList()
            newPlayers[state.currentPlayerIndex] = winner

            return state.copy(
                phase = GamePhase.GAME_OVER,
                players = newPlayers,
                turnStartPosition = newPosition,
                diceResult = roll,
                event = GameEvent.Won(winner),
                winner = winner,
                message = "\uD83C\uDF89 ${player.name} MENANG!"
            )
        }

        // --- ATURAN: Ular atau Tangga ---
        val board = Board()
        val afterSnakeOrLadder = board.checkSnakeOrLadder(newPosition)

        val (finalPosition, event, msg) = if (afterSnakeOrLadder != null) {
            if (afterSnakeOrLadder > newPosition) {
                Triple(afterSnakeOrLadder, GameEvent.ClimbedLadder(newPosition, afterSnakeOrLadder),
                    "\uD83E\uDE9C ${player.name} naik tangga! ${newPosition} → ${afterSnakeOrLadder}")
            } else {
                Triple(afterSnakeOrLadder, GameEvent.SlidDownSnake(newPosition, afterSnakeOrLadder),
                    "\uD83D\uDC0D ${player.name} digigit ular! ${newPosition} → ${afterSnakeOrLadder}")
            }
        } else {
            Triple(newPosition, GameEvent.Normal, "${player.name} maju ke kotak $newPosition")
        }

        val movedPlayer = player.copy(position = finalPosition, consecutiveSixes = sixCount)
        val newPlayers = state.players.toMutableList()
        newPlayers[state.currentPlayerIndex] = movedPlayer

        // --- ATURAN: Dapat 6 → giliran lagi (turnStartPosition tetap) ---
        val (phase, nextIndex, nextTurnStart, extraMsg) = if (roll == 6) {
            // Same player rolls again — turnStartPosition stays
            com.ulartangga.game.domain.model.Triple(
                GamePhase.EXTRA_TURN,
                state.currentPlayerIndex,
                state.turnStartPosition,
                "\n\uD83C\uDFB2 DAPAT 6! Lempar lagi!"
            )
        } else {
            // Next player's turn — snapshot their position
            val nextIdx = nextPlayerIndex(state.currentPlayerIndex, state.players.size)
            com.ulartangga.game.domain.model.Triple(
                GamePhase.ROLLING,
                nextIdx,
                newPlayers[nextIdx].position,
                ""
            )
        }

        return state.copy(
            phase = phase,
            players = newPlayers,
            currentPlayerIndex = nextIndex,
            turnStartPosition = nextTurnStart,
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

/** Helper triple — menghindari bentrok nama dengan Kotlin stdlib Triple */
data class Triple<A, B, C>(val first: A, val second: B, val third: C)
