package com.example.ui.components

import android.media.MediaPlayer
import android.media.PlaybackParams
import android.net.Uri
import android.os.Build
import android.widget.FrameLayout
import android.widget.VideoView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.SlowMotionVideo
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.data.model.AnimationMode
import com.example.data.model.Exercise
import com.example.ui.theme.KineticNeon
import com.example.ui.theme.MetricCyan
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.voice.CoachVoiceEngine
import kotlinx.coroutines.delay

@Composable
fun ExerciseAnimationCanvas(
    exercise: Exercise,
    modifier: Modifier = Modifier,
    initialMode: AnimationMode = AnimationMode.NORMAL,
    showControls: Boolean = true
) {
    val context = LocalContext.current
    val voiceEngine = remember { CoachVoiceEngine(context) }
    var isPlaying by remember { mutableStateOf(true) }
    var playbackSpeed by remember { mutableFloatStateOf(1.0f) }
    var isFullscreenVideoOpen by remember { mutableStateOf(false) }
    var mediaPlayerRef by remember { mutableStateOf<MediaPlayer?>(null) }
    var videoProgress by remember { mutableFloatStateOf(0f) }

    val rawVideoRes = remember(exercise.id) {
        OfflineExerciseVideoProvider.getVideoResForExercise(exercise)
    }

    // Ticker to track video progress and phase cues
    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            delay(100)
            mediaPlayerRef?.let { mp ->
                try {
                    val current = mp.currentPosition
                    val duration = mp.duration.coerceAtLeast(1)
                    videoProgress = (current.toFloat() / duration.toFloat()).coerceIn(0f, 1f)
                } catch (_: Exception) {
                }
            }
        }
    }

    val (phaseTitle, phaseDesc) = remember(videoProgress) {
        OfflineExerciseVideoProvider.getPhaseInfo(videoProgress)
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.5.dp, KineticNeon.copy(alpha = 0.35f), RoundedCornerShape(20.dp))
            .testTag("offline_exercise_video_container")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header Bar: Video Status & Offline Indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(KineticNeon, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "ORIGINAL OFFLINE VIDEO",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        color = KineticNeon,
                        letterSpacing = 1.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .background(SurfaceContainerHigh, RoundedCornerShape(8.dp))
                        .border(1.dp, MetricCyan.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "100% OFFLINE • NO INTERNET",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = MetricCyan
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Main Video Display Box (Native Android VideoView)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Black)
                    .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(16.dp))
                    .clickable { isPlaying = !isPlaying }
                    .testTag("offline_video_viewport"),
                contentAlignment = Alignment.Center
            ) {
                key(exercise.id) {
                    AndroidView(
                        factory = { ctx ->
                            val frameLayout = FrameLayout(ctx)
                            val videoView = VideoView(ctx).apply {
                                val videoUri = OfflineVideoCache.getVideoUri(ctx, rawVideoRes)
                                setVideoURI(videoUri)
                                setOnPreparedListener { mp ->
                                    mediaPlayerRef = mp
                                    mp.isLooping = true
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        try {
                                            mp.playbackParams = mp.playbackParams.setSpeed(playbackSpeed)
                                        } catch (_: Exception) {
                                        }
                                    }
                                    if (isPlaying) {
                                        start()
                                    }
                                }
                                setOnErrorListener { _, _, _ ->
                                    true
                                }
                            }
                            frameLayout.addView(
                                videoView,
                                FrameLayout.LayoutParams(
                                    FrameLayout.LayoutParams.MATCH_PARENT,
                                    FrameLayout.LayoutParams.MATCH_PARENT
                                )
                            )
                            frameLayout
                        },
                        update = { frameLayout ->
                            val videoView = frameLayout.getChildAt(0) as? VideoView
                            videoView?.let { vv ->
                                if (isPlaying) {
                                    if (!vv.isPlaying) vv.start()
                                } else {
                                    if (vv.isPlaying) vv.pause()
                                }
                            }
                            mediaPlayerRef?.let { mp ->
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    try {
                                        mp.playbackParams = mp.playbackParams.setSpeed(playbackSpeed)
                                    } catch (_: Exception) {
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                DisposableEffect(exercise.id) {
                    onDispose {
                        try {
                            mediaPlayerRef?.stop()
                            mediaPlayerRef?.release()
                            mediaPlayerRef = null
                        } catch (_: Exception) {
                        }
                    }
                }

                // Top Badge Gradient Overlay
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Black.copy(alpha = 0.7f), Color.Transparent)
                            )
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${exercise.name.uppercase()} • HD 60FPS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            color = Color.White
                        )
                        Box(
                            modifier = Modifier
                                .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(6.dp))
                                .border(1.dp, KineticNeon.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "OFFLINE",
                                fontSize = 9.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Black,
                                color = KineticNeon
                            )
                        }
                    }
                }

                // Paused Play Overlay Button
                if (!isPlaying) {
                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .background(Color.Black.copy(alpha = 0.75f), CircleShape)
                            .border(2.dp, KineticNeon, CircleShape)
                            .clickable { isPlaying = true }
                            .testTag("video_overlay_play_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play Video",
                            tint = KineticNeon,
                            modifier = Modifier.size(38.dp)
                        )
                    }
                }

                // Bottom Gradient Scrim with Real-time Phase
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f))
                            )
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = phaseTitle,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                fontFamily = FontFamily.Monospace,
                                color = KineticNeon
                            )
                            Text(
                                text = phaseDesc,
                                fontSize = 10.sp,
                                color = Color.White.copy(alpha = 0.85f),
                                maxLines = 1
                            )
                        }

                        IconButton(
                            onClick = { isFullscreenVideoOpen = true },
                            modifier = Modifier
                                .size(34.dp)
                                .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                                .border(1.dp, MetricCyan.copy(alpha = 0.5f), CircleShape)
                                .testTag("fullscreen_video_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Fullscreen,
                                contentDescription = "Fullscreen",
                                tint = MetricCyan,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            if (showControls) {
                Spacer(modifier = Modifier.height(12.dp))

                // Playback Controls Row: Play/Pause, Replay, Speed, Audio
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Play / Pause Button
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IconButton(
                            onClick = { isPlaying = !isPlaying },
                            modifier = Modifier
                                .size(42.dp)
                                .background(
                                    if (isPlaying) MetricCyan.copy(alpha = 0.2f) else KineticNeon.copy(alpha = 0.2f),
                                    CircleShape
                                )
                                .border(
                                    1.dp,
                                    if (isPlaying) MetricCyan else KineticNeon,
                                    CircleShape
                                )
                                .testTag("video_play_pause_button")
                        ) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (isPlaying) "Pause" else "Play",
                                tint = if (isPlaying) MetricCyan else KineticNeon,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        // Replay Button (rewind to start)
                        IconButton(
                            onClick = {
                                try {
                                    mediaPlayerRef?.seekTo(0)
                                    isPlaying = true
                                } catch (_: Exception) {
                                }
                            },
                            modifier = Modifier
                                .size(42.dp)
                                .background(SurfaceContainerHigh, CircleShape)
                                .border(1.dp, Color.White.copy(alpha = 0.15f), CircleShape)
                                .testTag("video_replay_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Replay,
                                contentDescription = "Replay",
                                tint = TextPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    // Playback Speed Selector (0.5x, 1.0x, 1.5x)
                    Row(
                        modifier = Modifier
                            .background(SurfaceContainerLow, RoundedCornerShape(10.dp))
                            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(10.dp))
                            .padding(2.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        listOf(0.5f to "0.5x", 1.0f to "1.0x", 1.5f to "1.5x").forEach { (speed, label) ->
                            val isSelected = playbackSpeed == speed
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) KineticNeon else Color.Transparent)
                                    .clickable {
                                        playbackSpeed = speed
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            try {
                                                mediaPlayerRef?.playbackParams =
                                                    mediaPlayerRef?.playbackParams?.setSpeed(speed)
                                                        ?: PlaybackParams().setSpeed(speed)
                                            } catch (_: Exception) {
                                            }
                                        }
                                    }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = label,
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) Color.Black else TextSecondary
                                )
                            }
                        }
                    }

                    // Audio Voice Cue Button
                    IconButton(
                        onClick = {
                            val audioGuide = "${exercise.name}. ${exercise.instructions.firstOrNull() ?: ""}"
                            voiceEngine.speak(audioGuide)
                        },
                        modifier = Modifier
                            .size(42.dp)
                            .background(KineticNeon.copy(alpha = 0.15f), CircleShape)
                            .border(1.dp, KineticNeon.copy(alpha = 0.4f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Audio Cue",
                            tint = KineticNeon,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }

    // Fullscreen Offline Video Dialog
    if (isFullscreenVideoOpen) {
        Dialog(
            onDismissRequest = { isFullscreenVideoOpen = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                // Video in Center
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { isPlaying = !isPlaying },
                    contentAlignment = Alignment.Center
                ) {
                    key(exercise.id) {
                        AndroidView(
                            factory = { ctx ->
                                val frameLayout = FrameLayout(ctx)
                                val videoView = VideoView(ctx).apply {
                                    val videoUri = OfflineVideoCache.getVideoUri(ctx, rawVideoRes)
                                    setVideoURI(videoUri)
                                    setOnPreparedListener { mp ->
                                        mediaPlayerRef = mp
                                        mp.isLooping = true
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            try {
                                                mp.playbackParams = mp.playbackParams.setSpeed(playbackSpeed)
                                            } catch (_: Exception) {
                                            }
                                        }
                                        start()
                                    }
                                    setOnErrorListener { _, _, _ ->
                                        true
                                    }
                                }
                                frameLayout.addView(
                                    videoView,
                                    FrameLayout.LayoutParams(
                                        FrameLayout.LayoutParams.MATCH_PARENT,
                                        FrameLayout.LayoutParams.MATCH_PARENT
                                    )
                                )
                                frameLayout
                            },
                            update = { frameLayout ->
                                val videoView = frameLayout.getChildAt(0) as? VideoView
                                videoView?.let { vv ->
                                    if (isPlaying) {
                                        if (!vv.isPlaying) vv.start()
                                    } else {
                                        if (vv.isPlaying) vv.pause()
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                // Top Header in Fullscreen
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Black.copy(alpha = 0.8f), Color.Transparent)
                            )
                        )
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = exercise.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                        Text(
                            text = "OFFLINE HD VIDEO • 1080P",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = KineticNeon
                        )
                    }

                    IconButton(
                        onClick = { isFullscreenVideoOpen = false },
                        modifier = Modifier
                            .size(38.dp)
                            .background(Color.White.copy(alpha = 0.2f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.White
                        )
                    }
                }

                // Bottom Overlay in Fullscreen
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.9f))
                            )
                        )
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = phaseTitle,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                fontFamily = FontFamily.Monospace,
                                color = KineticNeon
                            )
                            Text(
                                text = phaseDesc,
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }

                        IconButton(
                            onClick = { isPlaying = !isPlaying },
                            modifier = Modifier
                                .size(48.dp)
                                .background(KineticNeon, CircleShape)
                        ) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (isPlaying) "Pause" else "Play",
                                tint = Color.Black,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
