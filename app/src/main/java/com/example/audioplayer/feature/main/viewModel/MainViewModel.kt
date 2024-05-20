package com.example.audioplayer.feature.main.viewModel

import android.content.Context
import androidx.compose.runtime.FloatState
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import kotlin.random.Random

class MainViewModel: ViewModel() {

    private val _playerState: MutableState<ExoPlayer?> = mutableStateOf(null)
    val playerState: State<ExoPlayer?> get() = _playerState


    private val _audioLength: MutableFloatState = mutableFloatStateOf(0f)
    val audioLength: FloatState get() = _audioLength

    private val _audioProgress = mutableFloatStateOf(0f)
    val audioProgress: FloatState get() = _audioProgress

    private fun findRandomAudioUrl(): String{
        val audioNumber = Random.nextInt(1,18)
        val url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-$audioNumber.mp3"
        return url
    }

    fun findRandomAudioPlayer(context: Context){
        val url = findRandomAudioUrl()
        val player = ExoPlayer.Builder(context).build()
        val mediaItem = MediaItem.fromUri(url)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true
        _playerState.value = player
    }

}