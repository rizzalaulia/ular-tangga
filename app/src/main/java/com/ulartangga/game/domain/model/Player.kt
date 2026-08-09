package com.ulartangga.game.domain.model

/**
 * Representasi satu pemain di dalam game yang sedang berjalan.
 */
data class Player(
    val name: String,
    val tokenIndex: Int,         // Index ke token hewan (0-4)
    val isAI: Boolean = false,
    val position: Int = 0,       // Posisi di kotak; 0 = belum masuk papan
    val consecutiveSixes: Int = 0,  // Jumlah 6 berturut-turut dalam giliran ini
    val hasEnteredBoard: Boolean = false  // Apakah sudah masuk papan setelah lempar pertama
)

enum class TokenType(val emoji: String, val colorName: String) {
    CAT("\uD83D\uDC31", "Kucing"),      // 🐱
    RABBIT("\uD83D\uDC30", "Kelinci"),  // 🐰
    DOG("\uD83D\uDC36", "Anjing"),      // 🐶
    DUCK("\uD83D\uDC25", "Bebek"),      // 🐥
    FROG("\uD83D\uDC38", "Katak")       // 🐸
}
