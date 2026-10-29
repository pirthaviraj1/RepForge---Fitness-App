package com.example.ui.components

import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.widget.FrameLayout
import android.widget.VideoView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.SlowMotionVideo
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.example.data.model.Exercise
import com.example.ui.theme.CriticalRed
import com.example.ui.theme.KineticNeon
import com.example.ui.theme.MetricCyan
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.voice.CoachVoiceEngine

@Composable
fun OriginalExerciseVideoPlayer(
    exercise: Exercise,
    modifier: Modifier = Modifier,
    voiceEngine: CoachVoiceEngine? = null,
    isCompact: Boolean = false,
    showControls: Boolean = true
) {
    val context = LocalContext.current
    val rawVideoRes = remember(exercise.id) { OfflineExerciseVideoProvider.getVideoResForExercise(exercise) }
    val thumbnailRes = remember(exercise) { RealManExerciseProvider.getDrawableForExercise(exercise) }

    var isPlaying by remember { mutableStateOf(true) }
    var isMuted by remember { mutableStateOf(true) }
    var isVideoReady by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var hasError by remember { mutableStateOf(false) }
    var playbackSpeed by remember { mutableFloatStateOf(1.0f) }
    var isFullscreenOpen by remember { mutableStateOf(false) }
    var retryTrigger by remember { mutableStateOf(0) }

    var videoViewRef by remember { mutableStateOf<VideoView?>(null) }
    var mediaPlayerRef by remember { mutableStateOf<MediaPlayer?>(null) }

    val videoUri = remember(rawVideoRes, retryTrigger) {
        OfflineVideoCache.getVideoUri(context, rawVideoRes)
    }

    // Clean up playback on disposal
    DisposableEffect(rawVideoRes, retryTrigger) {
        onDispose {
            try {
                videoViewRef?.stopPlayback()
            } catch (_: Exception) { }
            videoViewRef = null
            mediaPlayerRef = null
        }
    }

    // Update speed if MediaPlayer is available
    LaunchedEffect(playbackSpeed, mediaPlayerRef) {
        val mp = mediaPlayerRef
        if (mp != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                val params = mp.playbackParams
                params.speed = playbackSpeed
                mp.playbackParams = params
            } catch (_: Exception) { }
        }
    }

    // Toggle Play/Pause on VideoView
    LaunchedEffect(isPlaying) {
        val vv = videoViewRef
        if (vv != null && isVideoReady) {
            try {
                if (isPlaying) {
                    if (!vv.isPlaying) vv.start()
                } else {
                    if (vv.isPlaying) vv.pause()
                }
            } catch (_: Exception) { }
        }
    }

    // Toggle Mute on MediaPlayer
    LaunchedEffect(isMuted, mediaPlayerRef) {
        val mp = mediaPlayerRef
        if (mp != null) {
            try {
                val vol = if (isMuted) 0f else 1f
                mp.setVolume(vol, vol)
            } catch (_: Exception) { }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(SurfaceContainerLowest, RoundedCornerShape(16.dp))
            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(16.dp))
            .padding(if (isCompact) 8.dp else 12.dp)
            .testTag("original_exercise_video_container")
    ) {
        // Video Header with Badges
        if (showControls) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Videocam,
                        contentDescription = null,
                        tint = KineticNeon,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "ORIGINAL VIDEO DEMO",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = KineticNeon
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(KineticNeon.copy(alpha = 0.15f), RoundedCornerShape(20.dp))
                            .border(1.dp, KineticNeon.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                            .padding(horizontal = 7.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "1080P HD",
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace,
                            color = KineticNeon,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Box(
                        modifier = Modifier
                            .background(SurfaceContainerHigh, RoundedCornerShape(20.dp))
                            .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(20.dp))
                            .padding(horizontal = 7.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "${playbackSpeed}X",
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace,
                            color = MetricCyan,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Main Video Playback Viewport
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(if (isCompact) 170.dp else 220.dp)
                .background(Color.Black, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
        ) {
            // Android Native VideoView for Real Video Playback
            if (!hasError) {
                AndroidView(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("native_video_view"),
                    factory = { ctx ->
                        VideoView(ctx).apply {
                            layoutParams = FrameLayout.LayoutParams(
                                FrameLayout.LayoutParams.MATCH_PARENT,
                                FrameLayout.LayoutParams.MATCH_PARENT
                            )
                            setVideoURI(videoUri)
                            setOnPreparedListener { mp ->
                                mediaPlayerRef = mp
                                mp.isLooping = true
                                val vol = if (isMuted) 0f else 1f
                                mp.setVolume(vol, vol)
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    try {
                                        val params = mp.playbackParams
                                        params.speed = playbackSpeed
                                        mp.playbackParams = params
                                    } catch (_: Exception) { }
                                }
                                isLoading = false
                                isVideoReady = true
                                if (isPlaying) {
                                    start()
                                }
                            }
                            setOnErrorListener { _, _, _ ->
                                hasError = true
                                isLoading = false
                                true
                            }
                            videoViewRef = this
                        }
                    },
                    update = { view ->
                        videoViewRef = view
                    }
                )
            }

            // Thumbnail / Loading Fallback Overlay
            if (!isVideoReady || hasError) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = thumbnailRes),
                        contentDescription = "Exercise Preview",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.5f))
                    )
                }
            }

            // Loading Spinner
            if (isLoading && !hasError) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CircularProgressIndicator(
                            color = KineticNeon,
                            modifier = Modifier.size(36.dp),
                            strokeWidth = 3.dp
                        )
                        Text(
                            text = "Loading Original Video...",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            color = Color.White
                        )
                    }
                }
            }

            // Error Overlay with Retry Option
            if (hasError) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .background(Color.Black.copy(alpha = 0.85f), RoundedCornerShape(12.dp))
                            .padding(14.dp)
                    ) {
                        Text(
                            text = "Video Stream Offline",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = CriticalRed,
                            fontFamily = FontFamily.Monospace
                        )
                        Row(
                            modifier = Modifier
                                .background(KineticNeon, RoundedCornerShape(8.dp))
                                .clickable {
                                    hasError = false
                                    isLoading = true
                                    retryTrigger++
                                }
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Retry",
                                tint = Color.Black,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "RELOAD VIDEO",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }
                }
            }

            // Clickable Surface overlay to Toggle Play/Pause
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { isPlaying = !isPlaying }
            )

            // Top Video Status Badge
            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.75f), RoundedCornerShape(16.dp))
                        .border(1.dp, KineticNeon.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(if (isPlaying) KineticNeon else CriticalRed, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isPlaying) "ORIGINAL VIDEO PLAYING" else "VIDEO PAUSED",
                            color = KineticNeon,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Audio Mute/Unmute Toggle
                    IconButton(
                        onClick = { isMuted = !isMuted },
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color.Black.copy(alpha = 0.7f), CircleShape)
                    ) {
                        Icon(
                            imageVector = if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                            contentDescription = if (isMuted) "Unmute" else "Mute",
                            tint = KineticNeon,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    // Fullscreen Modal Launcher
                    IconButton(
                        onClick = { isFullscreenOpen = true },
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color.Black.copy(alpha = 0.7f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Fullscreen,
                            contentDescription = "Fullscreen Video",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            // Big Center Play/Pause Indicator (shown when paused)
            if (!isPlaying && isVideoReady) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(54.dp)
                        .background(Color.Black.copy(alpha = 0.75f), CircleShape)
                        .border(1.5.dp, KineticNeon, CircleShape)
                        .clickable { isPlaying = true },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = KineticNeon,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // Bottom Bar with Exercise Name and Technique Cue
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.90f))
                        )
                    )
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = exercise.name.uppercase(),
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = "Target: ${exercise.targetMuscles}",
                            color = MetricCyan,
                            fontSize = 10.sp
                        )
                    }

                    // Play / Pause pill button
                    Box(
                        modifier = Modifier
                            .background(KineticNeon, RoundedCornerShape(20.dp))
                            .clickable { isPlaying = !isPlaying }
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (isPlaying) "PAUSE" else "PLAY",
                                color = Color.Black,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }
        }

        // Action Toolbar (Speed toggles, Voice Guidance, Restart)
        if (showControls) {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Playback speed selector pills (0.75x Slow-Mo, 1.0x Normal, 1.25x Dynamic)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listOf(0.75f, 1.0f, 1.25f).forEach { speed ->
                        val isSelected = playbackSpeed == speed
                        Box(
                            modifier = Modifier
                                .background(
                                    if (isSelected) KineticNeon else SurfaceContainerHigh,
                                    RoundedCornerShape(6.dp)
                                )
                                .border(
                                    1.dp,
                                    if (isSelected) KineticNeon else Color.White.copy(alpha = 0.12f),
                                    RoundedCornerShape(6.dp)
                                )
                                .clickable { playbackSpeed = speed }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = when (speed) {
                                    0.75f -> "0.75x SLOW"
                                    1.0f -> "1.0x NORMAL"
                                    else -> "1.25x FAST"
                                },
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace,
                                color = if (isSelected) Color.Black else TextSecondary
                            )
                        }
                    }
                }

                // Restart Video Button
                Row(
                    modifier = Modifier
                        .background(SurfaceContainerHigh, RoundedCornerShape(6.dp))
                        .clickable {
                            videoViewRef?.seekTo(0)
                            if (!isPlaying) isPlaying = true
                        }
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Replay,
                        contentDescription = "Restart",
                        tint = MetricCyan,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "REPLAY",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = MetricCyan
                    )
                }
            }
        }
    }

    // Fullscreen HD Video Shorts Player Dialog
    if (isFullscreenOpen) {
        Dialog(
            onDismissRequest = { isFullscreenOpen = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                // Fullscreen Video View
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { ctx ->
                        VideoView(ctx).apply {
                            layoutParams = FrameLayout.LayoutParams(
                                FrameLayout.LayoutParams.MATCH_PARENT,
                                FrameLayout.LayoutParams.MATCH_PARENT
                            )
                            setVideoURI(videoUri)
                            setOnPreparedListener { mp ->
                                mp.isLooping = true
                                val vol = if (isMuted) 0f else 1f
                                mp.setVolume(vol, vol)
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    try {
                                        val params = mp.playbackParams
                                        params.speed = playbackSpeed
                                        mp.playbackParams = params
                                    } catch (_: Exception) { }
                                }
                                start()
                            }
                            setOnErrorListener { _, _, _ ->
                                true
                            }
                        }
                    }
                )

                // Close Button Top-Right
                IconButton(
                    onClick = { isFullscreenOpen = false },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .background(Color.Black.copy(alpha = 0.7f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Fullscreen",
                        tint = Color.White
                    )
                }

                // Fullscreen Header Info Overlay
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.95f))
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
                                text = "ORIGINAL HD EXERCISE DEMONSTRATION",
                                color = KineticNeon,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = exercise.name,
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Black
                            )
                        }

                        Box(
                            modifier = Modifier
                                .background(KineticNeon, RoundedCornerShape(8.dp))
                                .clickable { isFullscreenOpen = false }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "DONE",
                                color = Color.Black,
                                fontWeight = FontWeight.Black,
                                fontSize = 12.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Form Cues: ${exercise.proTips.firstOrNull() ?: "Maintain neutral posture and full range of motion."}",
                        color = Color(0xFFE2E8F0),
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}
