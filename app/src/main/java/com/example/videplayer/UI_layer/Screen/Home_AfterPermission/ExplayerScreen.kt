package com.example.videplayer.UI_layer.Screen.Home_AfterPermission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Composable
fun ExplayerScreen(
    videouri:String,
    title:String? =null
){

    val context = LocalContext.current
    val exoplayer = remember{

        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videouri))
            playWhenReady =  true
            play()
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DisposableEffect(
            AndroidView(
                factory = {
                    PlayerView(context).apply {
                        player = exoplayer
                    }

                },
                update = {
                    it.player =  exoplayer
                }
            )
        ){
            onDispose {
                exoplayer.release()
            }
        }

    }

}