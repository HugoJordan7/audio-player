package com.example.audioplayer.feature.main.viewModel

import android.content.Context
import androidx.compose.runtime.FloatState
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.exoplayer.ExoPlayer
import com.example.audioplayer.service.repository.ExoPlayerRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel(private val exoPlayerRepository: ExoPlayerRepository) : ViewModel() {

    private val _playerState: MutableState<ExoPlayer?> = mutableStateOf(null)
    val playerState: State<ExoPlayer?> get() = _playerState

    private val _audioLength: MutableState<String> = mutableStateOf("0:00")
    val audioLength: State<String> get() = _audioLength

    private val _audioProgress: MutableState<Float> = mutableFloatStateOf(0.00f)
    val audioProgress: State<Float> get() = _audioProgress

    private val _formatAudioProgress = mutableStateOf("0:00")
    val formatAudioProgress: State<String> get() = _formatAudioProgress

    private val _isFailed: MutableState<Boolean> = mutableStateOf(false)
    val isFailed: State<Boolean> get() = _isFailed

    private val _errorMessage: MutableState<String?> = mutableStateOf(null)
    val errorMessage: State<String?> get() = _errorMessage

    private fun findRandomAudioUrl(): String {
        val audioNumber = Random.nextInt(1, 18)
        return "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-$audioNumber.mp3"
    }

    fun findRandomAudioPlayer(context: Context) {
        resetAudioPlayer()
        val url = findRandomAudioUrl()
        viewModelScope.launch {
            exoPlayerRepository.findRandomAudioPlayer(url, context) { result, error ->
                if (result != null) {
                    _playerState.value = result

                    result.addListener(object : Player.Listener {
                        override fun onPlaybackStateChanged(playbackState: Int) {
                            super.onPlaybackStateChanged(playbackState)
                            when (playbackState) {
                                Player.STATE_READY -> {
                                    val duration = formatAudioDuration(result.duration)
                                    _audioLength.value = duration
                                }
                            }
                        }

                        override fun onIsPlayingChanged(isPlaying: Boolean) {
                            super.onIsPlayingChanged(isPlaying)
                            viewModelScope.launch {
                                if (isPlaying){
                                    while (true) {
                                        delay(1000)
                                        updateAudioProgress(result)
                                    }
                                }

                            }
                        }

                    })

                } else {
                    _errorMessage.value = error
                }
            }
        }
    }

    private fun resetAudioPlayer(){
        _playerState.value?.stop()
        _playerState.value = null
        _audioProgress.value = 0.00f
        _audioLength.value = "0:00"
    }

    private fun updateAudioProgress(player: ExoPlayer) {
        _formatAudioProgress.value = formatAudioDuration(player.currentPosition)
        _audioProgress.value = player.currentPosition.toFloat() / player.duration.toFloat()
    }

    private fun formatAudioDuration(durationInMilliseconds: Long): String {
        val totalSeconds = durationInMilliseconds / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%d:%02d", minutes, seconds)
    }

}