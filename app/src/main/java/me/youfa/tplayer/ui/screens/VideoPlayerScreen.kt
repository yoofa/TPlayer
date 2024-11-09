package me.youfa.tplayer.ui.screens

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesomeMotion
import androidx.compose.material.icons.filled.ClosedCaption
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import me.youfa.tplayer.data.entities.MovieDetails
import me.youfa.tplayer.data.utils.StringConstants
import me.youfa.tplayer.ui.screens.components.VideoPlayerControlsIcon
import me.youfa.tplayer.ui.screens.components.VideoPlayerMainFrame
import me.youfa.tplayer.ui.screens.components.VideoPlayerMediaTitle
import me.youfa.tplayer.ui.screens.components.VideoPlayerMediaTitleType
import me.youfa.tplayer.ui.screens.components.VideoPlayerOverlay
import me.youfa.tplayer.ui.screens.components.VideoPlayerPulse
import me.youfa.tplayer.ui.screens.components.VideoPlayerPulseState
import me.youfa.tplayer.ui.screens.components.VideoPlayerSeeker
import me.youfa.tplayer.ui.screens.components.VideoPlayerState
import me.youfa.tplayer.ui.screens.components.rememberVideoPlayerPulseState
import me.youfa.tplayer.ui.screens.components.rememberVideoPlayerState
import me.youfa.tplayer.ui.utils.handleDPadKeyEvents
import me.youfa.tplayer.utils.Logging
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun VideoPlayerScreen(movieDetails: MovieDetails, onBackPressed: () -> Unit) {
    val context = LocalContext.current
    val videoPlayerState = rememberVideoPlayerState(hideSeconds = 4)
    val pulseState = rememberVideoPlayerPulseState()

    val exoPlayer = rememberExoPlayer(context)
    LaunchedEffect(exoPlayer, movieDetails) {
        exoPlayer.setMediaItem(
            MediaItem.Builder()
                .setUri(movieDetails.videoUri)
                .build()
        )
        exoPlayer.prepare()
    }

    var contentCurrentPosition by remember { mutableLongStateOf(0L) }
    var isPlaying: Boolean by remember { mutableStateOf(exoPlayer.isPlaying) }
    // TODO: Update in a more thoughtful manner
    LaunchedEffect(Unit) {
        while (true) {
            delay(300)
            contentCurrentPosition = exoPlayer.currentPosition
            isPlaying = exoPlayer.isPlaying
        }
    }

    BackHandler(onBack = onBackPressed)

    Box(Modifier.dPadEvents(exoPlayer, videoPlayerState, pulseState).focusable()) {
        AndroidView(
            factory = {
                PlayerView(context).apply { useController = false }
            },
            update = { it.player = exoPlayer },
            onRelease = { exoPlayer.release() }
        )
        val focusRequester = remember { FocusRequester() }

        VideoPlayerOverlay(
            modifier = Modifier.align(Alignment.BottomCenter),
            focusRequester = focusRequester,
            state = videoPlayerState,
            isPlaying = isPlaying,
            centerButton = { VideoPlayerPulse(pulseState) },
            subtitles = { /* TODO Implement subtitles */ },
            controls = {
                VideoPlayerControls(
                    movieDetails = movieDetails,
                    isPlaying = isPlaying,
                    contentCurrentPosition = contentCurrentPosition,
                    exoPlayer = exoPlayer,
                    state = videoPlayerState,
                    focusRequester = focusRequester
                )
            }
        )
    }

}

@Composable
fun VideoPlayerControls(
    movieDetails: MovieDetails,
    isPlaying: Boolean,
    contentCurrentPosition: Long,
    exoPlayer: ExoPlayer,
    state: VideoPlayerState,
    focusRequester: FocusRequester
) {
    val onPlayPauseToggle = { shouldPlay: Boolean ->
        if (shouldPlay) {
            exoPlayer.play()
        } else {
            exoPlayer.pause()
        }
    }

    VideoPlayerMainFrame(
        mediaTitle = {
            VideoPlayerMediaTitle(
                title = movieDetails.name,
                secondaryText = "2024",
                tertiaryText = "youfa",
                type = VideoPlayerMediaTitleType.DEFAULT
            )
        },
        mediaActions = {
            Row(
                modifier = Modifier.padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                VideoPlayerControlsIcon(
                    icon = Icons.Default.AutoAwesomeMotion,
                    state = state,
                    isPlaying = isPlaying,
                    contentDescription = StringConstants
                        .Composable
                        .VideoPlayerControlPlaylistButton,
                )
                VideoPlayerControlsIcon(
                    modifier = Modifier.padding(start = 12.dp),
                    icon = Icons.Default.ClosedCaption,
                    state = state,
                    isPlaying = isPlaying,
                    contentDescription = StringConstants
                        .Composable
                        .VideoPlayerControlClosedCaptionsButton
                )
                VideoPlayerControlsIcon(
                    modifier = Modifier.padding(start = 12.dp),
                    icon = Icons.Default.Settings,
                    state = state,
                    isPlaying = isPlaying,
                    contentDescription = StringConstants
                        .Composable
                        .VideoPlayerControlSettingsButton
                )
            }
        },
        seeker = {
            VideoPlayerSeeker(
                focusRequester,
                state,
                isPlaying,
                onPlayPauseToggle,
                onSeek = { exoPlayer.seekTo(exoPlayer.duration.times(it).toLong()) },
                contentProgress = contentCurrentPosition.milliseconds,
                contentDuration = exoPlayer.duration.milliseconds
            )
        },
        more = null
    )
}

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
private fun rememberExoPlayer(context: Context) = remember {
    ExoPlayer.Builder(context)
        .setSeekForwardIncrementMs(10)
        .setSeekBackIncrementMs(10)
        .setMediaSourceFactory(
            ProgressiveMediaSource.Factory(DefaultDataSource.Factory(context))
        )
        .setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING)
        .build()
        .apply {
            playWhenReady = true
            repeatMode = Player.REPEAT_MODE_ONE
        }
}

private fun Modifier.dPadEvents(
    player: Player,
    videoPlayerState: VideoPlayerState,
    pulseState: VideoPlayerPulseState
): Modifier = this.handleDPadKeyEvents(
    onLeft = {
        Logging.log("VideoPlayerScreen", "onLeft")
        if (!videoPlayerState.controlsVisible) {
            player.seekBack()
            pulseState.setType(VideoPlayerPulse.Type.BACK)
        }
    },
    onRight = {
        Logging.log("VideoPlayerScreen", "onRight")
        if (!videoPlayerState.controlsVisible) {
            player.seekForward()
            pulseState.setType(VideoPlayerPulse.Type.FORWARD)
        }
    },
    onUp = {
        Logging.log("VideoPlayerScreen", "onUp")
        videoPlayerState.showControls() },
    onDown = {
        Logging.log("VideoPlayerScreen", "onDown")
        videoPlayerState.showControls() },
    onEnter = {
        Logging.log("VideoPlayerScreen", "onEnter")
        player.pause()
        videoPlayerState.showControls()
    }
)