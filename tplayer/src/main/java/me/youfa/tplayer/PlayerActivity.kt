package me.youfa.tplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.tv.material3.LocalContentColor
import androidx.tv.material3.MaterialTheme
import me.youfa.tplayer.data.entities.MovieDetails
import me.youfa.tplayer.ui.screens.VideoPlayerScreen
import me.youfa.tplayer.ui.theme.PlayerViewTheme
import me.youfa.tplayer.utils.Logging

class PlayerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // get video uri from intent
        val videoUri = intent.data
        Logging.log("videoUri: $videoUri")
        val movieDetails = createMovieDetails(videoUri.toString())
        setContent {
            PlayerViewTheme {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface),
                ) {
                    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface) {
                        VideoPlayerScreen(movieDetails, onBackPressed = { finish() })
                    }
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