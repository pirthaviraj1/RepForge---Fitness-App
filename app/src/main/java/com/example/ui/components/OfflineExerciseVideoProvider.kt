package com.example.ui.components

import com.example.R
import com.example.data.model.Exercise
import com.example.data.model.ExerciseAnimationType
import com.example.data.model.ExerciseCategory

/**
 * Provides offline MP4 video resources for each exercise.
 * All video files are bundled locally in res/raw for 100% offline playback with zero buffering.
 */
object OfflineExerciseVideoProvider {

    fun getVideoResForExercise(exercise: Exercise): Int {
        val anim = exercise.animationType
        return when (anim) {
            // Chest Pushups
            ExerciseAnimationType.PUSH_UP_STANDARD,
            ExerciseAnimationType.PUSH_UP_INCLINE,
            ExerciseAnimationType.PUSH_UP_DECLINE,
            ExerciseAnimationType.PUSH_UP_DIAMOND,
            ExerciseAnimationType.PUSH_UP_KNEE -> R.raw.video_pushup

            // Chest Presses & Flyes
            ExerciseAnimationType.BENCH_PRESS_BARBELL,
            ExerciseAnimationType.BENCH_PRESS_DUMBBELL,
            ExerciseAnimationType.INCLINE_DUMBBELL_PRESS,
            ExerciseAnimationType.CHEST_FLY_DUMBBELL,
            ExerciseAnimationType.CHEST_DIPS,
            ExerciseAnimationType.SEATED_CHEST_PRESS_MACHINE,
            ExerciseAnimationType.CABLE_CROSSOVER -> R.raw.video_bench

            // Biceps, Arms & Forearms
            ExerciseAnimationType.BICEPS_CURL_DUMBBELL,
            ExerciseAnimationType.HAMMER_CURL,
            ExerciseAnimationType.CONCENTRATION_CURL,
            ExerciseAnimationType.PREACHER_CURL,
            ExerciseAnimationType.TRICEPS_DIPS,
            ExerciseAnimationType.TRICEPS_EXTENSION_OVERHEAD,
            ExerciseAnimationType.TRICEPS_PUSHDOWN_CABLE,
            ExerciseAnimationType.SKULL_CRUSHERS,
            ExerciseAnimationType.WRIST_CURLS,
            ExerciseAnimationType.FARMERS_WALK -> R.raw.video_bicep

            // Squats, Quads, Calves & Legs
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
            ExerciseAnimationType.DEEP_SQUAT_PRY -> R.raw.video_squat

            // Back, Pullups, Rows & Lats
            ExerciseAnimationType.PULL_UP,
            ExerciseAnimationType.CHIN_UP,
            ExerciseAnimationType.BARBELL_ROW,
            ExerciseAnimationType.DUMBBELL_ROW,
            ExerciseAnimationType.LAT_PULLDOWN,
            ExerciseAnimationType.SEATED_CABLE_ROW,
            ExerciseAnimationType.SUPERMAN_EXTENSION,
            ExerciseAnimationType.INVERTED_ROWS,
            ExerciseAnimationType.MUSCLE_UP -> R.raw.video_pullup

            // Shoulders & Overhead Presses
            ExerciseAnimationType.OVERHEAD_PRESS_BARBELL,
            ExerciseAnimationType.SHOULDER_PRESS_DUMBBELL,
            ExerciseAnimationType.LATERAL_RAISES,
            ExerciseAnimationType.FRONT_RAISES,
            ExerciseAnimationType.REVERSE_FLY,
            ExerciseAnimationType.PIKE_PUSH_UP,
            ExerciseAnimationType.CABLE_FACE_PULL -> R.raw.video_shoulder

            // Deadlift & Posterior Chain
            ExerciseAnimationType.DEADLIFT_BARBELL -> R.raw.video_deadlift

            // Core, Abs & Functional
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
            ExerciseAnimationType.BURPEES,
            ExerciseAnimationType.KETTLEBELL_SWING,
            ExerciseAnimationType.BATTLE_ROPES,
            ExerciseAnimationType.TURKISH_GET_UP,
            ExerciseAnimationType.MEDICINE_BALL_SLAM,
            ExerciseAnimationType.SLED_PUSH,
            ExerciseAnimationType.JUMP_ROPE,
            ExerciseAnimationType.JUMPING_JACKS,
            ExerciseAnimationType.HIGH_KNEES,
            ExerciseAnimationType.TREADMILL_RUNNING,
            ExerciseAnimationType.EXERCISE_BIKE,
            ExerciseAnimationType.HAMSTRING_STRETCH,
            ExerciseAnimationType.QUADRICEPS_STRETCH,
            ExerciseAnimationType.COBRA_STRETCH,
            ExerciseAnimationType.CHEST_DOORWAY_STRETCH,
            ExerciseAnimationType.WORLDS_GREATEST_STRETCH,
            ExerciseAnimationType.CAT_COW_MOBILITY -> R.raw.video_plank

            null -> when (exercise.category) {
                ExerciseCategory.CHEST -> R.raw.video_pushup
                ExerciseCategory.LEGS, ExerciseCategory.GLUTES -> R.raw.video_squat
                ExerciseCategory.BICEPS, ExerciseCategory.TRICEPS, ExerciseCategory.ARMS, ExerciseCategory.FOREARMS -> R.raw.video_bicep
                ExerciseCategory.BACK -> R.raw.video_pullup
                ExerciseCategory.SHOULDERS -> R.raw.video_shoulder
                ExerciseCategory.STRENGTH -> R.raw.video_deadlift
                else -> R.raw.video_plank
            }
        }
    }

    fun getPhaseInfo(progress: Float): Pair<String, String> {
        return when {
            progress < 0.25f -> Pair("SETUP & BRACE", "Full body tension, neutral spine, active tight grip")
            progress < 0.50f -> Pair("ECCENTRIC LOWERING", "Smooth controlled lowering phase, 2-3s tempo, inhale")
            progress < 0.75f -> Pair("PEAK CONTRACTION", "Target muscle peak squeeze at full contraction point")
            else -> Pair("CONCENTRIC DRIVE", "Explosive drive through primary movers, exhale forcefully")
        }
    }
}
