package me.youfa.tplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import me.youfa.tplayer.ui.screens.VideoPlayerScreen
import me.youfa.tplayer.data.entities.MovieDetails

import me.youfa.tplayer.ui.theme.TPlayerTheme
import me.youfa.tplayer.utils.Logging

class PlayerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // get video uri from intent
        val videoUri = intent.data
        Logging.log("videoUri: $videoUri")
        val movieDetails = createMovieDetails(videoUri.toString())
        setContent {
            TPlayerTheme {
                    Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    VideoPlayerScreen(movieDetails, onBackPressed = { finish() })
                }
            }
        }
    }

    private fun createMovieDetails(videoUri: String): MovieDetails {
        return MovieDetails(
            id = "movie.id",
            videoUri = videoUri,
            name = "movie.name",
            categories = listOf("Action", "Adventure", "Fantasy", "Comedy"),
            duration = "1h 59m",
        )
    }
}