package com.ulartangga.game.domain.engine

import com.ulartangga.game.domain.model.*

class GameEngine(private val dice: Dice = Dice()) {

    fun startGame(config: GameConfig): GameState {
        val players = config.players.map { cfg ->
            Player(name = if (cfg.isAI) cfg.aiName else cfg.name,
                tokenIndex = cfg.tokenIndex, isAI = cfg.isAI)
        }
        return GameState(phase = GamePhase.ROLLING, players = players,
            currentPlayerIndex = 0, message = "Giliran ${players.first().name}!")
    }

    fun processDiceRoll(state: GameState): GameState {
        val roll = dice.roll()
        val player = state.players[state.currentPlayerIndex]
        val sixCount = if (roll == 6) player.consecutiveSixes + 1 else 0

        // 3x6 busted
        if (sixCount >= 3) {
            val reset = player.copy(position = state.turnStartPosition, consecutiveSixes = 0)
            val nextIdx = (state.currentPlayerIndex + 1) % state.players.size
            return state.copy(phase = GamePhase.ROLLING,
                players = state.players.toMutableList().also { it[state.currentPlayerIndex] = reset },
                currentPlayerIndex = nextIdx, turnStartPosition = state.players[nextIdx].position,
                diceResult = roll, event = GameEvent.ThreeSixesBusted,
                message = "${player.name} dapat 6 tiga kali! Kembali ke ${state.turnStartPosition}.")
        }

        val startPos = player.position
        val target = startPos + roll

        // Overshoot
        if (target > 100) {
            val nextIdx = if (roll == 6) state.currentPlayerIndex else (state.currentPlayerIndex + 1) % state.players.size
            return state.copy(phase = GamePhase.ROLLING,
                players = state.players.toMutableList().also { it[state.currentPlayerIndex] = player.copy(consecutiveSixes = sixCount) },
                currentPlayerIndex = nextIdx, turnStartPosition = state.players[nextIdx].position,
                diceResult = roll, event = GameEvent.Overshoot,
                message = "Harus tepat di 100! ${player.name} tetap di $startPos.",
                animatingPosition = startPos)
        }

        // Win
        if (target == 100) {
            val winner = player.copy(position = 100)
            return state.copy(phase = GamePhase.GAME_OVER,
                players = state.players.toMutableList().also { it[state.currentPlayerIndex] = winner },
                diceResult = roll, event = GameEvent.Won(winner), winner = winner,
                message = "\uD83C\uDF89 ${player.name} MENANG!",
                animatingPosition = 100)
        }

        // Snake/ladder
        val board = Board()
        val after = board.checkSnakeOrLadder(target)
        val (finalPos, event, msg) = when {
            after != null && after > target -> Triple(after, GameEvent.ClimbedLadder(target, after),
                "\uD83E\uDE9C ${player.name} naik tangga! $target → $after")
            after != null -> Triple(after, GameEvent.SlidDownSnake(target, after),
                "\uD83D\uDC0D ${player.name} digigit ular! $target → $after")
            else -> Triple(target, GameEvent.Normal, "${player.name} maju ke $target")
        }

        val moved = player.copy(position = finalPos, consecutiveSixes = sixCount)
        val newPlayers = state.players.toMutableList().also { it[state.currentPlayerIndex] = moved }

        if (roll == 6) {
            return state.copy(phase = GamePhase.EXTRA_TURN, players = newPlayers,
                currentPlayerIndex = state.currentPlayerIndex, diceResult = roll,
                event = event, message = msg + "\n\uD83C\uDFB2 DAPAT 6! Lempar lagi!",
                animatingPosition = finalPos)
        }

        val nextIdx = (state.currentPlayerIndex + 1) % state.players.size
        return state.copy(phase = GamePhase.ANIMATING, players = newPlayers,
            currentPlayerIndex = nextIdx, turnStartPosition = newPlayers[nextIdx].position,
            diceResult = roll, event = event, message = msg,
            animatingPosition = finalPos)
    }
}
