package com.ulartangga.game.domain.engine

/**
 * AI player — cuma jadi proxy, tidak ada logika khusus.
 *
 * Karena ular tangga adalah pure-luck game, AI hanya menjalankan
 * lempar dadu acak seperti pemain manusia. Tidak ada biased dice,
 * tidak ada strategi, tidak ada cheating.
 *
 * Kelas ini ada sebagai marker/placeholder untuk ekspansi di masa
 * depan (misalnya menambah persona AI, delay timing, dll).
 */
object AIPlayer {

    /** Nama-nama bot lucu buat AI. */
    val BOT_NAMES = listOf(
        "Bot Kucing", "Bot Kelinci", "Bot Anjing", "Bot Bebek", "Bot Katak"
    )

    /** Ambil nama bot berikutnya yang belum dipakai. */
    fun nextAvailableName(usedNames: List<String>, index: Int): String {
        val available = BOT_NAMES.filter { it !in usedNames }
        return available.getOrElse(index) { "Bot ${index + 1}" }
    }
}
