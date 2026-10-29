package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import com.example.data.repository.FitnessRepository
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

@Composable
fun ProfileScreen(
    repository: FitnessRepository,
    voiceEngine: CoachVoiceEngine,
    modifier: Modifier = Modifier
) {
    val history by repository.workoutHistory.collectAsState(initial = emptyList())
    val profile by repository.userProfile.collectAsState(initial = null)

    var isVoiceActive by remember { mutableStateOf(voiceEngine.isVoiceEnabled) }
    var selectedTone by remember { mutableStateOf(voiceEngine.voiceTone) }
    var speechRate by remember { mutableFloatStateOf(voiceEngine.speechRate) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .padding(16.dp)
            .testTag("profile_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // User Profile Header
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, KineticNeon.copy(alpha = 0.3f), RoundedCornerShape(18.dp))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(KineticNeon.copy(alpha = 0.2f))
                            .border(2.dp, KineticNeon, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "A",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = KineticNeon
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Alex Hunter",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .background(KineticNeon.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "PRO TIER",
                                    fontSize = 9.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    color = KineticNeon
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Goal: Hypertrophy & Muscle Strength",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                        Text(
                            text = "7-Day Active Streak • 24 Lifetime Workouts",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            color = MetricCyan
                        )
                    }
                }
            }
        }

        // Body Measurements & Records
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "BIOMETRICS & PR RECORDS",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    BiometricCard(
                        title = "WEIGHT",
                        value = "76.5 KG",
                        target = "Target: 80.0 KG",
                        accentColor = KineticNeon,
                        modifier = Modifier.weight(1f)
                    )
                    BiometricCard(
                        title = "BODY FAT",
                        value = "14.2%",
                        target = "Target: 12.5%",
                        accentColor = MetricCyan,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    BiometricCard(
                        title = "PUSH-UP MAX",
                        value = "38 REPS",
                        target = "+6 reps PR",
                        accentColor = Color(0xFFFFB020),
                        modifier = Modifier.weight(1f)
                    )
                    BiometricCard(
                        title = "PLANK HOLD",
                        value = "120s",
                        target = "Target: 180s",
                        accentColor = Color(0xFFA78BFA),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Voice Coach Settings (Section 6)
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.White.copy(alpha = 0.06f), RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.RecordVoiceOver,
                                contentDescription = null,
                                tint = KineticNeon,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Male AI Voice Guidance",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }

                        Switch(
                            checked = isVoiceActive,
                            onCheckedChange = {
                                isVoiceActive = it
                                voiceEngine.isVoiceEnabled = it
                                if (it) voiceEngine.speak("Voice guidance enabled")
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.Black,
                                checkedTrackColor = KineticNeon
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "COACH VOICE STYLE",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = TextMuted
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Energetic Coach", "Calm Instructional").forEach { style ->
                            val isSel = selectedTone == style
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(
                                        if (isSel) KineticNeon.copy(alpha = 0.2f) else SurfaceContainerHigh,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .border(
                                        1.dp,
                                        if (isSel) KineticNeon else Color.Transparent,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable {
                                        selectedTone = style
                                        voiceEngine.voiceTone = style
                                        voiceEngine.speak("Coach Marcus updated to $style mode")
                                    }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = style,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSel) KineticNeon else TextPrimary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "LANGUAGE & VOCAL PACE",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = TextMuted
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Current: Hinglish / English Natural Speech • Speed: ${speechRate}x",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
            }
        }

        // Achievements & Badges (Section 11)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "FORGE ACHIEVEMENTS",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AchievementBadge("First Workout", "Locked In", KineticNeon, Modifier.weight(1f))
                    AchievementBadge("7-Day Streak", "Discipline", Color(0xFFFFB020), Modifier.weight(1f))
                    AchievementBadge("100 Reps", "Century", MetricCyan, Modifier.weight(1f))
                }
            }
        }

        // Room Database Workout History List
        item {
            Text(
                text = "LOGGED WORKOUT SESSIONS (${history.size})",
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                color = TextMuted
            )
        }

        if (history.isEmpty()) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "No saved workouts yet. Complete 'Today's Forge' to record your first local session in Room DB.",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        modifier = Modifier.padding(14.dp)
                    )
                }
            }
        } else {
            items(history) { entry ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(12.dp))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = entry.routineTitle,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = "${entry.completedExercisesCount} exercises • ${entry.totalSets} sets • ${entry.totalVolumeKg} KG",
                                fontSize = 11.sp,
                                color = TextSecondary,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        Text(
                            text = "${entry.durationSeconds / 60}m ${entry.durationSeconds % 60}s",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            color = KineticNeon
                        )
                    }
                }
            }
        }

        // Privacy Policy & Zero-Cloud Notice (Section 14 & Play Policy)
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        tint = MetricCyan,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "RepForge stores biometric data and workout records locally on your device for absolute privacy.",
                        fontSize = 11.sp,
                        color = TextMuted,
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun BiometricCard(
    title: String,
    value: String,
    target: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(12.dp))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = title,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                color = TextMuted
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Monospace,
                color = accentColor
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = target,
                fontSize = 10.sp,
                color = TextSecondary
            )
        }
    }
}

@Composable
fun AchievementBadge(
    title: String,
    tag: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.border(1.dp, accentColor.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(accentColor.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.MilitaryTech,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                maxLines = 1
            )
            Text(
                text = tag,
                fontSize = 9.sp,
                fontFamily = FontFamily.Monospace,
                color = accentColor
            )
        }
    }
}
