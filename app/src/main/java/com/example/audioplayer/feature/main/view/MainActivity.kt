package com.example.audioplayer.feature.main.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.audioplayer.R
import com.example.audioplayer.feature.main.viewModel.MainViewModel
import com.example.audioplayer.ui.theme.AudioPlayerTheme
import com.example.audioplayer.ui.theme.Purple80
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()
    private val isPlaying: MutableState<Boolean> = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AudioPlayerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RandomAudioPLayer()
                }
            }
        }
    }


    @Composable
    fun RandomAudioPLayer() {
        AudioPlayerTheme {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background),
            ) {
                if (viewModel.isFailed.value) {
                    Toast.makeText(
                        this@MainActivity,
                        viewModel.errorMessage.value,
                        Toast.LENGTH_LONG
                    ).show()
                }
                Row(
                    modifier = Modifier
                        .width(300.dp)
                        .height(100.dp)
                        .background(
                            color = Purple80,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .align(Alignment.Center)
                ) {
                    val playerIcon = if (isPlaying.value) R.drawable.pause else R.drawable.play

                    Image(
                        painter = painterResource(id = playerIcon),
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 10.dp)
                            .size(50.dp)
                            .clickable {
                                isPlaying.value = !isPlaying.value
                                if (isPlaying.value) {
                                    viewModel.playerState.value?.play()
                                } else {
                                    viewModel.playerState.value?.pause()
                                }
                            }
                    )
                    Text(
                        text = if (isPlaying.value) viewModel.formatAudioProgress.value else viewModel.audioLength.value,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 5.dp)
                    )
                    LinearProgressIndicator(
                        progress = viewModel.audioProgress.value,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 20.dp, end = 20.dp)
                    )
                }
                Button(
                    onClick = {
                        viewModel.findRandomAudioPlayer(this@MainActivity)
                        isPlaying.value = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Purple80),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(300.dp)
                        .offset(y = 100.dp)
                ) {
                    Text(text = "Random audio")
                    Image(painter = painterResource(id = R.drawable.dice), contentDescription = "")

                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        AudioPlayerTheme {
            RandomAudioPLayer()
        }
    }

}