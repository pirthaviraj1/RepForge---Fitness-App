package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.model.WorkoutRoutine
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

@Composable
fun HomeScreen(
    todayRoutine: WorkoutRoutine,
    onStartWorkout: () -> Unit,
    onOpenExerciseLibrary: () -> Unit,
    onOpenWorkouts: () -> Unit,
    onOpenNutrition: () -> Unit,
    onOpenProfile: () -> Unit,
    onOpenAICoach: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))

            // Header (RepForge mark, AI Coach Pill, Notifications, Avatar)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("home_header"),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_repforge_mark),
                        contentDescription = "RepForge Mark",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(36.dp)
                    )
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "REPFORGE",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 0.5.sp,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .background(KineticNeon.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                                    .border(1.dp, KineticNeon.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 5.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "AI COACH",
                                    color = KineticNeon,
                                    fontSize = 9.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = "Train Smart. Build Strength.",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box {
                        IconButton(
                            onClick = onOpenAICoach,
                            modifier = Modifier
                                .size(38.dp)
                                .background(SurfaceContainerHigh, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = TextPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        // Notification alert dot
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(KineticNeon, CircleShape)
                                .align(Alignment.TopEnd)
                        )
                    }

                    // User Profile Avatar
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(KineticNeon.copy(alpha = 0.2f))
                            .border(1.5.dp, KineticNeon, CircleShape)
                            .clickable { onOpenProfile() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "A",
                            fontWeight = FontWeight.Bold,
                            color = KineticNeon,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }

        // Welcome Greeting & Readiness Matrix
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "PHASE 2 • HYPERTROPHY CYCLE",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = MetricCyan
                        )
                        Box(
                            modifier = Modifier
                                .background(KineticNeon.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "🔥 7-DAY STREAK",
                                color = KineticNeon,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Good evening, Alex",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Goal: Hypertrophy & Muscle Strength",
                            fontSize = 13.sp,
                            color = TextSecondary
                        )
                        Text(
                            text = "Readiness: 94%",
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace,
                            color = KineticNeonLight,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Today's Forge Hero Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, KineticNeon.copy(alpha = 0.35f), RoundedCornerShape(20.dp))
                    .testTag("today_workout_card")
            ) {
                Column {
                    // Hero Image Banner with Gradient Overlay
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_hero_coach),
                            contentDescription = "Workout Hero Banner",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            SurfaceContainer.copy(alpha = 0.95f)
                                        )
                                    )
                                )
                        )

                        // Top Badges
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(KineticNeon, RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "TODAY'S FORGE",
                                    color = Color.Black,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    fontFamily = FontFamily.Monospace
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .background(Color.Black.copy(alpha = 0.65f), RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Timer,
                                        contentDescription = null,
                                        tint = TextPrimary,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "${todayRoutine.estimatedMinutes} MINS",
                                        color = TextPrimary,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                            }
                        }
                    }

                    // Content details
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = todayRoutine.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = todayRoutine.targetSummary,
                            fontSize = 12.sp,
                            color = TextSecondary
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Workout Exercises Preview
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            todayRoutine.exercises.forEachIndexed { index, item ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(SurfaceContainerHigh, RoundedCornerShape(8.dp))
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "${index + 1}.",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Monospace,
                                            color = KineticNeon
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = item.exercise.name,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = TextPrimary
                                        )
                                    }

                                    Text(
                                        text = if (item.isTimeBased) "${item.targetSets} sets × ${item.targetDurationSeconds}s"
                                        else "${item.targetSets} sets × ${item.targetReps} reps",
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily.Monospace,
                                        color = TextSecondary
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // High-contrast CTA Start Workout Button
                        Button(
                            onClick = onStartWorkout,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .testTag("start_workout_button"),
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
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "START WORKOUT",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Performance Matrix (2x2 Grid)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "PERFORMANCE MATRIX",
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted,
                    letterSpacing = 0.5.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricCard(
                        title = "COMPLETED",
                        value = "2/4",
                        subtitle = "Target: 4 sessions/wk",
                        progress = 0.5f,
                        icon = Icons.Default.CheckCircle,
                        accentColor = KineticNeon,
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        title = "ACTIVE TIME",
                        value = "28m",
                        subtitle = "+6m vs yesterday",
                        progress = 0.7f,
                        icon = Icons.Default.Timer,
                        accentColor = MetricCyan,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricCard(
                        title = "VOLUME",
                        value = "1,420 KG",
                        subtitle = "51% of 2,800 KG target",
                        progress = 0.51f,
                        icon = Icons.Default.FitnessCenter,
                        accentColor = KineticNeonLight,
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        title = "STREAK",
                        value = "7 DAYS",
                        subtitle = "M T W T F S S • Active",
                        progress = 1.0f,
                        icon = Icons.Default.Bolt,
                        accentColor = Color(0xFFFFB020),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Command Center: Quick Access Cards
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "COMMAND CENTER",
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted,
                    letterSpacing = 0.5.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionCard(
                        title = "Exercise Library",
                        subtitle = "60+ Movements",
                        icon = Icons.Default.FitnessCenter,
                        accentColor = KineticNeon,
                        onClick = onOpenExerciseLibrary,
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionCard(
                        title = "Workout Plans",
                        subtitle = "Splits & Protocols",
                        icon = Icons.Default.FitnessCenter,
                        accentColor = MetricCyan,
                        onClick = onOpenWorkouts,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionCard(
                        title = "Diet & Fuel",
                        subtitle = "Macros & Plans",
                        icon = Icons.Default.Restaurant,
                        accentColor = Color(0xFFFBBF24),
                        onClick = onOpenNutrition,
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionCard(
                        title = "Body Metrics",
                        subtitle = "Weight & Records",
                        icon = Icons.Default.ShowChart,
                        accentColor = Color(0xFFA78BFA),
                        onClick = onOpenProfile,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Coach Marcus Sticky Banner
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerHigh),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, KineticNeon.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                    .clickable { onOpenAICoach() }
                    .testTag("coach_marcus_banner")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .border(2.dp, KineticNeon, CircleShape)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_coach_avatar),
                            contentDescription = "Coach Marcus",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .align(Alignment.BottomEnd)
                                .background(KineticNeon, CircleShape)
                                .border(1.dp, ObsidianBg, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Mic,
                                contentDescription = "Voice Active",
                                tint = ObsidianBg,
                                modifier = Modifier.size(10.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Coach Marcus (AI Voice)",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(KineticNeon, CircleShape)
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "\"Ready for Chest Day? High energy today. Form guidance is active.\"",
                            fontSize = 12.sp,
                            color = TextSecondary,
                            maxLines = 2
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .background(KineticNeon, RoundedCornerShape(8.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "ASK",
                            color = Color.Black,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun MetricCard(
    title: String,
    value: String,
    subtitle: String,
    progress: Float,
    icon: ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(12.dp))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted
                )
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Monospace,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(CircleShape),
                color = accentColor,
                trackColor = SurfaceContainerHigh
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = TextSecondary,
                maxLines = 1
            )
        }
    }
}

@Composable
fun QuickActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
        shape = RoundedCornerShape(14.dp),
        modifier = modifier
            .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(14.dp))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(accentColor.copy(alpha = 0.15f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }
        }
    }
}
