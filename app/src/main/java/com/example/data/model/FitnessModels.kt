package com.example.data.model

enum class ExerciseCategory(val displayName: String, val icon: String) {
    CHEST("Chest", "fitness_center"),
    BICEPS("Biceps", "sports_gymnastics"),
    TRICEPS("Triceps", "fitness_center"),
    SHOULDERS("Shoulders", "accessibility_new"),
    BACK("Back", "rowing"),
    LEGS("Legs", "directions_run"),
    ABS("Abs", "airline_seat_flat"),
    CORE("Core", "shield"),
    FOREARMS("Forearms", "front_hand"),
    GLUTES("Glutes", "accessibility"),
    CARDIO("Cardio", "favorite"),
    BODYWEIGHT("Bodyweight Training", "sports_martial_arts"),
    GYM_MACHINES("Gym Machines", "precision_manufacturing"),
    STRETCHING("Stretching", "self_improvement"),
    MOBILITY("Mobility", "loop"),
    STRENGTH("Strength Training", "military_tech"),
    CALISTHENICS("Calisthenics", "sports_score"),
    HIIT("HIIT", "speed"),
    FUNCTIONAL("Functional Training", "bolt"),
    ARMS("Arms", "sports_gymnastics"),
    FULL_BODY("Full Body", "bolt")
}

enum class DifficultyLevel {
    BEGINNER, INTERMEDIATE, ADVANCED
}

enum class AnimationMode {
    NORMAL, SLOW_MOTION, STEP_BY_STEP, MUSCLE_HIGHLIGHT, COMMON_MISTAKES, BEGINNER_VARIATION
}

enum class ExerciseAnimationType {
    // Chest
    PUSH_UP_STANDARD,
    PUSH_UP_INCLINE,
    PUSH_UP_DECLINE,
    PUSH_UP_DIAMOND,
    PUSH_UP_KNEE,
    BENCH_PRESS_BARBELL,
    BENCH_PRESS_DUMBBELL,
    INCLINE_DUMBBELL_PRESS,
    CHEST_FLY_DUMBBELL,
    CHEST_DIPS,

    // Biceps
    BICEPS_CURL_DUMBBELL,
    HAMMER_CURL,
    CONCENTRATION_CURL,
    PREACHER_CURL,

    // Triceps
    TRICEPS_DIPS,
    TRICEPS_EXTENSION_OVERHEAD,
    TRICEPS_PUSHDOWN_CABLE,
    SKULL_CRUSHERS,

    // Forearms
    WRIST_CURLS,
    FARMERS_WALK,

    // Shoulders
    OVERHEAD_PRESS_BARBELL,
    SHOULDER_PRESS_DUMBBELL,
    LATERAL_RAISES,
    FRONT_RAISES,
    REVERSE_FLY,
    PIKE_PUSH_UP,
    CABLE_FACE_PULL,

    // Back
    PULL_UP,
    CHIN_UP,
    BARBELL_ROW,
    DUMBBELL_ROW,
    LAT_PULLDOWN,
    SEATED_CABLE_ROW,
    SUPERMAN_EXTENSION,

    // Legs
    SQUAT_BARBELL,
    SQUAT_BODYWEIGHT,
    JUMP_SQUATS,
    LUNGES_WALKING,
    BULGARIAN_SPLIT_SQUAT,
    CALF_RAISES,
    LEG_EXTENSION_MACHINE,
    LEG_CURL_MACHINE,

    // Glutes
    HIP_THRUST_BARBELL,
    GLUTE_BRIDGE,

    // Abs
    CRUNCHES_STANDARD,
    BICYCLE_CRUNCHES,
    LEG_RAISES_LYING,
    HANGING_LEG_RAISES,

    // Core
    PLANK_HOLD,
    SIDE_PLANK,
    MOUNTAIN_CLIMBERS,
    RUSSIAN_TWISTS,
    AB_WHEEL_ROLLOUT,

    // Cardio
    TREADMILL_RUNNING,
    EXERCISE_BIKE,
    JUMP_ROPE,
    JUMPING_JACKS,
    HIGH_KNEES,

    // Bodyweight
    INVERTED_ROWS,

    // Gym Machines
    LEG_PRESS_MACHINE,
    SEATED_CHEST_PRESS_MACHINE,
    CABLE_CROSSOVER,

    // Stretching
    HAMSTRING_STRETCH,
    QUADRICEPS_STRETCH,
    COBRA_STRETCH,
    CHEST_DOORWAY_STRETCH,

