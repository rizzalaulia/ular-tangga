package com.ulartangga.game.data

import android.content.Context
import android.content.SharedPreferences

/**
 * SharedPreferences wrapper untuk pengaturan & statistik.
 * Sederhana, tanpa dependency third-party.
 */
class PreferencesManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("ular_tangga_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_SOUND_EFFECTS = "sound_effects"
        private const val KEY_BG_MUSIC = "background_music"
        private const val KEY_MASTER_VOLUME = "master_volume"
        private const val KEY_TOTAL_GAMES = "total_games_played"
        private const val KEY_PLAYER_NAMES = "player_names"

        // Defaults
        const val DEFAULT_VOLUME = 80
    }

    // ---------- Sound Settings ----------

    var soundEffectsEnabled: Boolean
        get() = prefs.getBoolean(KEY_SOUND_EFFECTS, true)
        set(value) = prefs.edit().putBoolean(KEY_SOUND_EFFECTS, value).apply()

    var backgroundMusicEnabled: Boolean
        get() = prefs.getBoolean(KEY_BG_MUSIC, true)
        set(value) = prefs.edit().putBoolean(KEY_BG_MUSIC, value).apply()

    var masterVolume: Int
        get() = prefs.getInt(KEY_MASTER_VOLUME, DEFAULT_VOLUME)
        set(value) = prefs.edit().putInt(KEY_MASTER_VOLUME, value.coerceIn(0, 100)).apply()

    // ---------- Game Stats ----------

    val totalGamesPlayed: Int
        get() = prefs.getInt(KEY_TOTAL_GAMES, 0)

    fun incrementGamesPlayed() {
        prefs.edit().putInt(KEY_TOTAL_GAMES, totalGamesPlayed + 1).apply()
    }

    fun getWinsForPlayer(playerName: String): Int {
        return prefs.getInt("wins_$playerName", 0)
    }

    fun incrementWinsForPlayer(playerName: String) {
        val current = getWinsForPlayer(playerName)
        prefs.edit().putInt("wins_$playerName", current + 1).apply()
    }

    // ---------- Player Names Cache ----------

    fun getPlayerNames(): Set<String> {
        return prefs.getStringSet(KEY_PLAYER_NAMES, emptySet()) ?: emptySet()
    }

    fun addPlayerName(name: String) {
        val current = getPlayerNames().toMutableSet()
        current.add(name)
        prefs.edit().putStringSet(KEY_PLAYER_NAMES, current).apply()
    }
}
