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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DifficultyLevel
import com.example.data.model.Exercise
import com.example.data.repository.FitnessRepository
import com.example.ui.components.RealManExerciseProvider
import com.example.ui.theme.KineticNeon
import com.example.ui.theme.MetricCyan
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.launch

@Composable
fun ExerciseLibraryScreen(
    repository: FitnessRepository,
    onSelectExercise: (Exercise) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val favoriteIds by repository.favoriteIds.collectAsState(initial = emptySet())

    val allExercises = remember { repository.getAllExercises() }
    val filteredExercises = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            allExercises
        } else {
            allExercises.filter { exercise ->
                exercise.name.contains(searchQuery, ignoreCase = true) ||
                        exercise.targetMuscles.contains(searchQuery, ignoreCase = true) ||
                        exercise.category.displayName.contains(searchQuery, ignoreCase = true) ||
                        exercise.equipmentRequired.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .padding(16.dp)
            .testTag("exercise_library_screen")
    ) {
        // Title & Stats
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "EXERCISE REPOSITORY",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = KineticNeon
                )
                Text(
                    text = "All Exercises in One List",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextPrimary
                )
            }
            Box(
                modifier = Modifier
                    .background(SurfaceContainerHigh, RoundedCornerShape(8.dp))
                    .border(1.dp, KineticNeon.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "${filteredExercises.size} MOVEMENTS",
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = KineticNeon
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Search Bar (Allows quick filtering without separating into portions)
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search any exercise, muscle, equipment...", fontSize = 13.sp, color = TextMuted) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = TextMuted
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("exercise_search_field"),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = KineticNeon,
                unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
                focusedContainerColor = SurfaceContainerHigh,
                unfocusedContainerColor = SurfaceContainerHigh,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Single Continuous Exercise List - All exercises together in one line
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(filteredExercises, key = { _, exercise -> exercise.id }) { index, exercise ->
                val isFav = favoriteIds.contains(exercise.id)
                ExerciseCardItem(
                    index = index + 1,
                    exercise = exercise,
                    isFavorite = isFav,
                    onToggleFavorite = {
                        scope.launch {
                            repository.toggleFavorite(exercise.id, isFav)
                        }
                    },
                    onClick = { onSelectExercise(exercise) }
                )
            }
        }
    }
}

@Composable
fun ExerciseCardItem(
    index: Int,
    exercise: Exercise,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.White.copy(alpha = 0.06f), RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .testTag("exercise_item_${exercise.id}")
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Sequence Number Badge
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(SurfaceContainerHigh, RoundedCornerShape(6.dp))
                    .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(6.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$index",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = KineticNeon
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            // Real Athlete Coach Thumbnail
            val realManDrawable = remember(exercise) {
                RealManExerciseProvider.getDrawableForExercise(exercise)
            }
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, KineticNeon.copy(alpha = 0.45f), RoundedCornerShape(10.dp))
            ) {
                Image(
                    painter = painterResource(id = realManDrawable),
                    contentDescription = "Coach Marcus Demonstrating ${exercise.name}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .background(Color.Black.copy(alpha = 0.85f), RoundedCornerShape(topStart = 4.dp))
                        .padding(horizontal = 3.dp, vertical = 1.dp)
                ) {
                    Text(
                        text = "▶ HD",
                        fontSize = 7.sp,
                        color = KineticNeon,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = exercise.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )

                    // Category Pill Tag
                    Box(
                        modifier = Modifier
                            .background(SurfaceContainerHigh, RoundedCornerShape(4.dp))
                            .padding(horizontal = 5.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = exercise.category.displayName.uppercase(),
                            fontSize = 8.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = MetricCyan
                        )
                    }

                    // Difficulty Tag
                    Box(
                        modifier = Modifier
                            .background(
                                when (exercise.difficulty) {
                                    DifficultyLevel.BEGINNER -> KineticNeon.copy(alpha = 0.15f)
                                    DifficultyLevel.INTERMEDIATE -> MetricCyan.copy(alpha = 0.15f)
                                    DifficultyLevel.ADVANCED -> Color(0xFFEF4444).copy(alpha = 0.15f)
                                },
                                RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 4.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = exercise.difficulty.name.take(3),
                            fontSize = 8.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = when (exercise.difficulty) {
                                DifficultyLevel.BEGINNER -> KineticNeon
                                DifficultyLevel.INTERMEDIATE -> MetricCyan
                                DifficultyLevel.ADVANCED -> Color(0xFFEF4444)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = exercise.targetMuscles,
                    fontSize = 11.sp,
                    color = TextSecondary,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Timer Tag
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Timer,
                            contentDescription = null,
                            tint = KineticNeon,
                            modifier = Modifier.size(11.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${exercise.workDurationSeconds}s work / ${exercise.restDurationSeconds}s rest",
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.SemiBold,
                            color = KineticNeon
                        )
                    }

                    Text(
                        text = "• ${exercise.defaultSets}×${exercise.defaultReps} reps",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = TextMuted
                    )
                }
            }

            IconButton(onClick = onToggleFavorite) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Color(0xFFEF4444) else TextMuted,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
