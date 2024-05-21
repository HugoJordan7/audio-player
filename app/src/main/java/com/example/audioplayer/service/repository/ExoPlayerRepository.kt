package com.example.audioplayer.service.repository

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

class ExoPlayerRepository {
    suspend fun findRandomAudioPlayer(
        url: String,
        context: Context,
        callback: (result: ExoPlayer?, error: String?) -> Unit
    ){
        try {
            val player = ExoPlayer.Builder(context).build()
            val mediaItem = MediaItem.fromUri(url)
            player.setMediaItem(mediaItem)
            player.prepare()
            callback(player, null)
        } catch (e: Exception){
            callback(null, e.message ?: "Unknown error")
        }
    }

}