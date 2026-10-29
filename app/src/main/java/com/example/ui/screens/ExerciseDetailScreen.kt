package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AnimationMode
import com.example.data.model.Exercise
import com.example.ui.components.ExerciseAnimationCanvas
import com.example.ui.components.OriginalExerciseVideoPlayer
import com.example.ui.theme.CriticalRed
import com.example.ui.theme.KineticNeon
import com.example.ui.theme.MetricCyan
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.voice.CoachVoiceEngine
import kotlinx.coroutines.delay

@Composable
fun ExerciseDetailScreen(
    exercise: Exercise,
    voiceEngine: CoachVoiceEngine,
    onBack: () -> Unit,
    onStartWorkoutWithExercise: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Interactive Exercise Timer States (Runs exactly once for chosen duration, Set 1 of 1)
    var targetWorkSeconds by remember { mutableIntStateOf(exercise.workDurationSeconds) }
    val totalSets = 1
    var currentSet by remember { mutableIntStateOf(1) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var timerMode by remember { mutableStateOf("WORK") } // "WORK", "COMPLETED"
    var secondsRemaining by remember { mutableIntStateOf(exercise.workDurationSeconds) }
    var showStopSignalAlert by remember { mutableStateOf(false) }

    LaunchedEffect(showStopSignalAlert) {
        if (showStopSignalAlert) {
            delay(5000)
            showStopSignalAlert = false
        }
    }

    // Live countdown ticker effect - runs once for target duration, then stops completely
    LaunchedEffect(isTimerRunning) {
        while (isTimerRunning && secondsRemaining > 0) {
            delay(1000)
            secondsRemaining--
            if (secondsRemaining in 1..3) {
                voiceEngine.speakCountdownTick(secondsRemaining)
                voiceEngine.triggerVibration()
            }
        }
        if (isTimerRunning && secondsRemaining == 0) {
            // STOP condition reached: Halt immediately, do not loop or repeat
            isTimerRunning = false
            timerMode = "COMPLETED"
            showStopSignalAlert = true
            voiceEngine.speakStopSignal(setNumber = 1, isFinalSet = true, restSeconds = 0)
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .testTag("exercise_detail_screen")
    ) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceContainerLow)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = TextPrimary
                    )
                }
                Text(
                    text = exercise.category.displayName.uppercase(),
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = MetricCyan
                )
            }

            IconButton(
                onClick = {
                    val guideText = "${exercise.name}. ${exercise.instructions.joinToString(" ")}"
                    voiceEngine.speak(guideText)
                },
                modifier = Modifier
                    .size(38.dp)
                    .background(KineticNeon.copy(alpha = 0.2f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.VolumeUp,
                    contentDescription = "Audio Guide",
                    tint = KineticNeon,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title & Duration Tags
            Column {
                Text(
                    text = exercise.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(KineticNeon.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                            .border(1.dp, KineticNeon.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                            .padding(horizontal = 7.dp, vertical = 3.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = null,
                                tint = KineticNeon,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${exercise.workDurationSeconds}S TIMER",
                                color = KineticNeon,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .background(MetricCyan.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                            .border(1.dp, MetricCyan.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                            .padding(horizontal = 7.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "${exercise.restDurationSeconds}S REST",
                            color = MetricCyan,
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Box(
                        modifier = Modifier
                            .background(SurfaceContainerHigh, RoundedCornerShape(6.dp))
                            .padding(horizontal = 7.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "~${exercise.totalEstimatedMinutes}M TOTAL",
                            color = TextSecondary,
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Original Exercise Demonstration Video Player (Real Hardware Video Playback)
            OriginalExerciseVideoPlayer(
                exercise = exercise,
                voiceEngine = voiceEngine,
                showControls = true
            )

            // Interactive Exercise Work & Rest Timer Card
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.5.dp,
                        if (timerMode == "WORK") KineticNeon else MetricCyan,
                        RoundedCornerShape(16.dp)
                    )
                    .testTag("exercise_interactive_timer_card")
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Prominent Visual STOP Signal Alert
                    AnimatedVisibility(visible = showStopSignalAlert) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = CriticalRed.copy(alpha = 0.25f)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(2.dp, CriticalRed, RoundedCornerShape(12.dp))
                                .padding(bottom = 12.dp)
                                .testTag("stop_signal_banner")
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .background(CriticalRed, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("🛑", fontSize = 20.sp)
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "STOP! TIME IS UP!",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Black,
                                        fontFamily = FontFamily.Monospace,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "Exercise complete! Stop now. Great work!",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFFFEE2E2)
                                    )
                                }
                            }
                        }
                    }

                    // Header Bar with Mode and Set tracker (Set 1 of 1)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = null,
                                tint = if (timerMode == "COMPLETED") CriticalRed else KineticNeon,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (timerMode == "COMPLETED") "EXERCISE FINISHED" else "EXERCISE TIMER",
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                color = if (timerMode == "COMPLETED") CriticalRed else KineticNeon
                            )
                        }

                        // Single set badge (Set 1 of 1)
                        Box(
                            modifier = Modifier
                                .background(
                                    if (timerMode == "COMPLETED") KineticNeon else KineticNeon.copy(alpha = 0.2f),
                                    RoundedCornerShape(6.dp)
                                )
                                .border(1.dp, KineticNeon, RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "SET 1 OF 1",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace,
                                color = if (timerMode == "COMPLETED") Color.Black else KineticNeon
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Instruction line: How long to do this exercise
                    Text(
                        text = when (timerMode) {
                            "COMPLETED" -> "Target complete! Stop exercising. Excellent workout!"
                            else -> "Perform ${exercise.name} for ${targetWorkSeconds}s (Set 1 of 1)"
                        },
                        fontSize = 12.sp,
                        color = TextSecondary,
                        fontFamily = FontFamily.Monospace
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Big Countdown Display
                    Text(
                        text = String.format(
                            java.util.Locale.US,
                            "%02d:%02d",
                            secondsRemaining / 60,
                            secondsRemaining % 60
                        ),
                        fontSize = 46.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        color = if (timerMode == "COMPLETED") CriticalRed else KineticNeon
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Progress Bar
                    val maxSec = targetWorkSeconds
                    val progress = if (maxSec > 0) (secondsRemaining.toFloat() / maxSec.toFloat()).coerceIn(0f, 1f) else 0f
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(CircleShape),
                        color = if (timerMode == "COMPLETED") CriticalRed else KineticNeon,
                        trackColor = SurfaceContainerLowest
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Quick duration presets (30s, 45s, 60s, 90s)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        listOf(30, 45, 60, 90).forEach { sec ->
                            val isSelected = targetWorkSeconds == sec
                            Box(
                                modifier = Modifier
                                    .background(
                                        if (isSelected) KineticNeon.copy(alpha = 0.25f) else SurfaceContainerHigh,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .border(
                                        1.dp,
                                        if (isSelected) KineticNeon else Color.White.copy(alpha = 0.08f),
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable {
                                        targetWorkSeconds = sec
                                        if (!isTimerRunning) {
                                            secondsRemaining = sec
                                            timerMode = "WORK"
                                        }
                                    }
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Text(
                                    text = "${sec}s",
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) KineticNeon else TextPrimary
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))

                    // Timer Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Play / Pause Button
                        Button(
                            onClick = {
                                if (secondsRemaining == 0 || timerMode == "COMPLETED") {
                                    secondsRemaining = targetWorkSeconds
                                    timerMode = "WORK"
                                }
                                isTimerRunning = !isTimerRunning
                                if (isTimerRunning) {
                                    voiceEngine.speakWorkTimerStart(exercise.name, secondsRemaining, 1)
                                }
                            },
                            modifier = Modifier
                                .weight(1.3f)
                                .height(44.dp)
                                .testTag("exercise_timer_toggle_btn"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (timerMode == "COMPLETED") CriticalRed else KineticNeon,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (isTimerRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isTimerRunning) "PAUSE" else "START TIMER",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }

                        // Reset Button
                        OutlinedButton(
                            onClick = {
                                isTimerRunning = false
                                timerMode = "WORK"
                                secondsRemaining = targetWorkSeconds
                                showStopSignalAlert = false
                            },
                            modifier = Modifier
                                .weight(0.9f)
                                .height(44.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Reset Timer",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("RESET", fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                        }

                        // Stop Button
                        Button(
                            onClick = {
                                isTimerRunning = false
                                timerMode = "COMPLETED"
                                secondsRemaining = 0
                                showStopSignalAlert = true
                                voiceEngine.speakStopSignal(1, true, 0)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SurfaceContainerHigh,
                                contentColor = TextPrimary
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "STOP",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }

            // Primary Target Muscles
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.White.copy(alpha = 0.06f), RoundedCornerShape(14.dp))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "ANATOMICAL TARGET MUSCLES",
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = MetricCyan
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Primary: ${exercise.primaryMuscles.joinToString(", ")}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = KineticNeon
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Secondary & Stabilizers: ${exercise.secondaryMuscles.joinToString(", ")}",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
            }

            // Step-by-Step Instructions
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.White.copy(alpha = 0.06f), RoundedCornerShape(14.dp))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "EXECUTION GUIDE",
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    exercise.instructions.forEachIndexed { idx, step ->
                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = "${idx + 1}.",
                                fontSize = 12.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                color = KineticNeon
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = step,
                                fontSize = 13.sp,
                                color = TextPrimary,
                                lineHeight = 19.sp
                            )
                        }
                    }
                }
            }

            // Coach Pro Tips (Hinglish / Practical)
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, KineticNeon.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = KineticNeon,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "COACH MARCUS PRO TIPS",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = KineticNeon
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    exercise.proTips.forEach { tip ->
                        Row(
                            modifier = Modifier.padding(vertical = 3.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(text = "•", color = KineticNeon, modifier = Modifier.padding(end = 6.dp))
                            Text(text = tip, fontSize = 12.sp, color = TextPrimary, lineHeight = 18.sp)
                        }
                    }
                }
            }

            // Common Mistakes Red Flags (Section 4 & 5)
            Card(
                colors = CardDefaults.cardColors(containerColor = CriticalRed.copy(alpha = 0.08f)),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CriticalRed.copy(alpha = 0.35f), RoundedCornerShape(14.dp))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = CriticalRed,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "COMMON MISTAKES TO AVOID",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = CriticalRed
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    exercise.commonMistakes.forEach { mistake ->
                        Row(
                            modifier = Modifier.padding(vertical = 3.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(text = "✕", color = CriticalRed, modifier = Modifier.padding(end = 6.dp))
                            Text(text = mistake, fontSize = 12.sp, color = TextPrimary, lineHeight = 18.sp)
                        }
                    }
                }
            }

            // Beginner Regression / Variation
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerHigh),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "BEGINNER REGRESSION VARIATION",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = MetricCyan
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = exercise.beginnerVariation,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                }
            }

            // CTA Button: Start Set
            Button(
                onClick = onStartWorkoutWithExercise,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("start_exercise_set_btn"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = KineticNeon,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "START THIS EXERCISE",
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
