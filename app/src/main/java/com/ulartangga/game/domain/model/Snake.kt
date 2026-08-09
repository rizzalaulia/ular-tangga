package com.ulartangga.game.domain.model

/**
 * Ular — menghubungkan kepala (angka tinggi) ke ekor (angka rendah).
 */
data class Snake(
    val head: Int,    // Posisi kepala ular (angka lebih tinggi)
    val tail: Int     // Posisi ekor ular (angka lebih rendah)
) {
    init {
        require(head > tail) { "Kepala ular ($head) harus lebih tinggi dari ekor ($tail)" }
    }

    companion object {
        /** Posisi ular standar Snakes and Ladders. */
        val STANDARD_SNAKES: List<Snake> = listOf(
            Snake(16, 6),
            Snake(47, 26),
            Snake(49, 11),
            Snake(56, 53),
            Snake(62, 19),
            Snake(64, 60),
            Snake(87, 24),
            Snake(93, 73),
            Snake(95, 75),
            Snake(98, 78)
        )
    }
}
