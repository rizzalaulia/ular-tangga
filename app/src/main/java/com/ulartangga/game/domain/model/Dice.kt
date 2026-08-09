package com.ulartangga.game.domain.model

/**
 * Dadu 6 sisi — dadu acak murni, tidak dicurangi.
 * Semua pemain (manusia maupun AI) memiliki peluang yang sama.
 */
class Dice(
    private val random: () -> Float = { kotlin.random.Random.nextFloat() }
) {
    /** Roll dadu 1-6, setiap sisi peluang ~16.67% */
    fun roll(): Int {
        return (random() * 6).toInt() + 1
    }
}
