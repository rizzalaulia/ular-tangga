package com.ulartangga.game.domain.engine

import com.ulartangga.game.domain.model.*

/**
 * Core engine — mengelola semua aturan permainan ular tangga.
 * Immutable approach: setiap action return GameState baru.
 */
class GameEngine(
    private val dice: Dice = Dice()
) {
    fun startGame(config: GameConfig): GameState {
        val players = config.players.map { cfg ->
            Player(name = if (cfg.isAI) cfg.aiName else cfg.name,
                tokenIndex = cfg.tokenIndex, isAI = cfg.isAI)
        }
        return GameState(
            phase = GamePhase.ROLLING, players = players,
            currentPlayerIndex = 0, turnStartPosition = 0,
            message = "Giliran ${players.first().name}!"
        )
    }

    fun processDiceRoll(state: GameState): GameState {
        val roll = dice.roll()
        val player = state.players[state.currentPlayerIndex]
        val sixCount = if (roll == 6) player.consecutiveSixes + 1 else 0

        // ATURAN: 3x6 berturut-turut
        if (sixCount >= 3) {
            val resetPlayer = player.copy(position = state.turnStartPosition, consecutiveSixes = 0)
            val newPlayers = state.players.toMutableList().also { it[state.currentPlayerIndex] = resetPlayer }
            val nextIndex = nextPlayerIndex(state.currentPlayerIndex, state.players.size)
            return state.copy(
                phase = GamePhase.ROLLING, players = newPlayers, currentPlayerIndex = nextIndex,
                turnStartPosition = newPlayers[nextIndex].position, diceResult = roll,
                event = GameEvent.ThreeSixesBusted,
                message = "${player.name} dapat 6 tiga kali! Giliran batal. Kembali ke ${state.turnStartPosition}."
            )
        }

        // Hitung path langkah (untuk animasi stepping)
        val startPos = player.position
        val newPosition = startPos + roll

        // Overshoot kotak 100
        if (newPosition > Board().size) {
            val newPlayers = state.players.toMutableList().also { it[state.currentPlayerIndex] = player.copy(consecutiveSixes = sixCount) }
            val nextIndex = if (roll == 6) state.currentPlayerIndex else nextPlayerIndex(state.currentPlayerIndex, state.players.size)
            return state.copy(
                phase = GamePhase.ROLLING, players = newPlayers, currentPlayerIndex = nextIndex,
                turnStartPosition = newPlayers[nextIndex].position, diceResult = roll,
                event = GameEvent.Overshoot,
                message = "Harus tepat di 100! ${player.name} tetap di ${startPos}."
            )
        }

        // Build step path: [startPos+1, startPos+2, ..., target]
        val stepPath = (startPos + 1..newPosition).toList()

        // Menang tepat di 100
        if (newPosition == Board().size) {
            val winner = player.copy(position = newPosition)
            val newPlayers = state.players.toMutableList().also { it[state.currentPlayerIndex] = winner }
            return state.copy(
                phase = GamePhase.ANIMATING, players = newPlayers, diceResult = roll,
                event = GameEvent.Won(winner), winner = winner,
                message = "\uD83C\uDF89 ${player.name} MENANG!",
                stepPath = stepPath, stepIndex = -1,
                currentPlayerIndex = state.currentPlayerIndex
            )
        }

        // Cek ular/tangga
        val board = Board()
        val afterSnakeOrLadder = board.checkSnakeOrLadder(newPosition)
        val (finalPos, event, msg) = when {
            afterSnakeOrLadder != null && afterSnakeOrLadder > newPosition ->
                Triple(afterSnakeOrLadder, GameEvent.ClimbedLadder(newPosition, afterSnakeOrLadder),
                    "\uD83E\uDE9C ${player.name} naik tangga! ${newPosition} → ${afterSnakeOrLadder}")
            afterSnakeOrLadder != null ->
                Triple(afterSnakeOrLadder, GameEvent.SlidDownSnake(newPosition, afterSnakeOrLadder),
                    "\uD83D\uDC0D ${player.name} digigit ular! ${newPosition} → ${afterSnakeOrLadder}")
            else ->
                Triple(newPosition, GameEvent.Normal, "${player.name} maju ke $newPosition")
        }

        // Tambah path ke posisi akhir (setelah ular/tangga)
        val fullPath = stepPath.toMutableList()
        if (finalPos != newPosition) {
            fullPath.add(finalPos)
        }

        val movedPlayer = player.copy(position = finalPos, consecutiveSixes = sixCount)
        val newPlayers = state.players.toMutableList().also { it[state.currentPlayerIndex] = movedPlayer }

        if (roll == 6) {
            return state.copy(
                phase = GamePhase.ANIMATING, players = newPlayers,
                currentPlayerIndex = state.currentPlayerIndex, diceResult = roll,
                event = event, message = msg + "\n\uD83C\uDFB2 DAPAT 6! Lempar lagi!",
                stepPath = fullPath, stepIndex = -1
            )
        }

        val nextIdx = nextPlayerIndex(state.currentPlayerIndex, state.players.size)
        return state.copy(
            phase = GamePhase.ANIMATING, players = newPlayers,
            currentPlayerIndex = nextIdx, turnStartPosition = newPlayers[nextIdx].position,
            diceResult = roll, event = event, message = msg,
            stepPath = fullPath, stepIndex = -1
        )
    }

    /**
     * Menggerakkan pion satu langkah dalam animasi.
     * Return state dengan stepIndex+1 dan player.position di-update ke langkah tersebut.
     */
    fun advanceStep(state: GameState): GameState {
        val path = state.stepPath
        if (path.isEmpty() || state.stepIndex >= path.lastIndex) return state

        val nextStepIdx = state.stepIndex + 1
        val stepPos = path[nextStepIdx]
        val newPlayers = state.players.toMutableList()
        val player = newPlayers[state.currentPlayerIndex]
        newPlayers[state.currentPlayerIndex] = player.copy(position = stepPos)

        val done = (nextStepIdx >= path.lastIndex)

        return state.copy(
            players = newPlayers,
            stepIndex = nextStepIdx,
            phase = if (done && state.event is GameEvent.Won) GamePhase.GAME_OVER
                    else if (done) GamePhase.ROLLING
                    else GamePhase.ANIMATING
        )
    }

    private fun nextPlayerIndex(current: Int, total: Int) = (current + 1) % total
}
