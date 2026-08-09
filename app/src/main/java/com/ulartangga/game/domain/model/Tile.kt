package com.ulartangga.game.domain.model

/**
 * Satu kotak di papan ular tangga.
 */
data class Tile(
    val number: Int,            // Nomor kotak (1-100)
    val row: Int,               // Baris (0-9, 0 = baris bawah)
    val col: Int,               // Kolom (0-9)
    val isSnakeHead: Boolean = false,
    val isSnakeTail: Boolean = false,
    val isLadderBottom: Boolean = false,
    val isLadderTop: Boolean = false
)
