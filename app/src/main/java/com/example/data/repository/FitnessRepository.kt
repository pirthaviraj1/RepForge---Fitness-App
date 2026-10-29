package com.example.data.repository

import com.example.data.db.AppDatabase
import com.example.data.db.FavoriteExerciseEntity
import com.example.data.db.UserProfileEntity
import com.example.data.db.WorkoutHistoryEntity
import com.example.data.model.BodybuildingProgram
import com.example.data.model.Exercise
import com.example.data.model.ExerciseCategory
import com.example.data.model.FoodItem
import com.example.data.model.MealRecommendation
import com.example.data.model.WorkoutExerciseItem
import com.example.data.model.WorkoutRoutine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FitnessRepository(private val database: AppDatabase) {

    private val dao = database.fitnessDao()

    val workoutHistory: Flow<List<WorkoutHistoryEntity>> = dao.getAllWorkoutHistory()
    val favoriteIds: Flow<Set<String>> = dao.getFavoriteExerciseIds().map { list ->
        list.map { it.exerciseId }.toSet()
    }
    val userProfile: Flow<UserProfileEntity?> = dao.getUserProfile()

    suspend fun saveCompletedWorkout(
        routineTitle: String,
        durationSeconds: Int,
        completedExercisesCount: Int,
        totalSets: Int,
        totalReps: Int,
        totalVolumeKg: Int
    ) {
        val entry = WorkoutHistoryEntity(
            routineTitle = routineTitle,
            durationSeconds = durationSeconds,
            completedExercisesCount = completedExercisesCount,
            totalSets = totalSets,
            totalReps = totalReps,
            totalVolumeKg = totalVolumeKg
        )
        dao.insertWorkoutHistory(entry)
    }

    suspend fun toggleFavorite(exerciseId: String, isFavorite: Boolean) {
        if (isFavorite) {
            dao.removeFavorite(exerciseId)
        } else {
            dao.addFavorite(FavoriteExerciseEntity(exerciseId))
        }
    }

    suspend fun saveProfile(profile: UserProfileEntity) {
        dao.saveUserProfile(profile)
    }

    // Default Today's Workout Routine ("Today's Forge")
    fun getTodayWorkoutRoutine(): WorkoutRoutine {
        val pushups = getExerciseById("chest_pushups") ?: exercisesList.first()
        val inclinePushups = getExerciseById("chest_incline_pushups") ?: exercisesList[1]
        val dips = getExerciseById("arms_triceps_dips") ?: exercisesList[2]
        val plank = getExerciseById("abs_plank") ?: exercisesList[3]

        return WorkoutRoutine(
            id = "today_chest_triceps",
            title = "Chest & Triceps Mastery",
            focus = "Hypertrophy Focus",
            level = "Intermediate",
            estimatedMinutes = 45,
            targetSummary = "Target: Upper Pectorals, Anterior Delts & Triceps",
            bannerImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuB8hxQUuY6CqvLNUdJ_r7DVIzFCyAPv2YteLHF1AWxpDOn5TbsgpuawMeqim7h7t5zx5AyoUbju74lKC2GhRVKhFfExm0rMDw9qK1SigBFxwWyUk7Y-synCH6z5h490aB3YoZP932Ek4RxhEAP4U5sAhmRIEIZ5XYsf8BxzqeccLs_c1TNQJXhbCGLU0_SiGPKaAXten8l4zLgzTLyw5SqpAGDAPv3_Kfy5F2Wn0dzXUYXoiXuwNTE",
            exercises = listOf(
                WorkoutExerciseItem(exercise = pushups, targetSets = 3, targetReps = 15, targetDurationSeconds = 45, restDurationSeconds = 30),
                WorkoutExerciseItem(exercise = inclinePushups, targetSets = 3, targetReps = 12, targetDurationSeconds = 45, restDurationSeconds = 30),
                WorkoutExerciseItem(exercise = dips, targetSets = 3, targetReps = 10, targetDurationSeconds = 45, restDurationSeconds = 30),
                WorkoutExerciseItem(exercise = plank, targetSets = 3, targetReps = 30, isTimeBased = true, targetDurationSeconds = 60, restDurationSeconds = 30)
            )
        )
    }

    // Complete list of Workout Routines with explicit timers
    fun getAllWorkoutRoutines(): List<WorkoutRoutine> {
        val today = getTodayWorkoutRoutine()

        val squat = getExerciseById("legs_barbell_squats") ?: exercisesList.find { it.category == ExerciseCategory.LEGS } ?: exercisesList.first()
        val lunges = getExerciseById("legs_walking_lunges") ?: exercisesList.find { it.category == ExerciseCategory.LEGS } ?: exercisesList[1]
        val legExt = getExerciseById("legs_leg_extension") ?: exercisesList[2]
        val calfRaises = getExerciseById("legs_calf_raises") ?: exercisesList[3]

        val legsRoutine = WorkoutRoutine(
            id = "routine_quads_legs",
            title = "Legs & Power Quads",
            focus = "Strength & Volume",
            level = "Intermediate",
            estimatedMinutes = 40,
            targetSummary = "Target: Quadriceps, Hamstrings & Calves",
            bannerImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuB8hxQUuY6CqvLNUdJ_r7DVIzFCyAPv2YteLHF1AWxpDOn5TbsgpuawMeqim7h7t5zx5AyoUbju74lKC2GhRVKhFfExm0rMDw9qK1SigBFxwWyUk7Y-synCH6z5h490aB3YoZP932Ek4RxhEAP4U5sAhmRIEIZ5XYsf8BxzqeccLs_c1TNQJXhbCGLU0_SiGPKaAXten8l4zLgzTLyw5SqpAGDAPv3_Kfy5F2Wn0dzXUYXoiXuwNTE",
            exercises = listOf(
                WorkoutExerciseItem(exercise = squat, targetSets = 4, targetReps = 10, targetDurationSeconds = 50, restDurationSeconds = 45),
                WorkoutExerciseItem(exercise = lunges, targetSets = 3, targetReps = 12, targetDurationSeconds = 45, restDurationSeconds = 30),
                WorkoutExerciseItem(exercise = legExt, targetSets = 3, targetReps = 12, targetDurationSeconds = 40, restDurationSeconds = 30),
                WorkoutExerciseItem(exercise = calfRaises, targetSets = 4, targetReps = 15, targetDurationSeconds = 40, restDurationSeconds = 25)
            )
        )

        val pullups = getExerciseById("back_pullups") ?: exercisesList.find { it.category == ExerciseCategory.BACK } ?: exercisesList.first()
        val row = getExerciseById("back_barbell_row") ?: exercisesList[1]
        val curls = getExerciseById("arms_biceps_curl") ?: exercisesList[2]
        val hammerCurls = getExerciseById("arms_hammer_curl") ?: exercisesList[3]

        val backBicepsRoutine = WorkoutRoutine(
            id = "routine_back_biceps",
            title = "Back & Biceps Pull Engine",
            focus = "V-Taper Hypertrophy",
            level = "Advanced",
            estimatedMinutes = 35,
            targetSummary = "Target: Latissimus Dorsi, Rhomboids & Biceps",
            bannerImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuB8hxQUuY6CqvLNUdJ_r7DVIzFCyAPv2YteLHF1AWxpDOn5TbsgpuawMeqim7h7t5zx5AyoUbju74lKC2GhRVKhFfExm0rMDw9qK1SigBFxwWyUk7Y-synCH6z5h490aB3YoZP932Ek4RxhEAP4U5sAhmRIEIZ5XYsf8BxzqeccLs_c1TNQJXhbCGLU0_SiGPKaAXten8l4zLgzTLyw5SqpAGDAPv3_Kfy5F2Wn0dzXUYXoiXuwNTE",
            exercises = listOf(
                WorkoutExerciseItem(exercise = pullups, targetSets = 4, targetReps = 8, targetDurationSeconds = 45, restDurationSeconds = 45),
                WorkoutExerciseItem(exercise = row, targetSets = 3, targetReps = 10, targetDurationSeconds = 45, restDurationSeconds = 30),
                WorkoutExerciseItem(exercise = curls, targetSets = 3, targetReps = 12, targetDurationSeconds = 40, restDurationSeconds = 30),
                WorkoutExerciseItem(exercise = hammerCurls, targetSets = 3, targetReps = 12, targetDurationSeconds = 40, restDurationSeconds = 30)
            )
        )

        val mountainClimbers = getExerciseById("core_mountain_climbers") ?: exercisesList.find { it.category == ExerciseCategory.CORE } ?: exercisesList.first()
        val bicycleCrunches = getExerciseById("abs_bicycle_crunches") ?: exercisesList[1]
        val plankHold = getExerciseById("abs_plank") ?: exercisesList[2]
        val jumpingJacks = getExerciseById("cardio_jumping_jacks") ?: exercisesList[3]

        val coreCardioRoutine = WorkoutRoutine(
            id = "routine_core_cardio",
            title = "Core Shred & Cardio Burn",
            focus = "Fat Loss & Endurance",
            level = "All Levels",
            estimatedMinutes = 25,
            targetSummary = "Target: Rectus Abdominis, Obliques & Aerobic Capacity",
            bannerImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuB8hxQUuY6CqvLNUdJ_r7DVIzFCyAPv2YteLHF1AWxpDOn5TbsgpuawMeqim7h7t5zx5AyoUbju74lKC2GhRVKhFfExm0rMDw9qK1SigBFxwWyUk7Y-synCH6z5h490aB3YoZP932Ek4RxhEAP4U5sAhmRIEIZ5XYsf8BxzqeccLs_c1TNQJXhbCGLU0_SiGPKaAXten8l4zLgzTLyw5SqpAGDAPv3_Kfy5F2Wn0dzXUYXoiXuwNTE",
            exercises = listOf(
                WorkoutExerciseItem(exercise = jumpingJacks, targetSets = 3, targetReps = 30, isTimeBased = true, targetDurationSeconds = 45, restDurationSeconds = 20),
                WorkoutExerciseItem(exercise = mountainClimbers, targetSets = 3, targetReps = 25, isTimeBased = true, targetDurationSeconds = 45, restDurationSeconds = 20),
                WorkoutExerciseItem(exercise = bicycleCrunches, targetSets = 3, targetReps = 20, targetDurationSeconds = 40, restDurationSeconds = 20),
                WorkoutExerciseItem(exercise = plankHold, targetSets = 3, targetReps = 30, isTimeBased = true, targetDurationSeconds = 60, restDurationSeconds = 30)
            )
        )

        return listOf(today, legsRoutine, backBicepsRoutine, coreCardioRoutine)
    }

    fun getExerciseById(id: String): Exercise? {
        return exercisesList.find { it.id == id }
    }

    fun getAllExercises(): List<Exercise> = exercisesList

    fun getExercisesByCategory(category: ExerciseCategory): List<Exercise> {
        return exercisesList.filter { it.category == category }
    }

    fun getPrograms(): List<BodybuildingProgram> = bodybuildingPrograms

    fun getFoodItems(): List<FoodItem> = nutritionFoodLibrary

    fun getMealRecommendations(): List<MealRecommendation> = mealRecommendations

    companion object {
        // Complete Exercise Library as requested in section 4 & user prompt
        val exercisesList: List<Exercise> = ExerciseCatalog.allExercises

        // Bodybuilding Programs (Section 9)
        val bodybuildingPrograms = listOf(
            BodybuildingProgram(
                id = "prog_beginner_fullbody",
                title = "Beginner Full Body",
                category = "Beginner Programs",
                durationWeeks = 4,
                daysPerWeek = 3,
                description = "Basic movements seekhne aur regular exercise ki habit develop karne ke liye.",
                requiredEquipment = "Bodyweight only",
                schedule = listOf("Day 1: Full Body A", "Day 2: Rest / Light Walk", "Day 3: Full Body B", "Day 4: Rest", "Day 5: Full Body C", "Weekend: Rest")
            ),
            BodybuildingProgram(
                id = "prog_pushup_starter",
                title = "Push-up Starter Program",
                category = "Beginner Programs",
                durationWeeks = 4,
                daysPerWeek = 3,
                description = "Push-ups ki technique aur gradual progression seekhne ke liye.",
                requiredEquipment = "Bodyweight & Elevated Surface",
                schedule = listOf("Mon: Incline & Knee Push-ups", "Wed: Negative Push-ups & Plank", "Fri: Standard Push-up Sets")
            ),
            BodybuildingProgram(
                id = "prog_home_fitness_starter",
                title = "Home Fitness Starter",
                category = "Beginner Programs",
                durationWeeks = 6,
                daysPerWeek = 4,
                description = "Un users ke liye jo ghar par limited equipment ke saath workout karte hain.",
                requiredEquipment = "Bodyweight & Mat",
                schedule = listOf("Mon: Upper Body", "Tue: Core & Cardio", "Thu: Lower Body", "Fri: Total Body Tone")
            ),
            BodybuildingProgram(
                id = "prog_upper_strength",
                title = "Upper Body Strength",
                category = "Intermediate Programs",
                durationWeeks = 8,
                daysPerWeek = 4,
                description = "Chest, back, shoulder aur arms ki hypertrophic strength development.",
                requiredEquipment = "Dumbbells / Pull-up Bar",
                schedule = listOf("Mon: Upper Push", "Tue: Lower Body", "Thu: Upper Pull", "Fri: Arms & Shoulders")
            ),
            BodybuildingProgram(
                id = "prog_ppl",
                title = "Push-Pull-Legs (PPL)",
                category = "Intermediate Programs",
                durationWeeks = 10,
                daysPerWeek = 6,
                description = "Gold standard bodybuilding split for optimal muscle protein synthesis.",
                requiredEquipment = "Gym or Dumbbells",
                schedule = listOf("Mon: Push (Chest/Tri)", "Tue: Pull (Back/Bi)", "Wed: Legs & Calves", "Thu: Push", "Fri: Pull", "Sat: Legs", "Sun: Rest")
            ),
            BodybuildingProgram(
                id = "prog_full_strength",
                title = "Full Body Strength",
                category = "Intermediate Programs",
                durationWeeks = 8,
                daysPerWeek = 3,
                description = "Heavy compound movements for maximum functional force.",
                requiredEquipment = "Gym weights",
                schedule = listOf("Mon: Heavy Full Body", "Wed: Hypertrophy Full Body", "Fri: Power & Explosiveness")
            ),
            BodybuildingProgram(
                id = "prog_core_strength",
                title = "Core Strength & Anti-Rotation",
                category = "Intermediate Programs",
                durationWeeks = 6,
                daysPerWeek = 3,
                description = "Deep abdominal wall and spinal bracing stability.",
                requiredEquipment = "Bodyweight",
                schedule = listOf("Mon: Planks & Bracing", "Wed: Rotational Obliques", "Fri: Posterior Chain & Lower Abs")
            ),
            BodybuildingProgram(
                id = "prog_adv_bodyweight",
                title = "Advanced Bodyweight Training",
                category = "Advanced Programs",
                durationWeeks = 12,
                daysPerWeek = 5,
                description = "Calisthenics mastery: Diamond pushups, pike press, pullups, pistol squats.",
                requiredEquipment = "Pull-up bar & Parallel bars",
                schedule = listOf("Mon: Upper Calisthenics", "Tue: Lower Plyometrics", "Wed: Core Dynamics", "Thu: Pull & Push Max", "Fri: Full Body Circuit")
            ),
            BodybuildingProgram(
                id = "prog_gym_hypertrophy",
                title = "Gym Hypertrophy Program",
                category = "Advanced Programs",
                durationWeeks = 12,
                daysPerWeek = 5,
                description = "High volume progressive overload protocol for maximum muscle hypertrophy.",
                requiredEquipment = "Full Gym Access",
                schedule = listOf("Mon: Chest & Delts", "Tue: Back & Traps", "Wed: Legs Quads Focus", "Thu: Arms & Shoulders", "Fri: Hamstrings & Glutes")
            ),
            BodybuildingProgram(
                id = "prog_pullup_progression",
                title = "Pull-up Progression (0 to 15 Reps)",
                category = "Advanced Programs",
                durationWeeks = 8,
                daysPerWeek = 3,
                description = "Systematic overload with negative reps, banded assistance and weighted holds.",
                requiredEquipment = "Pull-up bar & bands",
                schedule = listOf("Mon: High Volume Assisted", "Wed: Eccentric Negatives", "Fri: Max Unassisted Reps")
            )
        )

        // Healthy Food Library & Macros (Section 10)
        val nutritionFoodLibrary = listOf(
            FoodItem(
                id = "food_chicken_breast",
                name = "Grilled Chicken Breast",
                category = "Protein",
                servingSize = "150g cooked",
                calories = 248,
                proteinGrams = 46.5f,
                carbsGrams = 0.0f,
                fatsGrams = 5.2f,
                fiberGrams = 0.0f,
                vegetarianAlternative = "Paneer (Cottage Cheese) or Tofu / Soya Chunks"
            ),
            FoodItem(
                id = "food_paneer",
                name = "Fresh Paneer (Cottage Cheese)",
                category = "Protein",
                servingSize = "100g",
                calories = 265,
                proteinGrams = 18.3f,
                carbsGrams = 3.5f,
                fatsGrams = 20.8f,
                fiberGrams = 0.0f,
                vegetarianAlternative = "Tofu for lower fat option"
            ),
            FoodItem(
                id = "food_eggs",
                name = "Whole Boiled Eggs",
                category = "Protein",
                servingSize = "2 large eggs",
                calories = 143,
                proteinGrams = 12.6f,
                carbsGrams = 0.8f,
                fatsGrams = 9.5f,
                fiberGrams = 0.0f,
                vegetarianAlternative = "Sprouted Moong Beans or Soya Milk"
            ),
            FoodItem(
                id = "food_yellow_dal",
                name = "Cooked Yellow Moong Dal",
                category = "Protein & Carbs",
                servingSize = "1 cup (200g)",
                calories = 212,
                proteinGrams = 14.2f,
                carbsGrams = 34.0f,
                fatsGrams = 1.2f,
                fiberGrams = 8.0f
            ),
            FoodItem(
                id = "food_brown_rice",
                name = "Cooked Brown Rice",
                category = "Carbohydrates",
                servingSize = "1 cup (195g)",
                calories = 216,
                proteinGrams = 5.0f,
                carbsGrams = 45.0f,
                fatsGrams = 1.8f,
                fiberGrams = 3.5f
            ),
            FoodItem(
                id = "food_oats",
                name = "Rolled Oats with Milk",
                category = "Carbohydrates",
                servingSize = "50g dry + 200ml milk",
                calories = 310,
                proteinGrams = 14.0f,
                carbsGrams = 48.0f,
                fatsGrams = 6.5f,
                fiberGrams = 5.0f
            ),
            FoodItem(
                id = "food_greek_yogurt",
                name = "Greek Yogurt (Curd)",
                category = "Protein & Probiotic",
                servingSize = "150g",
                calories = 130,
                proteinGrams = 15.0f,
                carbsGrams = 6.0f,
                fatsGrams = 2.0f,
                fiberGrams = 0.0f
            ),
            FoodItem(
                id = "food_almonds",
                name = "Raw Almonds & Walnuts",
                category = "Healthy Fats",
                servingSize = "30g (handful)",
                calories = 175,
                proteinGrams = 6.0f,
                carbsGrams = 5.5f,
                fatsGrams = 15.0f,
                fiberGrams = 3.0f
            )
        )

        // Meal Recommendations (Section 10)
        val mealRecommendations = listOf(
            MealRecommendation(
                mealType = "Breakfast",
                title = "Eggs or Paneer with Whole-Wheat Roti & Fruit",
                description = "High bio-availability protein paired with complex sustained carbs and electrolytes.",
                proteinGrams = 26,
                calories = 420,
                alternative = "Sprouted Moong Chilla + Curd"
            ),
            MealRecommendation(
                mealType = "Lunch",
                title = "Chicken Breast or Tofu, Brown Rice & Steamed Veggies",
                description = "Optimal midday refueling with lean amino acids and fiber-rich greens.",
                proteinGrams = 42,
                calories = 580,
                alternative = "Paneer Tikka with Quinoa and Mixed Greens"
            ),
            MealRecommendation(
                mealType = "Post-Workout",
                title = "Grilled Chicken / Paneer with Brown Rice",
                description = "Fresh garden veggies, roasted garlic & lime. Fast recovery window refueling.",
                proteinGrams = 38,
                calories = 520,
                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCCJKLgvDbTYyolx8r6Ea5jowLcLKKU8DpSflb3d-9lGlZIDBf-Z6ncNoyADJSHfB7yEKYxLSJkigICmc9F_DYFU6pqFIYyJo48spLpqa8rtwoKsbDi6Nmw_CIkGMLb4agY3VD1cu6Owrs03DVKJsCZt4wdKxaZ7e5lXjlzQpnE9OQc7JFXxREfWq5iCIBeiQz9bHBQ4vSVPn6VJkQ1PzwE4aom0Dc26boikeceeOxbh4uZC82Mzow",
                alternative = "Whey / Plant Protein Shake + 1 Banana"
            ),
            MealRecommendation(
                mealType = "Snack",
                title = "Greek Yogurt with Berries & Handful of Nuts",
                description = "Slow digesting casein protein and essential omega-3 fatty acids.",
                proteinGrams = 18,
                calories = 240,
                alternative = "Roasted Chana (Chickpeas) + Buttermilk"
            ),
            MealRecommendation(
                mealType = "Dinner",
                title = "Yellow Dal, Warm Roti & Stir-Fry Green Sabzi",
                description = "Light nighttime meal promoting smooth digestion and high sleep recovery.",
                proteinGrams = 22,
                calories = 480,
                alternative = "Soya Chunks Curry with Roti"
            )
        )
    }
}
