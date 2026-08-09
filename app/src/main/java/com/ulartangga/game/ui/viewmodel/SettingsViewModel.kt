package com.ulartangga.game.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.ulartangga.game.data.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SettingsState(
    val soundEffectsEnabled: Boolean = true,
    val backgroundMusicEnabled: Boolean = true,
    val masterVolume: Int = 80
)

class SettingsViewModel(
    private val prefs: PreferencesManager
) : ViewModel() {

    private val _settings = MutableStateFlow(
        SettingsState(
            soundEffectsEnabled = prefs.soundEffectsEnabled,
            backgroundMusicEnabled = prefs.backgroundMusicEnabled,
            masterVolume = prefs.masterVolume
        )
    )
    val settings: StateFlow<SettingsState> = _settings.asStateFlow()

    fun setSoundEffects(enabled: Boolean) {
        prefs.soundEffectsEnabled = enabled
        _settings.value = _settings.value.copy(soundEffectsEnabled = enabled)
    }

    fun setBackgroundMusic(enabled: Boolean) {
        prefs.backgroundMusicEnabled = enabled
        _settings.value = _settings.value.copy(backgroundMusicEnabled = enabled)
    }

    fun setVolume(volume: Int) {
        prefs.masterVolume = volume
        _settings.value = _settings.value.copy(masterVolume = volume)
    }
}
