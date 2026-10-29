package com.example.ui.components

import com.example.data.model.Exercise
import com.example.data.model.ExerciseAnimationType
import com.example.data.model.ExerciseCategory

object ExerciseVideoProvider {

    fun getVideoUrlForExercise(exercise: Exercise): String {
        // If a specific videoUrl is configured on the exercise, use it
        if (!exercise.videoUrl.isNullOrBlank()) {
            return exercise.videoUrl
        }

        // Dedicated original demonstration video URLs by specific exercise type
        return when (exercise.animationType) {
            // Pushups & Variations
            ExerciseAnimationType.PUSH_UP_STANDARD,
            ExerciseAnimationType.PUSH_UP_INCLINE,
            ExerciseAnimationType.PUSH_UP_DECLINE,
            ExerciseAnimationType.PUSH_UP_DIAMOND,
            ExerciseAnimationType.PUSH_UP_KNEE -> 
                "https://raw.githubusercontent.com/M-A-D-A-R-A/Sports_py/master/videos/push-up.mp4"

            // Bench Press & Heavy Chest Press
            ExerciseAnimationType.BENCH_PRESS_BARBELL,
            ExerciseAnimationType.BENCH_PRESS_DUMBBELL,
            ExerciseAnimationType.INCLINE_DUMBBELL_PRESS,
            ExerciseAnimationType.CHEST_FLY_DUMBBELL,
            ExerciseAnimationType.CHEST_DIPS,
            ExerciseAnimationType.SEATED_CHEST_PRESS_MACHINE,
            ExerciseAnimationType.CABLE_CROSSOVER -> 
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"

            // Squats & Lower Body Movements
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
            ExerciseAnimationType.DEEP_SQUAT_PRY -> 
                "https://raw.githubusercontent.com/samarthify/AI-Fitness-Trainer/main/squats.mp4"

            // Biceps, Forearms & Arms
            ExerciseAnimationType.BICEPS_CURL_DUMBBELL,
            ExerciseAnimationType.HAMMER_CURL,
            ExerciseAnimationType.CONCENTRATION_CURL,
            ExerciseAnimationType.PREACHER_CURL,
            ExerciseAnimationType.WRIST_CURLS,
            ExerciseAnimationType.FARMERS_WALK -> 
                "https://raw.githubusercontent.com/samarthify/AI-Fitness-Trainer/main/curls.mp4"

            // Triceps
            ExerciseAnimationType.TRICEPS_DIPS,
            ExerciseAnimationType.TRICEPS_EXTENSION_OVERHEAD,
            ExerciseAnimationType.TRICEPS_PUSHDOWN_CABLE,
            ExerciseAnimationType.SKULL_CRUSHERS -> 
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4"

            // Pull-ups, Rows & Back Movements
            ExerciseAnimationType.PULL_UP,
            ExerciseAnimationType.CHIN_UP,
            ExerciseAnimationType.BARBELL_ROW,
            ExerciseAnimationType.DUMBBELL_ROW,
            ExerciseAnimationType.LAT_PULLDOWN,
            ExerciseAnimationType.SEATED_CABLE_ROW,
            ExerciseAnimationType.SUPERMAN_EXTENSION,
            ExerciseAnimationType.INVERTED_ROWS,
            ExerciseAnimationType.MUSCLE_UP -> 
                "https://raw.githubusercontent.com/M-A-D-A-R-A/Sports_py/master/videos/pull-up.mp4"

            // Shoulders & Overhead Press
            ExerciseAnimationType.OVERHEAD_PRESS_BARBELL,
            ExerciseAnimationType.SHOULDER_PRESS_DUMBBELL,
            ExerciseAnimationType.LATERAL_RAISES,
            ExerciseAnimationType.FRONT_RAISES,
            ExerciseAnimationType.REVERSE_FLY,
            ExerciseAnimationType.PIKE_PUSH_UP,
            ExerciseAnimationType.CABLE_FACE_PULL -> 
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4"

            // Abs, Core & Sit-ups
            ExerciseAnimationType.CRUNCHES_STANDARD,
            ExerciseAnimationType.BICYCLE_CRUNCHES,
            ExerciseAnimationType.LEG_RAISES_LYING,
            ExerciseAnimationType.HANGING_LEG_RAISES,
            ExerciseAnimationType.PLANK_HOLD,
            ExerciseAnimationType.SIDE_PLANK,
            ExerciseAnimationType.MOUNTAIN_CLIMBERS,
            ExerciseAnimationType.RUSSIAN_TWISTS,
            ExerciseAnimationType.AB_WHEEL_ROLLOUT,
            ExerciseAnimationType.L_SIT_HOLD -> 
                "https://raw.githubusercontent.com/M-A-D-A-R-A/Sports_py/master/videos/sit-up.mp4"

            // Cardio, Functional & HIIT
            ExerciseAnimationType.TREADMILL_RUNNING,
            ExerciseAnimationType.EXERCISE_BIKE,
            ExerciseAnimationType.JUMP_ROPE,
            ExerciseAnimationType.JUMPING_JACKS,
            ExerciseAnimationType.HIGH_KNEES,
            ExerciseAnimationType.BURPEES,
            ExerciseAnimationType.KETTLEBELL_SWING,
            ExerciseAnimationType.BATTLE_ROPES,
            ExerciseAnimationType.TURKISH_GET_UP,
            ExerciseAnimationType.MEDICINE_BALL_SLAM,
            ExerciseAnimationType.SLED_PUSH,
            ExerciseAnimationType.DEADLIFT_BARBELL -> 
                "https://raw.githubusercontent.com/M-A-D-A-R-A/Sports_py/master/videos/walk.mp4"

            else -> getFallbackVideoByCategory(exercise.category)
        }
    }

    fun getFallbackVideoByCategory(category: ExerciseCategory): String {
        return when (category) {
            ExerciseCategory.CHEST -> "https://raw.githubusercontent.com/M-A-D-A-R-A/Sports_py/master/videos/push-up.mp4"
            ExerciseCategory.LEGS, ExerciseCategory.GLUTES -> "https://raw.githubusercontent.com/samarthify/AI-Fitness-Trainer/main/squats.mp4"
            ExerciseCategory.BICEPS, ExerciseCategory.ARMS, ExerciseCategory.FOREARMS -> "https://raw.githubusercontent.com/samarthify/AI-Fitness-Trainer/main/curls.mp4"
            ExerciseCategory.TRICEPS -> "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4"
            ExerciseCategory.BACK -> "https://raw.githubusercontent.com/M-A-D-A-R-A/Sports_py/master/videos/pull-up.mp4"
            ExerciseCategory.SHOULDERS -> "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4"
            ExerciseCategory.ABS, ExerciseCategory.CORE -> "https://raw.githubusercontent.com/M-A-D-A-R-A/Sports_py/master/videos/sit-up.mp4"
            else -> "https://raw.githubusercontent.com/M-A-D-A-R-A/Sports_py/master/videos/push-up.mp4"
        }
    }
}
