package com.example.ui.components

import com.example.R
import com.example.data.model.BodybuildingProgram
import com.example.data.model.Exercise
import com.example.data.model.ExerciseAnimationType
import com.example.data.model.ExerciseCategory

object RealManExerciseProvider {

    fun getDrawableForExercise(exercise: Exercise): Int {
        return getDrawableForType(exercise.animationType, exercise.category)
    }

    fun getDrawableForType(animationType: ExerciseAnimationType?, category: ExerciseCategory): Int {
        return when (animationType) {
            // Pushups & Floor Press
            ExerciseAnimationType.PUSH_UP_STANDARD,
            ExerciseAnimationType.PUSH_UP_INCLINE,
            ExerciseAnimationType.PUSH_UP_DECLINE,
            ExerciseAnimationType.PUSH_UP_DIAMOND,
            ExerciseAnimationType.PUSH_UP_KNEE -> R.drawable.exercise_real_pushup

            // Bench Press & Chest Machines
            ExerciseAnimationType.BENCH_PRESS_BARBELL,
            ExerciseAnimationType.BENCH_PRESS_DUMBBELL,
            ExerciseAnimationType.INCLINE_DUMBBELL_PRESS,
            ExerciseAnimationType.CHEST_FLY_DUMBBELL,
            ExerciseAnimationType.CHEST_DIPS,
            ExerciseAnimationType.SEATED_CHEST_PRESS_MACHINE,
            ExerciseAnimationType.CABLE_CROSSOVER -> R.drawable.exercise_real_bench

            // Squats & Lower Body
            ExerciseAnimationType.SQUAT_BARBELL,
            ExerciseAnimationType.SQUAT_BODYWEIGHT,
            ExerciseAnimationType.JUMP_SQUATS,
            ExerciseAnimationType.LUNGES_WALKING,
            ExerciseAnimationType.BULGARIAN_SPLIT_SQUAT,
            ExerciseAnimationType.CALF_RAISES,
            ExerciseAnimationType.LEG_PRESS_MACHINE,
            ExerciseAnimationType.LEG_EXTENSION_MACHINE,
            ExerciseAnimationType.LEG_CURL_MACHINE,
            ExerciseAnimationType.HIP_THRUST_BARBELL,
            ExerciseAnimationType.GLUTE_BRIDGE,
            ExerciseAnimationType.PISTOL_SQUAT,
            ExerciseAnimationType.DEEP_SQUAT_PRY -> R.drawable.exercise_real_squat

            // Biceps, Forearms & Arm Iso
            ExerciseAnimationType.BICEPS_CURL_DUMBBELL,
            ExerciseAnimationType.HAMMER_CURL,
            ExerciseAnimationType.CONCENTRATION_CURL,
            ExerciseAnimationType.PREACHER_CURL,
            ExerciseAnimationType.TRICEPS_DIPS,
            ExerciseAnimationType.TRICEPS_EXTENSION_OVERHEAD,
            ExerciseAnimationType.TRICEPS_PUSHDOWN_CABLE,
            ExerciseAnimationType.SKULL_CRUSHERS,
            ExerciseAnimationType.WRIST_CURLS,
            ExerciseAnimationType.FARMERS_WALK -> R.drawable.exercise_real_bicep

            // Shoulders & Overhead Press
            ExerciseAnimationType.OVERHEAD_PRESS_BARBELL,
            ExerciseAnimationType.SHOULDER_PRESS_DUMBBELL,
            ExerciseAnimationType.LATERAL_RAISES,
            ExerciseAnimationType.FRONT_RAISES,
            ExerciseAnimationType.REVERSE_FLY,
            ExerciseAnimationType.PIKE_PUSH_UP,
            ExerciseAnimationType.CABLE_FACE_PULL -> R.drawable.exercise_real_shoulder

            // Pullups, Rows & Back V-Taper
            ExerciseAnimationType.PULL_UP,
            ExerciseAnimationType.CHIN_UP,
            ExerciseAnimationType.BARBELL_ROW,
            ExerciseAnimationType.DUMBBELL_ROW,
            ExerciseAnimationType.LAT_PULLDOWN,
            ExerciseAnimationType.SEATED_CABLE_ROW,
            ExerciseAnimationType.SUPERMAN_EXTENSION,
            ExerciseAnimationType.INVERTED_ROWS,
            ExerciseAnimationType.MUSCLE_UP -> R.drawable.exercise_real_pullup

            // Abs, Core, Deadlift, HIIT & Cardio
            ExerciseAnimationType.PLANK_HOLD,
            ExerciseAnimationType.SIDE_PLANK,
            ExerciseAnimationType.MOUNTAIN_CLIMBERS,
            ExerciseAnimationType.RUSSIAN_TWISTS,
            ExerciseAnimationType.AB_WHEEL_ROLLOUT,
            ExerciseAnimationType.CRUNCHES_STANDARD,
            ExerciseAnimationType.BICYCLE_CRUNCHES,
            ExerciseAnimationType.LEG_RAISES_LYING,
            ExerciseAnimationType.HANGING_LEG_RAISES,
            ExerciseAnimationType.L_SIT_HOLD,
            ExerciseAnimationType.DEADLIFT_BARBELL,
            ExerciseAnimationType.BURPEES,
            ExerciseAnimationType.KETTLEBELL_SWING,
            ExerciseAnimationType.BATTLE_ROPES,
            ExerciseAnimationType.TURKISH_GET_UP,
            ExerciseAnimationType.MEDICINE_BALL_SLAM,
            ExerciseAnimationType.SLED_PUSH,
            ExerciseAnimationType.HAMSTRING_STRETCH,
            ExerciseAnimationType.QUADRICEPS_STRETCH,
            ExerciseAnimationType.COBRA_STRETCH,
            ExerciseAnimationType.CHEST_DOORWAY_STRETCH,
            ExerciseAnimationType.WORLDS_GREATEST_STRETCH,
            ExerciseAnimationType.CAT_COW_MOBILITY,
            ExerciseAnimationType.TREADMILL_RUNNING,
            ExerciseAnimationType.EXERCISE_BIKE,
            ExerciseAnimationType.JUMP_ROPE,
            ExerciseAnimationType.JUMPING_JACKS,
            ExerciseAnimationType.HIGH_KNEES -> R.drawable.exercise_real_plank

            null -> when (category) {
                ExerciseCategory.CHEST -> R.drawable.exercise_real_pushup
                ExerciseCategory.LEGS, ExerciseCategory.GLUTES -> R.drawable.exercise_real_squat
                ExerciseCategory.BICEPS, ExerciseCategory.TRICEPS, ExerciseCategory.FOREARMS, ExerciseCategory.ARMS -> R.drawable.exercise_real_bicep
                ExerciseCategory.SHOULDERS -> R.drawable.exercise_real_shoulder
                ExerciseCategory.BACK -> R.drawable.exercise_real_pullup
                else -> R.drawable.exercise_real_plank
            }
        }
    }

