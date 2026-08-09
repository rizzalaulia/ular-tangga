package com.ulartangga.game.data

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool

/**
 * Manajer audio untuk efek suara game.
 * Menggunakan SoundPool untuk low-latency playback.
 *
 * TODO: Tambahkan file audio ke res/raw/ dan daftarkan di sini.
 * Untuk saat ini, semua metode return early — game tetap jalan tanpa sound.
 */
class SoundManager(context: Context) {

    private val soundPool: SoundPool

    // Sound IDs (diisi setelah file audio tersedia)
    private var diceRollId: Int = -1
    private var ladderClimbId: Int = -1
    private var snakeSlideId: Int = -1
    private var extraTurnId: Int = -1
    private var winFanfareId: Int = -1
    private var buttonClickId: Int = -1

    // Volume state
    var soundEnabled: Boolean = true
    var masterVolume: Float = 0.8f  // 0.0 - 1.0

    init {
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(attributes)
            .build()

        // TODO: Load sound files setelah ditambahkan ke res/raw
        // diceRollId = soundPool.load(context, R.raw.dice_roll, 1)
        // ladderClimbId = soundPool.load(context, R.raw.ladder_climb, 1)
        // snakeSlideId = soundPool.load(context, R.raw.snake_slide, 1)
        // extraTurnId = soundPool.load(context, R.raw.extra_turn, 1)
        // winFanfareId = soundPool.load(context, R.raw.win_fanfare, 1)
        // buttonClickId = soundPool.load(context, R.raw.button_click, 1)
    }

    fun playDiceRoll() = playSound(diceRollId)
    fun playLadderClimb() = playSound(ladderClimbId)
    fun playSnakeSlide() = playSound(snakeSlideId)
    fun playExtraTurn() = playSound(extraTurnId)
    fun playWinFanfare() = playSound(winFanfareId)
    fun playButtonClick() = playSound(buttonClickId)

    private fun playSound(soundId: Int) {
        if (soundId == -1 || !soundEnabled) return
        soundPool.play(soundId, masterVolume, masterVolume, 1, 0, 1.0f)
    }

    fun release() {
        soundPool.release()
    }
}