    // Mobility
    WORLDS_GREATEST_STRETCH,
    CAT_COW_MOBILITY,
    DEEP_SQUAT_PRY,

    // Strength
    DEADLIFT_BARBELL,

    // Calisthenics
    MUSCLE_UP,
    L_SIT_HOLD,
    PISTOL_SQUAT,

    // HIIT
    BURPEES,
    KETTLEBELL_SWING,
    BATTLE_ROPES,

    // Functional
    TURKISH_GET_UP,
    MEDICINE_BALL_SLAM,
    SLED_PUSH
}

data class Exercise(
    val id: String,
    val name: String,
    val category: ExerciseCategory,
    val difficulty: DifficultyLevel = DifficultyLevel.INTERMEDIATE,
    val defaultSets: Int = 3,
    val defaultReps: Int = 10,
    val isTimeBased: Boolean = false,
    val durationSeconds: Int = 0,
    val targetMuscles: String,
    val primaryMuscles: List<String>,
    val secondaryMuscles: List<String>,
    val instructions: List<String>,
    val proTips: List<String>,
    val commonMistakes: List<String>,
    val beginnerVariation: String,
    val equipmentRequired: String = "Bodyweight",
    val imageUrl: String? = null,
    val videoUrl: String? = null,
    val animationType: ExerciseAnimationType? = null,
    val animationAvailable: Boolean = true
) {
    val workDurationSeconds: Int
        get() = if (durationSeconds > 0) durationSeconds else 45

    val restDurationSeconds: Int
        get() = 30

    val totalEstimatedMinutes: Int
        get() = (((defaultSets * workDurationSeconds) + ((defaultSets - 1) * restDurationSeconds)) / 60).coerceAtLeast(2)
}

data class WorkoutExerciseItem(
    val exercise: Exercise,
    val targetSets: Int,
    val targetReps: Int,
    val isTimeBased: Boolean = false,
    val targetDurationSeconds: Int = 0,
    val restDurationSeconds: Int = 30,
    var completedSets: Int = 0,
    var completedReps: Int = 0,
    var isDone: Boolean = false
) {
    val workDurationSeconds: Int
        get() = if (targetDurationSeconds > 0) targetDurationSeconds else exercise.workDurationSeconds

    val totalEstimatedMinutes: Int
        get() = (((targetSets * workDurationSeconds) + ((targetSets - 1) * restDurationSeconds)) / 60).coerceAtLeast(2)
}

data class WorkoutRoutine(
    val id: String,
    val title: String,
    val focus: String,
    val level: String,
    val estimatedMinutes: Int,
    val exercises: List<WorkoutExerciseItem>,
    val bannerImageUrl: String,
    val targetSummary: String
)

data class BodybuildingProgram(
    val id: String,
    val title: String,
    val category: String, // Beginner, Intermediate, Advanced
    val durationWeeks: Int,
    val daysPerWeek: Int,
    val description: String,
    val requiredEquipment: String,
    val schedule: List<String>
)

data class FoodItem(
    val id: String,
    val name: String,
    val category: String, // Protein, Carbs, Fats, Healthy Snack
    val servingSize: String,
    val calories: Int,
    val proteinGrams: Float,
    val carbsGrams: Float,
    val fatsGrams: Float,
    val fiberGrams: Float,
    val vegetarianAlternative: String? = null
)

data class MealRecommendation(
    val mealType: String, // Breakfast, Lunch, Snack, Dinner, Post-Workout
    val title: String,
    val description: String,
    val proteinGrams: Int,
    val calories: Int,
    val imageUrl: String? = null,
    val alternative: String
)

data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val sender: String, // "user" or "marcus"
    val text: String,
    val timestamp: String,
    val isSafetyAlert: Boolean = false,
    val actionSuggestion: String? = null
)

data class UserSettings(
    val userName: String = "Alex",
    val goal: String = "Hypertrophy & Muscle Strength",
    val experienceLevel: String = "Intermediate",
    val workoutLocation: String = "Home & Gym",
    val daysPerWeek: Int = 4,
    val sessionMinutes: Int = 45,
    val voiceEnabled: Boolean = true,
    val voiceSpeed: Float = 1.0f,
    val voiceTone: String = "Energetic Coach", // or "Calm Instructional"
    val voiceLanguage: String = "Hinglish / Eng",
    val vibrationEnabled: Boolean = true
)
