package com.ulartangga.game.domain.model

/**
 * Tangga — menghubungkan bawah (angka rendah) ke atas (angka tinggi).
 */
data class Ladder(
    val bottom: Int,  // Kaki tangga (angka lebih rendah)
    val top: Int      // Puncak tangga (angka lebih tinggi)
) {
    init {
        require(top > bottom) { "Puncak tangga ($top) harus lebih tinggi dari kaki ($bottom)" }
    }

    companion object {
        /** Posisi tangga standar Snakes and Ladders. */
        val STANDARD_LADDERS: List<Ladder> = listOf(
            Ladder(2, 38),
            Ladder(7, 14),
            Ladder(8, 31),
            Ladder(15, 26),
            Ladder(21, 42),
            Ladder(28, 84),
            Ladder(36, 44),
            Ladder(51, 67),
            Ladder(71, 91),
            Ladder(78, 99)
        )
    }
}
