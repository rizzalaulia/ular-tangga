package com.ulartangga.game.domain.model

/**
 * Papan permainan 10×10 kotak dengan ular & tangga.
 */
data class Board(
    val tiles: List<Tile> = generateStandardBoard(),
    val snakes: List<Snake> = Snake.STANDARD_SNAKES,
    val ladders: List<Ladder> = Ladder.STANDARD_LADDERS
) {
    /** Total kotak */
    val size: Int get() = 100

    /** Cari kotak berdasarkan nomor (1-indexed) */
    fun tileAt(number: Int): Tile = tiles[number - 1]

    /** Cek apakah di kotak ini ada kepala ular */
    fun isSnakeHead(number: Int): Boolean = number in snakeHeadSet
    private val snakeHeadSet: Set<Int> by lazy { snakes.map { it.head }.toSet() }

    /** Cek apakah di kotak ini ada kaki tangga */
    fun isLadderBottom(number: Int): Boolean = number in ladderBottomSet
    private val ladderBottomSet: Set<Int> by lazy { ladders.map { it.bottom }.toSet() }

    /** Dapatkan posisi setelah kena ular/tangga, atau null kalau tidak kena */
    fun checkSnakeOrLadder(position: Int): Int? {
        // Cek ular (kepala → ekor)
        snakes.find { it.head == position }?.let { return it.tail }
        // Cek tangga (kaki → puncak)
        ladders.find { it.bottom == position }?.let { return it.top }
        return null
    }

    companion object {
        /**
         * Generate papan 10×10 dengan penomoran zigzag (boustrophedon).
         * Baris 0 (bawah): 1→10 (kiri ke kanan)
         * Baris 1:        20→11 (kanan ke kiri)
         * Baris 2:        21→30 (kiri ke kanan)
         * ...dan seterusnya.
         */
        fun generateStandardBoard(): List<Tile> = buildList {
            for (row in 0 until 10) {
                val isLeftToRight = (row % 2 == 0)
                for (col in 0 until 10) {
                    val actualCol = if (isLeftToRight) col else (9 - col)
                    val number = row * 10 + col + 1
                    add(Tile(number = number, row = row, col = actualCol))
                }
            }
        }
    }
}
