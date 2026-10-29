package com.example

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.test.core.app.ApplicationProvider
import com.example.data.db.AppDatabase
import com.example.data.repository.FitnessRepository
import com.example.ui.screens.HomeScreen
import com.example.ui.theme.RepForgeTheme
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel8, sdk = [36])
class GreetingScreenshotTest {

    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun repforge_home_screenshot() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = AppDatabase.getDatabase(context)
        val repository = FitnessRepository(db)
        val todayRoutine = repository.getTodayWorkoutRoutine()

        composeTestRule.setContent {
            RepForgeTheme {
                HomeScreen(
                    todayRoutine = todayRoutine,
                    onStartWorkout = {},
                    onOpenExerciseLibrary = {},
                    onOpenWorkouts = {},
                    onOpenNutrition = {},
                    onOpenProfile = {},
                    onOpenAICoach = {}
                )
            }
        }

        composeTestRule.onRoot().captureRoboImage(filePath = "src/test/screenshots/repforge_home.png")
    }
}
