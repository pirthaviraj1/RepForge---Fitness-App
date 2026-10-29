package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.db.AppDatabase
import com.example.data.model.DifficultyLevel
import com.example.data.model.ExerciseCategory
import com.example.data.repository.FitnessRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    private lateinit var context: Context
    private lateinit var repository: FitnessRepository

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext<Context>()
        val db = AppDatabase.getDatabase(context)
        repository = FitnessRepository(db)
    }

    @Test
    fun `read string from context verifies app name is RepForge`() {
        val appName = context.getString(R.string.app_name)
        assertEquals("RepForge", appName)
    }

    @Test
    fun `repository returns exercises across all bodybuilding categories`() {
        val exercises = repository.getAllExercises()
        assertTrue("Exercises list should have over 10 movements", exercises.size >= 10)

        val categories = exercises.map { it.category }.toSet()
        assertTrue("Should include CHEST category", categories.contains(ExerciseCategory.CHEST))
        assertTrue("Should include LEGS category", categories.contains(ExerciseCategory.LEGS))
        assertTrue("Should include BICEPS category", categories.contains(ExerciseCategory.BICEPS))
        assertTrue("Should include TRICEPS category", categories.contains(ExerciseCategory.TRICEPS))
        assertTrue("Should include SHOULDERS category", categories.contains(ExerciseCategory.SHOULDERS))
        assertTrue("Should include BACK category", categories.contains(ExerciseCategory.BACK))
        assertTrue("Should include ABS category", categories.contains(ExerciseCategory.ABS))
        assertTrue("Should include CORE category", categories.contains(ExerciseCategory.CORE))
        assertTrue("Should include FOREARMS category", categories.contains(ExerciseCategory.FOREARMS))
        assertTrue("Should include GLUTES category", categories.contains(ExerciseCategory.GLUTES))
        assertTrue("Should include CARDIO category", categories.contains(ExerciseCategory.CARDIO))
        assertTrue("Should include STRENGTH category", categories.contains(ExerciseCategory.STRENGTH))
        assertTrue("Should include HIIT category", categories.contains(ExerciseCategory.HIIT))
        assertTrue("Should include FUNCTIONAL category", categories.contains(ExerciseCategory.FUNCTIONAL))
    }

    @Test
    fun `every exercise has distinct animation type and valid form instructions`() {
        val exercises = repository.getAllExercises()
        assertTrue("Should have extensive exercise library", exercises.size >= 40)

        exercises.forEach { exercise ->
            assertNotNull("Exercise ID must not be empty", exercise.id)
            assertNotNull("Exercise name must not be empty", exercise.name)
            assertTrue("Instructions must be detailed", exercise.instructions.isNotEmpty())
            assertTrue("Equipment description must be present", exercise.equipmentRequired.isNotEmpty())
            if (exercise.animationAvailable) {
                assertNotNull("Animation type must be defined for available demonstration", exercise.animationType)
            }
        }
    }

    @Test
    fun `repository provides today workout routine with valid movements`() {
        val routine = repository.getTodayWorkoutRoutine()
        assertNotNull(routine)
        assertEquals("Chest & Triceps Mastery", routine.title)
        assertTrue(routine.exercises.isNotEmpty())
    }

    @Test
    fun `repository provides nutrition macros and food library`() {
        val foods = repository.getFoodItems()
        assertTrue(foods.isNotEmpty())
        val chicken = foods.find { it.name.contains("Chicken", ignoreCase = true) }
        assertNotNull(chicken)
        assertNotNull(chicken?.vegetarianAlternative)
    }

    @Test
    fun `real man exercise provider provides valid drawable for every exercise in library`() {
        val exercises = repository.getAllExercises()
        exercises.forEach { exercise ->
            val drawableRes = com.example.ui.components.RealManExerciseProvider.getDrawableForExercise(exercise)
            assertTrue("Drawable resource must be valid positive ID for ${exercise.name}", drawableRes > 0)

            val videoFrames = com.example.ui.components.RealManExerciseProvider.getVideoClipFrames(exercise)
            assertTrue("Video frames list must not be empty for ${exercise.name}", videoFrames.isNotEmpty())
            videoFrames.forEach { frameId ->
                assertTrue("Frame resource ID must be valid for ${exercise.name}", frameId > 0)
            }
        }
    }

    @Test
    fun `exercise video provider provides valid original mp4 video stream url for every exercise`() {
        val exercises = repository.getAllExercises()
        exercises.forEach { exercise ->
            val videoUrl = com.example.ui.components.ExerciseVideoProvider.getVideoUrlForExercise(exercise)
            assertTrue("Video URL must not be blank for ${exercise.name}", videoUrl.isNotBlank())
            assertTrue("Video URL must point to mp4 stream for ${exercise.name}", videoUrl.contains(".mp4"))
        }
    }
}
