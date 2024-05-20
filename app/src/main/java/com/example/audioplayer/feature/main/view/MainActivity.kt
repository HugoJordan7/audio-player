package com.example.audioplayer.feature.main.view

import android.content.Context
import android.os.Bundle
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    private val isPlayer = mutableStateOf(false)
    private val audioLength = mutableFloatStateOf(0f)
    private val audioProgress = mutableFloatStateOf(0f)

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
                Button(
                    onClick = { viewModel.findRandomAudioPlayer(this@MainActivity) },
                    colors = ButtonDefaults.buttonColors(containerColor = Purple80),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(300.dp)
                        .offset(y = 100.dp)
                ) {
                    Text(text = "Random audio")
                    Image(painter = painterResource(id = R.drawable.dice), contentDescription = "")

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
                    val playerIcon = if (isPlayer.value) R.drawable.pause else R.drawable.play

                    Image(
                        painter = painterResource(id = playerIcon),
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 10.dp)
                            .size(50.dp)
                            .clickable {
                                isPlayer.value = !isPlayer.value
                            }
                    )
                    Text(
                        text = "0:20",
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 5.dp)
                    )
                    LinearProgressIndicator(
                        progress = audioProgress.floatValue,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 20.dp, end = 20.dp)
                    )
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