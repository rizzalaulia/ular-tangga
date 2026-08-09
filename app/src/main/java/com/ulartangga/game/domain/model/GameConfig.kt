package com.ulartangga.game.domain.model

/**
 * Konfigurasi awal game.
 * Setiap sesi game baru punya GameConfig sendiri.
 */
data class GameConfig(
    val players: List<PlayerConfig>,
    val boardLayout: BoardLayout = BoardLayout.STANDARD
)

data class PlayerConfig(
    val name: String,
    val tokenIndex: Int,
    val isAI: Boolean = false,
    val aiName: String = if (isAI) "Bot" else name
)

enum class BoardLayout {
    STANDARD  // 10x10 — 100 kotak
}
