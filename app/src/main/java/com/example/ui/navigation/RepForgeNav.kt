package com.example.ui.navigation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.ViewList
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ai.GeminiCoachService
import com.example.data.model.Exercise
import com.example.data.repository.FitnessRepository
import com.example.ui.screens.ActiveWorkoutScreen
import com.example.ui.screens.CoachChatScreen
import com.example.ui.screens.ExerciseDetailScreen
import com.example.ui.screens.ExerciseLibraryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.NutritionScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.WorkoutsScreen
import com.example.ui.theme.KineticNeon
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.voice.CoachVoiceEngine

enum class RepForgeTab(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
) {
    HOME("Home", Icons.Filled.Home, Icons.Outlined.Home, "tab_home"),
    WORKOUTS("Workouts", Icons.Filled.FitnessCenter, Icons.Outlined.FitnessCenter, "tab_workouts"),
    EXERCISES("Exercises", Icons.Filled.ViewList, Icons.Outlined.ViewList, "tab_exercises"),
    NUTRITION("Nutrition", Icons.Filled.Restaurant, Icons.Outlined.Restaurant, "tab_nutrition"),
    PROFILE("Profile", Icons.Filled.Person, Icons.Outlined.Person, "tab_profile")
}

sealed class ScreenDestination {
    object Main : ScreenDestination()
    object ActiveWorkout : ScreenDestination()
    data class ExerciseDetail(val exercise: Exercise) : ScreenDestination()
    object CoachChat : ScreenDestination()
}

@Composable
fun RepForgeApp(
    repository: FitnessRepository,
    coachService: GeminiCoachService,
    voiceEngine: CoachVoiceEngine
) {
    var currentTab by remember { mutableStateOf(RepForgeTab.HOME) }
    var currentDestination by remember { mutableStateOf<ScreenDestination>(ScreenDestination.Main) }

    val todayRoutine = remember { repository.getTodayWorkoutRoutine() }

    when (val dest = currentDestination) {
        is ScreenDestination.ActiveWorkout -> {
            ActiveWorkoutScreen(
                routine = todayRoutine,
                repository = repository,
                voiceEngine = voiceEngine,
                onFinishWorkout = { currentDestination = ScreenDestination.Main },
                onBack = { currentDestination = ScreenDestination.Main }
            )
        }
        is ScreenDestination.ExerciseDetail -> {
            ExerciseDetailScreen(
                exercise = dest.exercise,
                voiceEngine = voiceEngine,
                onBack = { currentDestination = ScreenDestination.Main },
                onStartWorkoutWithExercise = { currentDestination = ScreenDestination.ActiveWorkout }
            )
        }
        is ScreenDestination.CoachChat -> {
            CoachChatScreen(
                coachService = coachService,
                voiceEngine = voiceEngine,
                onBack = { currentDestination = ScreenDestination.Main }
            )
        }
        is ScreenDestination.Main -> {
            Scaffold(
                bottomBar = {
                    NavigationBar(
                        containerColor = SurfaceContainerLow,
                        tonalElevation = 0.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color.White.copy(alpha = 0.05f))
                            .testTag("main_bottom_nav")
                    ) {
                        RepForgeTab.values().forEach { tab ->
                            val isSelected = currentTab == tab
                            NavigationBarItem(
                                selected = isSelected,
                                onClick = { currentTab = tab },
                                icon = {
                                    Icon(
                                        imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                        contentDescription = tab.title,
                                        modifier = Modifier.size(22.dp)
                                    )
                                },
                                label = {
                                    Text(
                                        text = tab.title.uppercase(),
                                        fontSize = 9.sp,
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color.Black,
                                    selectedTextColor = KineticNeon,
                                    indicatorColor = KineticNeon,
                                    unselectedIconColor = TextMuted,
                                    unselectedTextColor = TextMuted
                                ),
                                modifier = Modifier.testTag(tab.testTag)
                            )
                        }
                    }
                },
                containerColor = ObsidianBg
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Crossfade(targetState = currentTab, label = "tab_crossfade") { tab ->
                        when (tab) {
                            RepForgeTab.HOME -> {
                                HomeScreen(
                                    todayRoutine = todayRoutine,
                                    onStartWorkout = { currentDestination = ScreenDestination.ActiveWorkout },
                                    onOpenExerciseLibrary = { currentTab = RepForgeTab.EXERCISES },
                                    onOpenWorkouts = { currentTab = RepForgeTab.WORKOUTS },
                                    onOpenNutrition = { currentTab = RepForgeTab.NUTRITION },
                                    onOpenProfile = { currentTab = RepForgeTab.PROFILE },
                                    onOpenAICoach = { currentDestination = ScreenDestination.CoachChat }
                                )
                            }
                            RepForgeTab.WORKOUTS -> {
                                WorkoutsScreen(
                                    repository = repository,
                                    onStartWorkout = { currentDestination = ScreenDestination.ActiveWorkout }
                                )
                            }
                            RepForgeTab.EXERCISES -> {
                                ExerciseLibraryScreen(
                                    repository = repository,
                                    onSelectExercise = { exercise ->
                                        currentDestination = ScreenDestination.ExerciseDetail(exercise)
                                    }
                                )
                            }
                            RepForgeTab.NUTRITION -> {
                                NutritionScreen(repository = repository)
                            }
                            RepForgeTab.PROFILE -> {
                                ProfileScreen(
                                    repository = repository,
                                    voiceEngine = voiceEngine
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