    fun getDrawableForProgram(program: BodybuildingProgram): Int {
        val titleLower = program.title.lowercase()
        return when {
            titleLower.contains("chest") || titleLower.contains("push") -> R.drawable.exercise_real_bench
            titleLower.contains("leg") || titleLower.contains("squat") -> R.drawable.exercise_real_squat
            titleLower.contains("back") || titleLower.contains("pull") -> R.drawable.exercise_real_pullup
            titleLower.contains("shoulder") -> R.drawable.exercise_real_shoulder
            titleLower.contains("arm") || titleLower.contains("bicep") -> R.drawable.exercise_real_bicep
            else -> R.drawable.img_hero_coach
        }
    }

    fun getPhaseInfo(progress: Float): Pair<String, String> {
        return when {
            progress < 0.25f -> Pair("SETUP & BRACE", "Full body tension, locked neutral spine, active grip")
            progress < 0.50f -> Pair("ECCENTRIC LOWERING", "Smooth controlled descent, 2-3 sec cadence, inhale")
            progress < 0.75f -> Pair("PEAK CONTRACTION", "Maximum muscle squeeze at terminal range of motion")
            else -> Pair("CONCENTRIC DRIVE", "Explosive drive through primary movers, exhale forcefully")
        }
    }

    fun getVideoClipFrames(exercise: Exercise): List<Int> {
        val anim = exercise.animationType
        return when (anim) {
            ExerciseAnimationType.PUSH_UP_STANDARD,
            ExerciseAnimationType.PUSH_UP_INCLINE,
            ExerciseAnimationType.PUSH_UP_DECLINE,
            ExerciseAnimationType.PUSH_UP_DIAMOND,
            ExerciseAnimationType.PUSH_UP_KNEE -> listOf(
                R.drawable.exercise_real_pushup,
                R.drawable.exercise_pushup_down,
                R.drawable.exercise_pushup_down,
                R.drawable.exercise_real_pushup
            )

            ExerciseAnimationType.BENCH_PRESS_BARBELL,
            ExerciseAnimationType.BENCH_PRESS_DUMBBELL,
            ExerciseAnimationType.INCLINE_DUMBBELL_PRESS,
            ExerciseAnimationType.CHEST_FLY_DUMBBELL,
            ExerciseAnimationType.CHEST_DIPS,
            ExerciseAnimationType.SEATED_CHEST_PRESS_MACHINE,
            ExerciseAnimationType.CABLE_CROSSOVER -> listOf(
                R.drawable.exercise_real_bench,
                R.drawable.exercise_bench_touch,
                R.drawable.exercise_bench_touch,
                R.drawable.exercise_real_bench
            )

            ExerciseAnimationType.SQUAT_BARBELL,
            ExerciseAnimationType.SQUAT_BODYWEIGHT,
            ExerciseAnimationType.JUMP_SQUATS,
            ExerciseAnimationType.LUNGES_WALKING,
            ExerciseAnimationType.BULGARIAN_SPLIT_SQUAT,
            ExerciseAnimationType.CALF_RAISES,
            ExerciseAnimationType.LEG_PRESS_MACHINE,
            ExerciseAnimationType.LEG_EXTENSION_MACHINE,
            ExerciseAnimationType.LEG_CURL_MACHINE,
            ExerciseAnimationType.HIP_THRUST_BARBELL,
            ExerciseAnimationType.GLUTE_BRIDGE,
            ExerciseAnimationType.PISTOL_SQUAT,
            ExerciseAnimationType.DEEP_SQUAT_PRY -> listOf(
                R.drawable.exercise_real_squat,
                R.drawable.exercise_squat_deep,
                R.drawable.exercise_squat_deep,
                R.drawable.exercise_real_squat
            )

            ExerciseAnimationType.BICEPS_CURL_DUMBBELL,
            ExerciseAnimationType.HAMMER_CURL,
            ExerciseAnimationType.CONCENTRATION_CURL,
            ExerciseAnimationType.PREACHER_CURL,
            ExerciseAnimationType.TRICEPS_DIPS,
            ExerciseAnimationType.TRICEPS_EXTENSION_OVERHEAD,
            ExerciseAnimationType.TRICEPS_PUSHDOWN_CABLE,
            ExerciseAnimationType.SKULL_CRUSHERS,
            ExerciseAnimationType.WRIST_CURLS,
            ExerciseAnimationType.FARMERS_WALK -> listOf(
                R.drawable.exercise_real_bicep,
                R.drawable.exercise_bicep_peak,
                R.drawable.exercise_bicep_peak,
                R.drawable.exercise_real_bicep
            )

            ExerciseAnimationType.PULL_UP,
            ExerciseAnimationType.CHIN_UP,
            ExerciseAnimationType.BARBELL_ROW,
            ExerciseAnimationType.DUMBBELL_ROW,
            ExerciseAnimationType.LAT_PULLDOWN,
            ExerciseAnimationType.SEATED_CABLE_ROW,
            ExerciseAnimationType.SUPERMAN_EXTENSION,
            ExerciseAnimationType.INVERTED_ROWS,
            ExerciseAnimationType.MUSCLE_UP -> listOf(
                R.drawable.exercise_real_pullup,
                R.drawable.exercise_pullup_top,
                R.drawable.exercise_pullup_top,
                R.drawable.exercise_real_pullup
            )

            ExerciseAnimationType.OVERHEAD_PRESS_BARBELL,
            ExerciseAnimationType.SHOULDER_PRESS_DUMBBELL,
            ExerciseAnimationType.LATERAL_RAISES,
            ExerciseAnimationType.FRONT_RAISES,
            ExerciseAnimationType.REVERSE_FLY,
            ExerciseAnimationType.PIKE_PUSH_UP,
            ExerciseAnimationType.CABLE_FACE_PULL -> listOf(
                R.drawable.exercise_real_shoulder,
                R.drawable.exercise_real_bench,
                R.drawable.exercise_real_shoulder
            )

            else -> when (exercise.category) {
                ExerciseCategory.CHEST -> listOf(
                    R.drawable.exercise_real_pushup,
                    R.drawable.exercise_pushup_down,
                    R.drawable.exercise_real_pushup
                )
                ExerciseCategory.LEGS, ExerciseCategory.GLUTES -> listOf(
                    R.drawable.exercise_real_squat,
                    R.drawable.exercise_squat_deep,
                    R.drawable.exercise_real_squat
                )
                ExerciseCategory.BICEPS, ExerciseCategory.TRICEPS, ExerciseCategory.ARMS, ExerciseCategory.FOREARMS -> listOf(
                    R.drawable.exercise_real_bicep,
                    R.drawable.exercise_bicep_peak,
                    R.drawable.exercise_real_bicep
                )
                ExerciseCategory.BACK -> listOf(
                    R.drawable.exercise_real_pullup,
                    R.drawable.exercise_pullup_top,
                    R.drawable.exercise_real_pullup
                )
                ExerciseCategory.SHOULDERS -> listOf(
                    R.drawable.exercise_real_shoulder,
                    R.drawable.exercise_real_bench,
                    R.drawable.exercise_real_shoulder
                )
                else -> listOf(
                    R.drawable.exercise_real_plank,
                    R.drawable.exercise_pushup_down,
                    R.drawable.exercise_real_plank
                )
            }
        }
    }
}
