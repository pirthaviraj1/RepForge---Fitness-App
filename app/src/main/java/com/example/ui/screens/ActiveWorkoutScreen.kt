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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.VolumeUp
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.data.model.WorkoutExerciseItem
import com.example.data.model.WorkoutRoutine
import com.example.data.repository.FitnessRepository
import com.example.ui.components.ExerciseAnimationCanvas
import com.example.ui.components.OriginalExerciseVideoPlayer
import com.example.ui.theme.CriticalRed
import com.example.ui.theme.KineticNeon
import com.example.ui.theme.KineticNeonLight
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
import kotlinx.coroutines.launch

@Composable
fun ActiveWorkoutScreen(
    routine: WorkoutRoutine,
    repository: FitnessRepository,
    voiceEngine: CoachVoiceEngine,
    onFinishWorkout: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var currentExerciseIndex by remember { mutableIntStateOf(0) }
    var currentSetNumber by remember { mutableIntStateOf(1) }
    var currentRepsCompleted by remember { mutableIntStateOf(0) }
    var workoutDurationSeconds by remember { mutableIntStateOf(0) }
    var isWorkoutActive by remember { mutableStateOf(true) }

    val currentItem = routine.exercises.getOrNull(currentExerciseIndex) ?: routine.exercises.first()
    val totalExercises = routine.exercises.size

    // Active Exercise Work Countdown Timer
    var exerciseSecondsRemaining by remember { mutableIntStateOf(currentItem.workDurationSeconds) }
    var isExerciseTimerActive by remember { mutableStateOf(false) }
    var showScheduleDrawer by remember { mutableStateOf(false) }
    var showStopSignalAlert by remember { mutableStateOf(false) }

    LaunchedEffect(showStopSignalAlert) {
        if (showStopSignalAlert) {
            delay(4000)
            showStopSignalAlert = false
        }
    }

    // Rest Timer State
    var isResting by remember { mutableStateOf(false) }
    var restSecondsRemaining by remember { mutableIntStateOf(currentItem.restDurationSeconds) }
    var totalRestDuration by remember { mutableIntStateOf(currentItem.restDurationSeconds) }
    var isRestPaused by remember { mutableStateOf(false) }

    // Summary modal on finish
    var isCompletedModalOpen by remember { mutableStateOf(false) }

    // Welcome speech on workout launch
    LaunchedEffect(Unit) {
        voiceEngine.speakWorkoutStart(routine.title)
    }

    // Workout global duration ticker
    LaunchedEffect(isWorkoutActive) {
        while (isWorkoutActive) {
            delay(1000)
            workoutDurationSeconds++
        }
    }

    // Active Exercise Work Countdown Timer Loop
    LaunchedEffect(isExerciseTimerActive, isResting) {
        if (isExerciseTimerActive && !isResting) {
            while (exerciseSecondsRemaining > 0 && isExerciseTimerActive && !isResting) {
                delay(1000)
                exerciseSecondsRemaining--
                if (exerciseSecondsRemaining in 1..3) {
                    voiceEngine.speakCountdownTick(exerciseSecondsRemaining)
                    voiceEngine.triggerVibration()
                }
            }
            if (exerciseSecondsRemaining <= 0 && isExerciseTimerActive && !isResting) {
                isExerciseTimerActive = false
                showStopSignalAlert = true
                val isFinalSet = currentSetNumber >= currentItem.targetSets && currentExerciseIndex >= totalExercises - 1
                voiceEngine.speakStopSignal(currentSetNumber, isFinalSet, currentItem.restDurationSeconds)
                // Auto trigger recovery rest
                if (currentSetNumber < currentItem.targetSets) {
                    currentSetNumber++
                    currentRepsCompleted = 0
                    restSecondsRemaining = currentItem.restDurationSeconds
                    totalRestDuration = currentItem.restDurationSeconds
                    isResting = true
                    isRestPaused = false
                    exerciseSecondsRemaining = currentItem.workDurationSeconds
                    voiceEngine.speakRestCountdown(currentItem.restDurationSeconds)
                } else {
                    if (currentExerciseIndex < totalExercises - 1) {
                        currentExerciseIndex++
                        currentSetNumber = 1
                        currentRepsCompleted = 0
                        restSecondsRemaining = 45
                        totalRestDuration = 45
                        isResting = true
                        isRestPaused = false
                        exerciseSecondsRemaining = routine.exercises[currentExerciseIndex].workDurationSeconds
                        voiceEngine.speakRestCountdown(45)
                    } else {
                        isWorkoutActive = false
                        isCompletedModalOpen = true
                        voiceEngine.speakWorkoutComplete(totalExercises, 40)
                        voiceEngine.triggerVibration()
                    }
                }
            }
        }
    }

    // Smart Rest Timer countdown loop
    LaunchedEffect(isResting, isRestPaused) {
        if (isResting && !isRestPaused) {
            while (restSecondsRemaining > 0 && isResting && !isRestPaused) {
                delay(1000)
                restSecondsRemaining--
                if (restSecondsRemaining == 3) {
                    voiceEngine.triggerVibration()
                }
            }
            if (restSecondsRemaining <= 0 && isResting) {
                isResting = false
                exerciseSecondsRemaining = currentItem.workDurationSeconds
                voiceEngine.speakRestComplete()
                voiceEngine.triggerVibration()
            }
        }
    }

    // Speak exercise intro and reset work timer when exercise changes
    LaunchedEffect(currentExerciseIndex) {
        exerciseSecondsRemaining = currentItem.workDurationSeconds
        isExerciseTimerActive = false
        currentRepsCompleted = 0
        voiceEngine.speakExerciseIntro(
            currentItem.exercise.name,
            currentItem.targetSets,
            currentItem.targetReps,
            currentItem.isTimeBased
        )
    }

    // Reset exercise timer when set changes
    LaunchedEffect(currentSetNumber) {
        exerciseSecondsRemaining = currentItem.workDurationSeconds
        isExerciseTimerActive = false
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .testTag("active_workout_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Header Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(38.dp)
                        .background(SurfaceContainerHigh, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Exit Workout",
                        tint = TextPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "WORKOUT SESSION",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = KineticNeon
                    )
                    Text(
                        text = String.format(
                            java.util.Locale.US,
                            "%02d:%02d",
                            workoutDurationSeconds / 60,
                            workoutDurationSeconds % 60
                        ),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Monospace,
                        color = TextPrimary
                    )
                }

                // Voice Guide Mute/Unmute Toggle
                IconButton(
                    onClick = {
                        voiceEngine.isVoiceEnabled = !voiceEngine.isVoiceEnabled
                        if (voiceEngine.isVoiceEnabled) {
                            voiceEngine.speak("Voice guide active")
                        }
                    },
                    modifier = Modifier
                        .size(38.dp)
                        .background(
                            if (voiceEngine.isVoiceEnabled) KineticNeon.copy(alpha = 0.2f) else SurfaceContainerHigh,
                            CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Voice Guide",
                        tint = if (voiceEngine.isVoiceEnabled) KineticNeon else TextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Exercise Title & Category Chip
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "EXERCISE ${currentExerciseIndex + 1} OF $totalExercises",
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = MetricCyan
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = currentItem.exercise.name.uppercase(),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = TextPrimary
                    )
                }

                Box(
                    modifier = Modifier
                        .background(SurfaceContainerHigh, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "${currentItem.targetSets} SETS × ${currentItem.targetReps} REPS",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = KineticNeon
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Original Exercise Demonstration Video Player (Hardware Video Playback)
            OriginalExerciseVideoPlayer(
                exercise = currentItem.exercise,
                voiceEngine = voiceEngine,
                showControls = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Form Guide Text Banner
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(12.dp))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = KineticNeon,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = currentItem.exercise.instructions.firstOrNull()
                            ?: "Keep your body straight. Brace your core. Lower yourself with control.",
                        fontSize = 12.sp,
                        color = TextPrimary,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Active Tracker: Set & Reps Controls
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, KineticNeon.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // STOP Signal Visual Banner
                    AnimatedVisibility(visible = showStopSignalAlert) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = CriticalRed.copy(alpha = 0.25f)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(2.dp, CriticalRed, RoundedCornerShape(12.dp))
                                .padding(bottom = 12.dp)
                                .testTag("active_stop_signal_banner")
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
                                        text = "Stop exercising! Time is up. Set completed. Great job!",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFFFEE2E2)
                                    )
                                }
                            }
                        }
                    }

                    // Set Indicator Tabs (e.g. Set 1 of 3)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "CURRENT SET",
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = TextMuted
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            (1..currentItem.targetSets).forEach { sNum ->
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .background(
                                            when {
                                                sNum < currentSetNumber -> KineticNeon
                                                sNum == currentSetNumber -> KineticNeon.copy(alpha = 0.3f)
                                                else -> SurfaceContainerHigh
                                            },
                                            CircleShape
                                        )
                                        .border(
                                            1.dp,
                                            if (sNum == currentSetNumber) KineticNeon else Color.Transparent,
                                            CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "$sNum",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (sNum < currentSetNumber) Color.Black else TextPrimary
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Big Repetition Counter
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                if (currentRepsCompleted > 0) currentRepsCompleted--
                            },
                            modifier = Modifier
                                .size(48.dp)
                                .background(SurfaceContainerHigh, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Remove,
                                contentDescription = "Decrement Reps",
                                tint = TextPrimary
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "$currentRepsCompleted",
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Black,
                                fontFamily = FontFamily.Monospace,
                                color = KineticNeon
                            )
                            Text(
                                text = "OF ${currentItem.targetReps} REPETITIONS",
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                color = TextSecondary
                            )
                        }

                        IconButton(
                            onClick = {
                                currentRepsCompleted++
                                voiceEngine.speakRepCount(currentRepsCompleted)
                                voiceEngine.triggerVibration()
                            },
                            modifier = Modifier
                                .size(48.dp)
                                .background(SurfaceContainerHigh, CircleShape)
                                .testTag("increment_rep_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Increment Reps",
                                tint = KineticNeon
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Set Complete CTA Button -> Triggers Smart Rest Timer
                    Button(
                        onClick = {
                            if (currentSetNumber < currentItem.targetSets) {
                                currentSetNumber++
                                currentRepsCompleted = 0
                                // Trigger 30s rest timer
                                restSecondsRemaining = 30
                                totalRestDuration = 30
                                isResting = true
                                isRestPaused = false
                                voiceEngine.speakRestCountdown(30)
                            } else {
                                // Exercise completed! Advance or finish
                                if (currentExerciseIndex < totalExercises - 1) {
                                    currentExerciseIndex++
                                    currentSetNumber = 1
                                    currentRepsCompleted = 0
                                    restSecondsRemaining = 45
                                    totalRestDuration = 45
                                    isResting = true
                                    voiceEngine.speakRestCountdown(45)
                                } else {
                                    // Full workout done!
                                    isWorkoutActive = false
                                    isCompletedModalOpen = true
                                    voiceEngine.speakWorkoutComplete(totalExercises, 40)
                                    voiceEngine.triggerVibration()
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("complete_set_btn"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = KineticNeon,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (currentSetNumber < currentItem.targetSets) "COMPLETE SET $currentSetNumber"
                            else if (currentExerciseIndex < totalExercises - 1) "COMPLETE EXERCISE & REST"
                            else "FINISH WORKOUT",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Rest Timer Card (Shown when Resting)
            AnimatedVisibility(visible = isResting) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerHigh),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.5.dp, MetricCyan, RoundedCornerShape(16.dp))
                        .testTag("rest_timer_banner")
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "⚡ SMART RECOVERY REST",
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                color = MetricCyan
                            )
                            Text(
                                text = "TAKE DEEP BREATHS",
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace,
                                color = TextMuted
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = String.format(
                                java.util.Locale.US,
                                "00:%02d",
                                restSecondsRemaining
                            ),
                            fontSize = 38.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace,
                            color = MetricCyan
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        val restProgress = restSecondsRemaining.toFloat() / totalRestDuration.toFloat()
                        LinearProgressIndicator(
                            progress = { restProgress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(CircleShape),
                            color = MetricCyan,
                            trackColor = SurfaceContainerLowest
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Controls: Pause, +15s, Skip Rest
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            OutlinedButton(
                                onClick = { isRestPaused = !isRestPaused },
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(
                                    imageVector = if (isRestPaused) Icons.Default.PlayArrow else Icons.Default.Pause,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (isRestPaused) "RESUME" else "PAUSE",
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                            }

                            OutlinedButton(
                                onClick = {
                                    restSecondsRemaining += 15
                                    totalRestDuration += 15
                                },
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "+15s REST",
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                            }

                            Button(
                                onClick = {
                                    isResting = false
                                    voiceEngine.speakRestComplete()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MetricCyan,
                                    contentColor = Color.Black
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.testTag("skip_rest_button")
                            ) {
                                Text(
                                    text = "SKIP REST",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Navigation between Exercises (Previous / Next / End)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = {
                        if (currentExerciseIndex > 0) {
                            currentExerciseIndex--
                            currentSetNumber = 1
                            currentRepsCompleted = 0
                            isResting = false
                        }
                    },
                    enabled = currentExerciseIndex > 0,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("PREVIOUS", fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                }

                Button(
                    onClick = {
                        isWorkoutActive = false
                        isCompletedModalOpen = true
                        voiceEngine.speakWorkoutComplete(totalExercises, 36)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SurfaceContainerHigh,
                        contentColor = TextPrimary
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("FINISH WORKOUT", fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                }

                OutlinedButton(
                    onClick = {
                        if (currentExerciseIndex < totalExercises - 1) {
                            currentExerciseIndex++
                            currentSetNumber = 1
                            currentRepsCompleted = 0
                            isResting = false
                        }
                    },
                    enabled = currentExerciseIndex < totalExercises - 1,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("NEXT", fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Completion Dialog / Summary Overlay (Section 7)
        if (isCompletedModalOpen) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.88f))
                    .clickable { /* prevent clicks through */ }
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, KineticNeon, RoundedCornerShape(20.dp))
                        .testTag("workout_summary_card")
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(KineticNeon.copy(alpha = 0.2f), CircleShape)
                                .border(2.dp, KineticNeon, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = null,
                                tint = KineticNeon,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "WORKOUT COMPLETE!",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace,
                            color = KineticNeon
                        )
                        Text(
                            text = routine.title,
                            fontSize = 14.sp,
                            color = TextPrimary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Stats Summary 2x2
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            SummaryStatItem("DURATION", "${workoutDurationSeconds / 60}m ${workoutDurationSeconds % 60}s")
                            SummaryStatItem("EXERCISES", "$totalExercises DONE")
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            SummaryStatItem("SETS FINISHED", "${totalExercises * 3}")
                            SummaryStatItem("TOTAL VOLUME", "1,420 KG")
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = {
                                // Save to Room DB asynchronously
                                scope.launch {
                                    repository.saveCompletedWorkout(
                                        routineTitle = routine.title,
                                        durationSeconds = workoutDurationSeconds,
                                        completedExercisesCount = totalExercises,
                                        totalSets = totalExercises * 3,
                                        totalReps = 40,
                                        totalVolumeKg = 1420
                                    )
                                }
                                onFinishWorkout()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("save_and_exit_btn"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = KineticNeon,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "LOG & RETURN HOME",
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryStatItem(label: String, value: String) {
    Column(
        modifier = Modifier
            .background(SurfaceContainerHigh, RoundedCornerShape(8.dp))
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace,
            color = TextMuted
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            color = TextPrimary
        )
    }
}
