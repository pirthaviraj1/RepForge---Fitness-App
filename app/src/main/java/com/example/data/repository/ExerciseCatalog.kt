package com.example.data.repository

import com.example.data.model.DifficultyLevel
import com.example.data.model.Exercise
import com.example.data.model.ExerciseAnimationType
import com.example.data.model.ExerciseCategory

/**
 * Master Exercise Catalog for RepForge
 * Curated, diverse exercise movements with direct naming and real biomechanical variety.
 */
object ExerciseCatalog {

    val allExercises: List<Exercise> by lazy {
        listOf(
            // 1. CHEST: Direct Push-ups
            Exercise(
                id = "chest_pushups",
                name = "Push-ups",
                category = ExerciseCategory.CHEST,
                difficulty = DifficultyLevel.BEGINNER,
                defaultSets = 3,
                defaultReps = 15,
                targetMuscles = "Pectoralis Major, Anterior Deltoids, Triceps",
                primaryMuscles = listOf("Pectoralis Major"),
                secondaryMuscles = listOf("Triceps", "Anterior Deltoids", "Core"),
                instructions = listOf(
                    "Place hands flat on floor shoulder-width apart, wrists vertically under shoulders.",
                    "Maintain rigid straight line from head to heels with engaged glutes and braced core.",
                    "Inhale and descend smoothly over 2-3 seconds, tucking elbows at 45 degrees.",
                    "Lower until chest hovers one inch above floor.",
                    "Exhale forcefully and press through palms, extending elbows to lockout."
                ),
                proTips = listOf(
                    "Tuck elbows at 45 degrees instead of flaring wide to protect shoulder capsules.",
                    "Screw your palms into the floor to activate external rotator cuff."
                ),
                commonMistakes = listOf(
                    "Sagging hips or lower back arching indicating loose core.",
                    "Flaring elbows wide causing shoulder impingement.",
                    "Cutting depth short without full chest descent."
                ),
                beginnerVariation = "Knee Push-ups or Hands Elevated on Incline Bench",
                equipmentRequired = "Bodyweight / Exercise Mat",
                animationType = ExerciseAnimationType.PUSH_UP_STANDARD,
                animationAvailable = true
            ),

            // 2. ARMS: Direct Bicep Curls
            Exercise(
                id = "arms_biceps_curl",
                name = "Bicep Curls",
                category = ExerciseCategory.BICEPS,
                difficulty = DifficultyLevel.BEGINNER,
                defaultSets = 3,
                defaultReps = 12,
                targetMuscles = "Biceps Brachii (Long & Short Heads), Brachialis",
                primaryMuscles = listOf("Biceps Brachii"),
                secondaryMuscles = listOf("Brachialis", "Brachioradialis"),
                instructions = listOf(
                    "Stand upright holding dumbbells at sides with palms forward.",
                    "Keep elbows pinned close to ribcage throughout movement.",
                    "Curl dumbbells upward in smooth arc toward shoulders.",
                    "Squeeze biceps hard for 1 second at top contraction.",
                    "Lower slowly under 3-second eccentric control to full arm extension."
                ),
                proTips = listOf(
                    "Do not swing torso or use momentum from hips.",
                    "Keep elbows slightly forward of hips throughout the curl."
                ),
                commonMistakes = listOf(
                    "Swinging body to heave the weight up.",
                    "Allowing elbows to drift backwards during the curl."
                ),
                beginnerVariation = "Light Dumbbell or Resistance Band Curls",
                equipmentRequired = "Dumbbells",
                animationType = ExerciseAnimationType.BICEPS_CURL_DUMBBELL,
                animationAvailable = true
            ),

            // 3. LEGS: Barbell Squats
            Exercise(
                id = "legs_barbell_squats",
                name = "Barbell Squats",
                category = ExerciseCategory.LEGS,
                difficulty = DifficultyLevel.INTERMEDIATE,
                defaultSets = 4,
                defaultReps = 10,
                targetMuscles = "Quadriceps, Gluteus Maximus, Adductors, Core",
                primaryMuscles = listOf("Quadriceps", "Gluteus Maximus"),
                secondaryMuscles = listOf("Hamstrings", "Calves", "Erector Spinae"),
                instructions = listOf(
                    "Rest barbell across upper trapezius muscles with firm overhand grip.",
                    "Stand with feet shoulder-width apart, toes turned outward 15-30 degrees.",
                    "Brace core deeply and break at hips and knees simultaneously.",
                    "Descend until hip crease is below top of knees (parallel or deeper).",
                    "Drive heels through floor and extend hips to upright stance."
                ),
                proTips = listOf(
                    "Push knees outward in line with toes to open hips.",
                    "Maintain tall chest and proud collarbones throughout descent."
                ),
                commonMistakes = listOf(
                    "Knees caving inward (valgus collapse).",
                    "Heels lifting off floor shifting weight onto toes."
                ),
                beginnerVariation = "Goblet Squats with single dumbbell",
                equipmentRequired = "Olympic Barbell & Squat Rack",
                animationType = ExerciseAnimationType.SQUAT_BARBELL,
                animationAvailable = true
            ),

            // 4. BACK: Pull-ups
            Exercise(
                id = "back_pullups",
                name = "Pull-ups",
                category = ExerciseCategory.BACK,
                difficulty = DifficultyLevel.ADVANCED,
                defaultSets = 4,
                defaultReps = 8,
                targetMuscles = "Latissimus Dorsi, Teres Major, Rhomboids, Biceps",
                primaryMuscles = listOf("Latissimus Dorsi"),
                secondaryMuscles = listOf("Biceps Brachii", "Rhomboids", "Middle Trapezius"),
                instructions = listOf(
                    "Hang from pull-up bar with overhand grip slightly wider than shoulders.",
                    "Depress scapulae downward and engage lats from dead hang.",
                    "Pull chest up toward bar by driving elbows down into side pockets.",
                    "Clear chin over bar and hold peak contraction for 1 count.",
                    "Lower smoothly under control back to full arm extension."
                ),
                proTips = listOf(
                    "Think about pulling elbows down to hips rather than pulling body up.",
                    "Keep legs straight and core hollow to prevent swinging."
                ),
                commonMistakes = listOf(
                    "Kicking legs or kipping body for momentum.",
                    "Failing to descend to complete arm lockout."
                ),
                beginnerVariation = "Resistance Band Assisted Pull-ups or Lat Pulldown",
                equipmentRequired = "Pull-up Bar",
                animationType = ExerciseAnimationType.PULL_UP,
                animationAvailable = true
            ),

            // 5. CHEST: Bench Press
            Exercise(
                id = "chest_barbell_bench_press",
                name = "Bench Press",
                category = ExerciseCategory.CHEST,
                difficulty = DifficultyLevel.INTERMEDIATE,
                defaultSets = 4,
                defaultReps = 8,
                targetMuscles = "Pectoralis Major, Anterior Deltoids, Triceps Brachii",
                primaryMuscles = listOf("Pectoralis Major"),
                secondaryMuscles = listOf("Triceps Brachii", "Anterior Deltoids"),
                instructions = listOf(
                    "Lie flat on bench with eyes positioned under racked barbell.",
                    "Plant feet firmly flat on floor and pull shoulder blades tightly together.",
                    "Grip bar slightly wider than shoulder-width with thumbs wrapped.",
                    "Unrack bar and stabilize directly over upper chest.",
                    "Lower bar under control until it gently touches lower sternum.",
                    "Press bar upward and slightly back to starting position over shoulders."
                ),
                proTips = listOf(
                    "Keep wrists straight directly stacked over forearms.",
                    "Drive legs into the ground without lifting hips off bench."
                ),
                commonMistakes = listOf(
                    "Bouncing bar hard off ribcage.",
                    "Flaring elbows straight out at 90 degrees."
                ),
                beginnerVariation = "Dumbbell Bench Press",
                equipmentRequired = "Olympic Barbell & Flat Bench",
                animationType = ExerciseAnimationType.BENCH_PRESS_BARBELL,
                animationAvailable = true
            ),

            // 6. SHOULDERS: Shoulder Press
            Exercise(
                id = "shoulders_military_press",
                name = "Shoulder Press",
                category = ExerciseCategory.SHOULDERS,
                difficulty = DifficultyLevel.INTERMEDIATE,
                defaultSets = 4,
                defaultReps = 8,
                targetMuscles = "Anterior & Lateral Deltoids, Triceps, Upper Chest",
                primaryMuscles = listOf("Anterior Deltoid", "Lateral Deltoid"),
                secondaryMuscles = listOf("Triceps Brachii", "Trapezius", "Core"),
                instructions = listOf(
                    "Hold barbell or dumbbells at shoulder height with elbows tucked slightly forward.",
                    "Brace abs and squeeze glutes to establish rock-solid foundation.",
                    "Press weights overhead vertically in straight line.",
                    "Move head slightly forward once bar clears forehead to complete lockout.",
                    "Lower under control back to clavicle level."
                ),
                proTips = listOf(
                    "Do not excessively arch lower back; squeeze glutes to lock pelvis.",
                    "Lock out elbows completely overhead with active traps."
                ),
                commonMistakes = listOf(
                    "Leaning back excessively turning lift into incline chest press.",
                    "Pushing bar far in front of head instead of straight up."
                ),
                beginnerVariation = "Seated Dumbbell Shoulder Press",
                equipmentRequired = "Barbell or Dumbbells",
                animationType = ExerciseAnimationType.OVERHEAD_PRESS_BARBELL,
                animationAvailable = true
            ),

            // 7. FULL BODY / STRENGTH: Deadlift
            Exercise(
                id = "spec_barbell_deadlift",
                name = "Deadlift",
                category = ExerciseCategory.STRENGTH,
                difficulty = DifficultyLevel.ADVANCED,
                defaultSets = 4,
                defaultReps = 6,
                targetMuscles = "Erector Spinae, Gluteus Maximus, Hamstrings, Trapezius, Forearms",
                primaryMuscles = listOf("Gluteus Maximus", "Hamstrings", "Erector Spinae"),
                secondaryMuscles = listOf("Latissimus Dorsi", "Trapezius", "Forearms", "Core"),
                instructions = listOf(
                    "Stand with feet hip-width apart, barbell cutting over middle of shoelaces.",
                    "Hinge at hips and grip bar just outside shins with hands shoulder-width.",
                    "Pull slack out of bar, flatten back, and pack lats tightly.",
                    "Drive floor away through heels, extending knees and hips together.",
                    "Lock out standing tall with hips fully extended and glutes squeezed."
                ),
                proTips = listOf(
                    "Keep barbell in constant contact with legs throughout ascent.",
                    "Breathe deep into belly and create 360-degree intra-abdominal pressure."
                ),
                commonMistakes = listOf(
                    "Rounding lumbar spine under heavy load.",
                    "Hyperextending spine backward at top lockout."
                ),
                beginnerVariation = "Kettlebell Deadlift or Trap Bar Deadlift",
                equipmentRequired = "Olympic Barbell & Bumper Plates",
                animationType = ExerciseAnimationType.DEADLIFT_BARBELL,
                animationAvailable = true
            ),

            // 8. CORE: Plank
            Exercise(
                id = "abs_plank",
                name = "Plank",
                category = ExerciseCategory.CORE,
                difficulty = DifficultyLevel.BEGINNER,
                defaultSets = 3,
                defaultReps = 30,
                isTimeBased = true,
                durationSeconds = 60,
                targetMuscles = "Rectus Abdominis, Transverse Abdominis, Obliques, Serratus",
                primaryMuscles = listOf("Transverse Abdominis", "Rectus Abdominis"),
                secondaryMuscles = listOf("Gluteus Maximus", "Shoulders", "Erector Spinae"),
                instructions = listOf(
                    "Rest on forearms with elbows directly under shoulders.",
                    "Extend legs straight back on toes, body forming straight line from crown to heels.",
                    "Pull belly button in toward spine and aggressively squeeze glutes and quads.",
                    "Breathe steadily while holding maximal full-body tension."
                ),
                proTips = listOf(
                    "Actively pull elbows toward toes to double abdominal recruitment.",
                    "Never let hips sag below shoulder level."
                ),
                commonMistakes = listOf(
                    "Hips drooping downward hyperextending lower back.",
                    "Piking hips high in air to unload abdominal core."
                ),
                beginnerVariation = "Incline Forearm Plank on Bench",
                equipmentRequired = "Exercise Mat",
                animationType = ExerciseAnimationType.PLANK_HOLD,
                animationAvailable = true
            ),

            // 9. NEW EXERCISE: Romanian Deadlift (RDL)
            Exercise(
                id = "legs_romanian_deadlift",
                name = "Romanian Deadlift (RDL)",
                category = ExerciseCategory.LEGS,
                difficulty = DifficultyLevel.INTERMEDIATE,
                defaultSets = 4,
                defaultReps = 10,
                targetMuscles = "Hamstrings, Gluteus Maximus, Lower Back, Adductors",
                primaryMuscles = listOf("Hamstrings", "Gluteus Maximus"),
                secondaryMuscles = listOf("Erector Spinae", "Forearms", "Lats"),
                instructions = listOf(
                    "Stand holding dumbbells or barbell with overhand grip at thighs.",
                    "Unlock knees with a soft 15-degree bend and keep knees fixed.",
                    "Push hips straight backward as if touching a wall behind you.",
                    "Lower weights along thighs and shins until intense hamstring stretch is reached.",
                    "Drive hips forward through glute contraction to return upright."
                ),
                proTips = listOf(
                    "Movement is pure hip hinge, not a squat. Shins stay near vertical.",
                    "Keep weights shaving your shins to protect lower back."
                ),
                commonMistakes = listOf(
                    "Bending knees excessively turning it into a squat.",
                    "Rounding spine to reach lower to floor."
                ),
                beginnerVariation = "Dumbbell RDL with moderate load",
                equipmentRequired = "Barbell or Dumbbells",
                animationType = ExerciseAnimationType.DEADLIFT_BARBELL,
                animationAvailable = true
            ),

            // 10. ARMS: Hammer Curls
            Exercise(
                id = "arms_hammer_curl",
                name = "Hammer Curls",
                category = ExerciseCategory.BICEPS,
                difficulty = DifficultyLevel.BEGINNER,
                defaultSets = 3,
                defaultReps = 12,
                targetMuscles = "Brachialis, Brachioradialis, Biceps Outer Long Head",
                primaryMuscles = listOf("Brachialis", "Brachioradialis"),
                secondaryMuscles = listOf("Biceps Brachii"),
                instructions = listOf(
                    "Hold dumbbells at sides with palms facing inward toward each other (neutral grip).",
                    "Keep upper arms stationary and elbows tucked against ribs.",
                    "Curl dumbbells upward until forearms are near vertical.",
                    "Squeeze forearms and brachialis hard at top.",
                    "Lower slowly under control to full arm stretch."
                ),
                proTips = listOf(
                    "Neutral grip takes stress off wrists and builds huge forearm-to-bicep thickness.",
                    "Avoid alternating with wild torso swings."
                ),
                commonMistakes = listOf(
                    "Swinging torso forward and backward.",
                    "Letting elbows flare outwards."
                ),
                beginnerVariation = "Seated Neutral Hammer Curls",
                equipmentRequired = "Dumbbells",
                animationType = ExerciseAnimationType.HAMMER_CURL,
                animationAvailable = true
            ),

            // 11. CHEST: Cable Crossover
            Exercise(
                id = "chest_cable_crossover",
                name = "Cable Crossover",
                category = ExerciseCategory.CHEST,
                difficulty = DifficultyLevel.INTERMEDIATE,
                defaultSets = 4,
                defaultReps = 12,
                targetMuscles = "Pectoralis Major (Sternal & Costal), Anterior Deltoids",
                primaryMuscles = listOf("Pectoralis Major"),
                secondaryMuscles = listOf("Anterior Deltoids", "Biceps Short Head"),
                instructions = listOf(
                    "Set pulleys to shoulder height or higher on dual cable station.",
                    "Step forward into split stance with slight torso forward lean.",
                    "With slight bend in elbows, sweep handles forward and down in wide hugging arc.",
                    "Cross hands slightly at peak squeeze to maximize inner chest contraction.",
                    "Return slowly resisting cable tension until deep chest stretch."
                ),
                proTips = listOf(
                    "Constant cable tension keeps pecs engaged even at full contraction.",
                    "Do not press handles forward; imagine wrapping arms around a barrel."
                ),
                commonMistakes = listOf(
                    "Using body momentum to throw weight forward.",
                    "Straightening and bending elbows like a press."
                ),
                beginnerVariation = "Flat Dumbbell Flyes",
                equipmentRequired = "Dual Cable Station",
                animationType = ExerciseAnimationType.CABLE_CROSSOVER,
                animationAvailable = true
            ),

            // 12. BACK: Lat Pulldown
            Exercise(
                id = "back_lat_pulldown",
                name = "Lat Pulldown",
                category = ExerciseCategory.BACK,
                difficulty = DifficultyLevel.BEGINNER,
                defaultSets = 4,
                defaultReps = 10,
                targetMuscles = "Latissimus Dorsi, Teres Major, Rhomboids, Biceps",
                primaryMuscles = listOf("Latissimus Dorsi"),
                secondaryMuscles = listOf("Biceps Brachii", "Posterior Deltoids", "Rhomboids"),
                instructions = listOf(
                    "Sit securely in machine with thigh pads locked snug over knees.",
                    "Grip wide bar with overhand grip wider than shoulders.",
                    "Lean back slightly (10-15 degrees) with chest lifted.",
                    "Drive elbows down and back, pulling bar smoothly to upper chest.",
                    "Squeeze lats firmly, then slowly extend arms back to overhead stretch."
                ),
                proTips = listOf(
                    "Lead with elbows driving toward back pockets.",
                    "Control the weight back up; do not let stack crash."
                ),
                commonMistakes = listOf(
                    "Leaning back 45+ degrees turning it into a row.",
                    "Pulling bar behind neck which strains cervical spine."
                ),
                beginnerVariation = "Resistance band assisted pulldowns",
                equipmentRequired = "Lat Pulldown Machine",
                animationType = ExerciseAnimationType.LAT_PULLDOWN,
                animationAvailable = true
            ),

            // 13. NEW EXERCISE: Arnold Press
            Exercise(
                id = "shoulders_arnold_press",
                name = "Arnold Press",
                category = ExerciseCategory.SHOULDERS,
                difficulty = DifficultyLevel.INTERMEDIATE,
                defaultSets = 3,
                defaultReps = 10,
                targetMuscles = "All 3 Deltoid Heads (Anterior, Lateral, Posterior), Triceps",
                primaryMuscles = listOf("Anterior Deltoid", "Lateral Deltoid"),
                secondaryMuscles = listOf("Triceps Brachii", "Rotator Cuff"),
                instructions = listOf(
                    "Sit on upright bench holding dumbbells at chest with palms facing you (supinated).",
                    "Press dumbbells overhead while simultaneously rotating wrists 180 degrees outward.",
                    "Finish at top lockout with palms facing forward.",
                    "Reverse rotation slowly as you lower dumbbells back to chest position."
                ),
                proTips = listOf(
                    "Smooth continuous rotation hits all three heads of deltoid in one movement.",
                    "Use slightly lighter dumbbells than standard shoulder press for perfect form."
                ),
                commonMistakes = listOf(
                    "Rushing rotation jerky instead of fluid circular press.",
                    "Flaring elbows too early before clearing chin."
                ),
                beginnerVariation = "Standard Seated Dumbbell Shoulder Press",
                equipmentRequired = "Dumbbells & Upright Bench",
                animationType = ExerciseAnimationType.SHOULDER_PRESS_DUMBBELL,
                animationAvailable = true
            ),

            // 14. LEGS: Bulgarian Split Squats
            Exercise(
                id = "legs_bulgarian_split_squat",
                name = "Bulgarian Split Squats",
                category = ExerciseCategory.LEGS,
                difficulty = DifficultyLevel.ADVANCED,
                defaultSets = 3,
                defaultReps = 10,
                targetMuscles = "Quadriceps, Gluteus Medius & Maximus, Hamstrings",
                primaryMuscles = listOf("Quadriceps", "Gluteus Maximus"),
                secondaryMuscles = listOf("Hamstrings", "Calves", "Core Stabilizers"),
                instructions = listOf(
                    "Stand two feet in front of flat bench, placing top of rear foot flat on bench cushion.",
                    "Hold dumbbell in each hand at sides with tall spine and braced core.",
                    "Lower hips smoothly until back knee hovers two inches above floor.",
                    "Front knee should track directly over front laces at 90-degree angle.",
                    "Drive through front heel to stand upright."
                ),
                proTips = listOf(
                    "Slight forward torso lean shifts focus dramatically onto glutes.",
                    "Fix gaze on stationary spot in front of you for balance."
                ),
                commonMistakes = listOf(
                    "Stance too cramped pushing front knee painfully past toes.",
                    "Rear leg bearing too much weight instead of working front leg."
                ),
                beginnerVariation = "Bodyweight Stationary Split Squat on flat floor",
                equipmentRequired = "Flat Bench & Dumbbells",
                animationType = ExerciseAnimationType.BULGARIAN_SPLIT_SQUAT,
                animationAvailable = true
            ),

            // 15. TRICEPS: Skull Crushers
            Exercise(
                id = "arms_skull_crushers",
                name = "Skull Crushers",
                category = ExerciseCategory.TRICEPS,
                difficulty = DifficultyLevel.INTERMEDIATE,
                defaultSets = 3,
                defaultReps = 12,
                targetMuscles = "Triceps Brachii (Medial & Long Heads)",
                primaryMuscles = listOf("Triceps Brachii"),
                secondaryMuscles = listOf("Anterior Deltoids", "Forearms"),
                instructions = listOf(
                    "Lie on flat bench holding EZ-curl bar or dumbbells vertically above chest.",
                    "Angle upper arms slightly back past vertical toward head (roughly 15 degrees).",
                    "Hinge strictly at elbows, lowering weight toward hairline or just behind crown.",
                    "Feel deep stretch in triceps long head.",
                    "Extend elbows forcefully back to starting locked position."
                ),
                proTips = listOf(
                    "Keep elbows tucked inward; do not allow them to flare wide.",
                    "Angling upper arms backward maintains tension at the top lockout."
                ),
                commonMistakes = listOf(
                    "Moving upper arms forward and back like a pullover.",
                    "Dropping bar rapidly toward forehead without control."
                ),
                beginnerVariation = "Cable Overhead Tricep Extension",
                equipmentRequired = "EZ-Bar or Dumbbells & Flat Bench",
                animationType = ExerciseAnimationType.SKULL_CRUSHERS,
                animationAvailable = true
            ),

            // 16. NEW EXERCISE: T-Bar Row
            Exercise(
                id = "back_tbar_row",
                name = "T-Bar Row",
                category = ExerciseCategory.BACK,
                difficulty = DifficultyLevel.INTERMEDIATE,
                defaultSets = 4,
                defaultReps = 10,
                targetMuscles = "Middle Trapezius, Rhomboids, Latissimus Dorsi, Erector Spinae",
                primaryMuscles = listOf("Middle Trapezius", "Rhomboids", "Lats"),
                secondaryMuscles = listOf("Biceps", "Posterior Deltoids", "Erector Spinae"),
                instructions = listOf(
                    "Straddle T-bar platform or landmine with feet shoulder-width.",
                    "Hinge at hips at 45-degree torso angle with flat neutral spine.",
                    "Grip close-grip handles firmly with arms extended.",
                    "Pull handles explosively into upper abdomen, squeezing shoulder blades together.",
                    "Lower weight under control to full lat stretch."
                ),
                proTips = listOf(
                    "Pin chest out and keep spine completely rigid under load.",
                    "Squeeze shoulder blades together as if holding a pen between them."
                ),
                commonMistakes = listOf(
                    "Using excessive leg bounce to yank bar upward.",
                    "Rounding upper back under heavy weight."
                ),
                beginnerVariation = "Seated Cable Row",
                equipmentRequired = "T-Bar Row Machine or Landmine Barbell",
                animationType = ExerciseAnimationType.BARBELL_ROW,
                animationAvailable = true
            ),

            // 17. ABS: Hanging Leg Raises
            Exercise(
                id = "abs_hanging_leg_raises",
                name = "Hanging Leg Raises",
                category = ExerciseCategory.ABS,
                difficulty = DifficultyLevel.ADVANCED,
                defaultSets = 3,
                defaultReps = 12,
                targetMuscles = "Lower Rectus Abdominis, Iliopsoas, Obliques, Grip Strength",
                primaryMuscles = listOf("Rectus Abdominis"),
                secondaryMuscles = listOf("Hip Flexors", "Forearms", "Obliques"),
                instructions = listOf(
                    "Hang from high pull-up bar with overhand grip and engaged shoulders.",
                    "Prevent body from swinging by bracing core and glutes.",
                    "Raise straight legs smoothly until parallel to floor or toes touch bar.",
                    "Curl pelvis up toward ribs at top for maximum lower ab recruitment.",
                    "Lower legs slowly over 3 seconds under strict control."
                ),
                proTips = listOf(
                    "Tilt pelvis backward at top of lift; simply raising legs uses hip flexors.",
                    "Keep legs straight for maximum leverage resistance."
                ),
                commonMistakes = listOf(
                    "Swinging legs back and forth using pendulum momentum.",
                    "Releasing abdominal brace at the bottom."
                ),
                beginnerVariation = "Hanging Knee Raises or Captain's Chair Leg Lift",
                equipmentRequired = "Pull-up Bar",
                animationType = ExerciseAnimationType.HANGING_LEG_RAISES,
                animationAvailable = true
            ),

            // 18. NEW EXERCISE: Dumbbell Pullover
            Exercise(
                id = "chest_dumbbell_pullover",
                name = "Dumbbell Pullover",
                category = ExerciseCategory.CHEST,
                difficulty = DifficultyLevel.INTERMEDIATE,
                defaultSets = 3,
                defaultReps = 12,
                targetMuscles = "Serratus Anterior, Upper Pectoralis, Latissimus Dorsi, Triceps Long Head",
                primaryMuscles = listOf("Pectoralis Major", "Latissimus Dorsi"),
                secondaryMuscles = listOf("Serratus Anterior", "Triceps Long Head"),
                instructions = listOf(
                    "Lie across flat bench with only upper back and shoulders supported.",
                    "Hold single dumbbell with both hands forming diamond under top plate.",
                    "With slight elbow bend, slowly lower dumbbell back behind head in wide arc.",
                    "Feel immense stretch across chest, serratus, and ribcage.",
                    "Pull dumbbell back over chest squeezing pecs and lats."
                ),
                proTips = listOf(
                    "Keep hips dropped slightly lower than bench to maximize ribcage expansion.",
                    "Keep elbow angle fixed throughout; do not turn into tricep extension."
                ),
                commonMistakes = listOf(
                    "Bending elbows excessively during the descent.",
                    "Lifting hips up high eliminating thoracic stretch."
                ),
                beginnerVariation = "Light dumbbell pullover lying flat lengthwise on bench",
                equipmentRequired = "Dumbbell & Flat Bench",
                animationType = ExerciseAnimationType.BENCH_PRESS_DUMBBELL,
                animationAvailable = true
            ),

            // 19. NEW EXERCISE: EZ-Bar Spider Curls
            Exercise(
                id = "biceps_spider_curls",
                name = "EZ-Bar Spider Curls",
                category = ExerciseCategory.BICEPS,
                difficulty = DifficultyLevel.INTERMEDIATE,
                defaultSets = 3,
                defaultReps = 12,
                targetMuscles = "Biceps Brachii Short Head (Peak Hypertrophy), Brachialis",
                primaryMuscles = listOf("Biceps Brachii"),
                secondaryMuscles = listOf("Brachialis", "Forearms"),
                instructions = listOf(
                    "Lie chest-down on incline bench set to 45 degrees, chest supported.",
                    "Hold EZ-curl bar with arms hanging vertically perpendicular to floor.",
                    "Without moving upper arms, curl bar upward toward forehead.",
                    "Hold peak squeeze for 1 second at top contraction.",
                    "Lower slowly to full vertical hang stretch."
                ),
                proTips = listOf(
                    "Chest support 100% eliminates swinging and shoulder cheating.",
                    "Keep upper arms locked strictly vertical."
                ),
                commonMistakes = listOf(
                    "Swinging elbows backward toward hips.",
                    "Lifting chest off bench to assist the lift."
                ),
                beginnerVariation = "Seated Incline Dumbbell Curls",
                equipmentRequired = "Incline Bench & EZ-Bar",
                animationType = ExerciseAnimationType.PREACHER_CURL,
                animationAvailable = true
            ),

            // 20. LEGS: Leg Press
            Exercise(
                id = "legs_leg_press",
                name = "Leg Press",
                category = ExerciseCategory.LEGS,
                difficulty = DifficultyLevel.INTERMEDIATE,
                defaultSets = 4,
                defaultReps = 12,
                targetMuscles = "Quadriceps, Gluteus Maximus, Adductors, Hamstrings",
                primaryMuscles = listOf("Quadriceps", "Gluteus Maximus"),
                secondaryMuscles = listOf("Hamstrings", "Adductors"),
                instructions = listOf(
                    "Sit securely in 45-degree leg press with back and head flat against backrest.",
                    "Place feet shoulder-width apart on center of sled sled platform.",
                    "Release safety locks and lower sled under control until knees reach 90 degrees.",
                    "Press firmly through full foot and heels to extend legs.",
                    "Stop just short of harsh knee hyperextension at top."
                ),
                proTips = listOf(
                    "Never lock out knees aggressively under heavy leg press loads.",
                    "Higher foot placement biases glutes; lower placement biases quads."
                ),
                commonMistakes = listOf(
                    "Allowing lower back to peel off seat cushion at bottom.",
                    "Bouncing weight sled off safety stops."
                ),
                beginnerVariation = "Bodyweight Squats or Light Sled Press",
                equipmentRequired = "45-Degree Leg Press Machine",
                animationType = ExerciseAnimationType.LEG_PRESS_MACHINE,
                animationAvailable = true
            ),

            // 21. SHOULDERS: Cable Face Pulls
            Exercise(
                id = "shoulders_cable_face_pull",
                name = "Cable Face Pulls",
                category = ExerciseCategory.SHOULDERS,
                difficulty = DifficultyLevel.BEGINNER,
                defaultSets = 4,
                defaultReps = 15,
                targetMuscles = "Posterior Deltoids, Infraspinatus, Teres Minor, Rhomboids",
                primaryMuscles = listOf("Posterior Deltoids", "Rotator Cuff"),
                secondaryMuscles = listOf("Rhomboids", "Trapezius"),
                instructions = listOf(
                    "Attach rope handle to cable pulley set at eye level.",
                    "Grip rope with neutral or thumbs-back grip and step back into staggered stance.",
                    "Pull rope center toward bridge of nose while flaring elbows high and wide.",
                    "Externally rotate hands back past ears at peak contraction.",
                    "Hold for 1 count, then slowly extend arms back to start."
                ),
                proTips = listOf(
                    "Essential movement for shoulder health and counteracting rounded desk posture.",
                    "Focus on external shoulder rotation at finish rather than pure arm pulling."
                ),
                commonMistakes = listOf(
                    "Yanking weight back with excessive torso sway.",
                    "Dropping elbows down low turning it into an upright row."
                ),
                beginnerVariation = "Resistance Band Face Pulls",
                equipmentRequired = "Cable Machine with Rope Attachment",
                animationType = ExerciseAnimationType.CABLE_FACE_PULL,
                animationAvailable = true
            ),

            // 22. NEW EXERCISE: Tricep Rope Pushdown
            Exercise(
                id = "arms_triceps_pushdown",
                name = "Tricep Rope Pushdown",
                category = ExerciseCategory.TRICEPS,
                difficulty = DifficultyLevel.BEGINNER,
                defaultSets = 4,
                defaultReps = 12,
                targetMuscles = "Triceps Lateral & Medial Heads (Horseshoe Definition)",
                primaryMuscles = listOf("Triceps Brachii"),
                secondaryMuscles = listOf("Forearms"),
                instructions = listOf(
                    "Attach rope to high pulley cable station.",
                    "Stand tall with slight forward hip hinge, elbows pinned against sides.",
                    "Push rope downward by extending elbows until arms are straight.",
                    "Spread rope ends apart outward at bottom for peak tricep contraction.",
                    "Control weight back up until forearms reach parallel to floor."
                ),
                proTips = listOf(
                    "Spreading rope apart at bottom ignites the lateral head of triceps.",
                    "Keep upper arms completely motionless throughout."
                ),
                commonMistakes = listOf(
                    "Letting elbows drift forward and backward.",
                    "Using shoulder momentum to push weight down."
                ),
                beginnerVariation = "Straight Bar Cable Pushdown",
                equipmentRequired = "High Cable Station & Rope Attachment",
                animationType = ExerciseAnimationType.TRICEPS_PUSHDOWN_CABLE,
                animationAvailable = true
            ),

            // 23. FULL BODY: Burpees
            Exercise(
                id = "spec_burpees",
                name = "Burpees",
                category = ExerciseCategory.HIIT,
                difficulty = DifficultyLevel.INTERMEDIATE,
                defaultSets = 4,
                defaultReps = 15,
                targetMuscles = "Cardiovascular System, Quadriceps, Chest, Deltoids, Core",
                primaryMuscles = listOf("Quadriceps", "Pectoralis Major"),
                secondaryMuscles = listOf("Deltoids", "Core", "Calves"),
                instructions = listOf(
                    "Stand feet shoulder-width, drop down placing hands on floor inside feet.",
                    "Kick feet back into full push-up plank position.",
                    "Lower chest to touch floor in rapid push-up.",
                    "Press up and jump feet back in towards hands.",
                    "Explode vertically into air reaching arms overhead with soft landing."
                ),
                proTips = listOf(
                    "Pace yourself with rhythmic breathing during high rep sets.",
                    "Land softly on balls of feet absorbing shock through knees."
                ),
                commonMistakes = listOf(
                    "Sagging lower back into floor on plank transition.",
                    "Hard stiff-legged landing."
                ),
                beginnerVariation = "Step-Back Burpee without push-up or jump",
                equipmentRequired = "Bodyweight",
                animationType = ExerciseAnimationType.BURPEES,
                animationAvailable = true
            ),

            // 24. SHOULDERS: Lateral Raises
            Exercise(
                id = "shoulders_lateral_raises",
                name = "Lateral Raises",
                category = ExerciseCategory.SHOULDERS,
                difficulty = DifficultyLevel.BEGINNER,
                defaultSets = 4,
                defaultReps = 15,
                targetMuscles = "Lateral Deltoids (Shoulder Width & 3D Cap)",
                primaryMuscles = listOf("Lateral Deltoid"),
                secondaryMuscles = listOf("Anterior Deltoid", "Supraspinatus", "Trapezius"),
                instructions = listOf(
                    "Stand holding dumbbells at sides with slight forward lean (10 degrees).",
                    "Maintain soft bend in elbows.",
                    "Raise dumbbells out to sides along scapular plane until parallel to floor.",
                    "Lead with elbows and keep pinkies slightly higher than thumbs.",
                    "Lower dumbbells slowly over 3 seconds to sides."
                ),
                proTips = listOf(
                    "Think about pushing weights outward toward walls rather than pulling up.",
                    "Never shrug traps up to ears."
                ),
                commonMistakes = listOf(
                    "Swinging heavy dumbbells with momentum.",
                    "Raising weights far above shoulder level engaging upper traps."
                ),
                beginnerVariation = "Cable Lateral Raises or Lighter Dumbbells",
                equipmentRequired = "Dumbbells",
                animationType = ExerciseAnimationType.LATERAL_RAISES,
                animationAvailable = true
            ),

            // 25. CORE: Russian Twists
            Exercise(
                id = "abs_russian_twists",
                name = "Russian Twists",
                category = ExerciseCategory.CORE,
                difficulty = DifficultyLevel.INTERMEDIATE,
                defaultSets = 3,
                defaultReps = 20,
                targetMuscles = "Internal & External Obliques, Rectus Abdominis, Hip Flexors",
                primaryMuscles = listOf("Obliques"),
                secondaryMuscles = listOf("Rectus Abdominis", "Hip Flexors"),
                instructions = listOf(
                    "Sit on floor with knees bent and feet elevated slightly off ground.",
                    "Recline torso back roughly 45 degrees to engage core.",
                    "Hold weight or clasp hands in front of chest.",
                    "Rotate torso smoothly to right side touching floor beside hip.",
                    "Rotate to left side under control, alternating sides smoothly."
                ),
                proTips = listOf(
                    "Rotate ribcage and shoulders, not just swinging your arms.",
                    "Keep knees steady without wild side-to-side wobble."
                ),
                commonMistakes = listOf(
                    "Rounding spine into slouch.",
                    "Swinging arms without actual torso rotation."
                ),
                beginnerVariation = "Keep heels planted on floor",
                equipmentRequired = "Weight Plate, Dumbbell, or Bodyweight",
                animationType = ExerciseAnimationType.RUSSIAN_TWISTS,
                animationAvailable = true
            ),

            // 26. NEW EXERCISE: Box Jumps
            Exercise(
                id = "spec_box_jumps",
                name = "Box Jumps",
                category = ExerciseCategory.FUNCTIONAL,
                difficulty = DifficultyLevel.INTERMEDIATE,
                defaultSets = 4,
                defaultReps = 10,
                targetMuscles = "Fast-Twitch Quadriceps, Glutes, Hamstrings, Calves, Explosive Power",
                primaryMuscles = listOf("Quadriceps", "Gluteus Maximus"),
                secondaryMuscles = listOf("Calves", "Hamstrings", "Core"),
                instructions = listOf(
                    "Stand arms-length in front of sturdy plyo box with feet shoulder-width.",
                    "Hinge hips back and swing arms behind you in quarter squat load.",
                    "Explode vertically extending hips, knees, and ankles while swinging arms forward.",
                    "Land softly on center of box with quiet feet in athletic half-squat.",
                    "Stand tall to full hip extension, then step down safely one foot at a time."
                ),
                proTips = listOf(
                    "Always step down; jumping down puts unnecessary stress on Achilles tendons.",
                    "Focus on maximum explosive jump height and soft, silent landing."
                ),
                commonMistakes = listOf(
                    "Landing hard with straight stiff legs.",
                    "Selecting box too tall causing dangerous shin scrapes."
                ),
                beginnerVariation = "Step-ups on low box or bench",
                equipmentRequired = "Plyometric Box / Platform",
                animationType = ExerciseAnimationType.JUMP_SQUATS,
                animationAvailable = true
            ),

            // 27. NEW EXERCISE: Dumbbell Shrugs
            Exercise(
                id = "shoulders_dumbbell_shrugs",
                name = "Dumbbell Shrugs",
                category = ExerciseCategory.SHOULDERS,
                difficulty = DifficultyLevel.BEGINNER,
                defaultSets = 4,
                defaultReps = 12,
                targetMuscles = "Upper Trapezius & Levator Scapulae",
                primaryMuscles = listOf("Upper Trapezius"),
                secondaryMuscles = listOf("Levator Scapulae", "Forearms"),
                instructions = listOf(
                    "Stand tall holding heavy dumbbells at sides with neutral grip.",
                    "Keep arms straight and elevate shoulders straight up toward ears.",
                    "Squeeze upper traps hard for 1 full second at top.",
                    "Lower smoothly under control back to deep stretch."
                ),
                proTips = listOf(
                    "Move shoulders straight up and down; never roll shoulders in circles which grinds the joint.",
                    "Hold peak squeeze at the top to stimulate high-threshold trap fibers."
                ),
                commonMistakes = listOf(
                    "Rolling shoulders backward or forward.",
                    "Bending elbows to cheat weight up."
                ),
                beginnerVariation = "Barbell Shrugs or Lighter Dumbbells",
                equipmentRequired = "Heavy Dumbbells",
                animationType = ExerciseAnimationType.LATERAL_RAISES,
                animationAvailable = true
            ),

            // 28. FUNCTIONAL: Kettlebell Swings
            Exercise(
                id = "spec_kettlebell_swings",
                name = "Kettlebell Swings",
                category = ExerciseCategory.FUNCTIONAL,
                difficulty = DifficultyLevel.INTERMEDIATE,
                defaultSets = 4,
                defaultReps = 15,
                targetMuscles = "Posterior Chain, Glutes, Hamstrings, Erector Spinae, Latissimus Dorsi",
                primaryMuscles = listOf("Gluteus Maximus", "Hamstrings"),
                secondaryMuscles = listOf("Erector Spinae", "Shoulders", "Core"),
                instructions = listOf(
                    "Stand with feet shoulder-width, kettlebell one foot in front on floor.",
                    "Hinge at hips, tilt bell back, and hike between legs like football snap.",
                    "Snap hips forward explosively, standing tall with clenched glutes.",
                    "Allow kettlebell to float up to chest level driven entirely by hip power.",
                    "Guide bell back into hip hinge and immediately repeat."
                ),
                proTips = listOf(
                    "Power comes 100% from hip snap, not from lifting with arms or shoulders.",
                    "Keep spine long and locked neutral throughout the hinge."
                ),
                commonMistakes = listOf(
                    "Squatting down instead of hinging at hips.",
                    "Raising bell overhead with arms."
                ),
                beginnerVariation = "Light dumbbell swings or glute bridges",
                equipmentRequired = "Cast Iron Kettlebell",
                animationType = ExerciseAnimationType.KETTLEBELL_SWING,
                animationAvailable = true
            ),

            // 29. LEGS: Standing Calf Raises
            Exercise(
                id = "legs_calf_raises",
                name = "Standing Calf Raises",
                category = ExerciseCategory.LEGS,
                difficulty = DifficultyLevel.BEGINNER,
                defaultSets = 4,
                defaultReps = 15,
                targetMuscles = "Gastrocnemius & Soleus Calves",
                primaryMuscles = listOf("Gastrocnemius"),
                secondaryMuscles = listOf("Soleus"),
                instructions = listOf(
                    "Stand with balls of feet on edge of sturdy block or step, heels hanging off.",
                    "Lower heels as far as comfortable to deep calf stretch for 1 second.",
                    "Drive through balls of feet and big toes, raising up onto tiptoes as high as possible.",
                    "Squeeze calves hard at peak contraction for 1 second.",
                    "Descend slowly over 3 seconds."
                ),
                proTips = listOf(
                    "Pause at the bottom stretch to eliminate Achilles tendon spring reflex.",
                    "Drive through big toes to hit inner gastrocnemius head."
                ),
                commonMistakes = listOf(
                    "Bouncing rapidly at bottom without pause.",
                    "Bending knees instead of keeping legs straight."
                ),
                beginnerVariation = "Bodyweight Calf Raises on flat ground",
                equipmentRequired = "Step Block or Calf Machine",
                animationType = ExerciseAnimationType.CALF_RAISES,
                animationAvailable = true
            ),

            // 30. BACK: Chin-ups
            Exercise(
                id = "back_chinups",
                name = "Chin-ups",
                category = ExerciseCategory.BACK,
                difficulty = DifficultyLevel.INTERMEDIATE,
                defaultSets = 3,
                defaultReps = 8,
                targetMuscles = "Latissimus Dorsi, Biceps Brachii, Brachialis",
                primaryMuscles = listOf("Latissimus Dorsi", "Biceps Brachii"),
                secondaryMuscles = listOf("Brachialis", "Forearms", "Core"),
                instructions = listOf(
                    "Grip pull-up bar with underhand grip (palms facing toward you) shoulder-width.",
                    "Hang fully with extended arms and engaged shoulder blades.",
                    "Pull chest up toward bar driving elbows down and back.",
                    "Bring chin cleanly over bar and squeeze biceps and lats.",
                    "Lower smoothly under control back to full hang."
                ),
                proTips = listOf(
                    "Underhand grip significantly increases bicep activation compared to pull-ups.",
                    "Avoid swinging or kipping with knees."
                ),
                commonMistakes = listOf(
                    "Cutting bottom range of motion short.",
                    "Rounding shoulders forward at the top."
                ),
                beginnerVariation = "Band-assisted Chin-ups",
                equipmentRequired = "Pull-up Bar",
                animationType = ExerciseAnimationType.CHIN_UP,
                animationAvailable = true
            ),

            // 31. LEGS: Hamstring Leg Curls
            Exercise(
                id = "legs_leg_curl",
                name = "Hamstring Leg Curls",
                category = ExerciseCategory.LEGS,
                difficulty = DifficultyLevel.BEGINNER,
                defaultSets = 3,
                defaultReps = 12,
                targetMuscles = "Biceps Femoris, Semitendinosus, Semimembranosus",
                primaryMuscles = listOf("Hamstrings"),
                secondaryMuscles = listOf("Calves"),
                instructions = listOf(
                    "Lie face down on leg curl machine with roller pad positioned just below calf muscles.",
                    "Grip handles firmly and press hips flat into bench pad.",
                    "Curl legs upward smoothly toward glutes as far as possible.",
                    "Hold peak contraction for 1 count.",
                    "Lower slowly under 3-second eccentric control."
                ),
                proTips = listOf(
                    "Keep hips pressed firmly into bench so lower back does not take over.",
                    "Dorsiflex ankles (toes pulled toward shins) for greater hamstring recruitment."
                ),
                commonMistakes = listOf(
                    "Lifting hips up off bench during curl.",
                    "Letting weight stack drop down quickly without resistance."
                ),
                beginnerVariation = "Seated Leg Curl Machine or Resistance Band Leg Curls",
                equipmentRequired = "Lying Leg Curl Machine",
                animationType = ExerciseAnimationType.LEG_CURL_MACHINE,
                animationAvailable = true
            ),

            // 32. LEGS: Leg Extensions
            Exercise(
                id = "legs_leg_extension",
                name = "Leg Extensions",
                category = ExerciseCategory.LEGS,
                difficulty = DifficultyLevel.BEGINNER,
                defaultSets = 3,
                defaultReps = 12,
                targetMuscles = "Quadriceps (Rectus Femoris, Vastus Lateralis & Medialis)",
                primaryMuscles = listOf("Quadriceps"),
                secondaryMuscles = listOf(),
                instructions = listOf(
                    "Sit securely in leg extension machine with back firmly against pad.",
                    "Align knee joints directly with machine's pivot axis point.",
                    "Extend knees smoothly to lift roller pad until legs are straight.",
                    "Squeeze quads hard at the top peak for 1 second.",
                    "Lower weight slowly over 3 seconds under full control."
                ),
                proTips = listOf(
                    "Great for isolating the teardrop vastus medialis quad muscle.",
                    "Control the eccentric descent to maximize muscle fiber activation."
                ),
                commonMistakes = listOf(
                    "Kicking legs explosively using momentum.",
                    "Lifting hips up off seat cushion."
                ),
                beginnerVariation = "Bodyweight Squats or Lighter Machine Weight",
                equipmentRequired = "Leg Extension Machine",
                animationType = ExerciseAnimationType.LEG_EXTENSION_MACHINE,
                animationAvailable = true
            ),

            // 33. NEW EXERCISE: Cable Woodchoppers
            Exercise(
                id = "abs_cable_woodchoppers",
                name = "Cable Woodchoppers",
                category = ExerciseCategory.CORE,
                difficulty = DifficultyLevel.INTERMEDIATE,
                defaultSets = 3,
                defaultReps = 12,
                targetMuscles = "Obliques, Transverse Abdominis, Shoulders, Rotational Power",
                primaryMuscles = listOf("Obliques"),
                secondaryMuscles = listOf("Transverse Abdominis", "Deltoids"),
                instructions = listOf(
                    "Set cable pulley to high position with single handle.",
                    "Stand sideways to machine with feet wider than shoulder-width, gripping handle with both hands.",
                    "With straight arms, rotate torso down and across body toward opposite knee.",
                    "Pivot back foot slightly while engaging core obliques.",
                    "Slowly resist cable tension returning back to high starting position."
                ),
                proTips = listOf(
                    "Drive movement through torso and hips, not by pulling with arms.",
                    "Builds real rotational athletic strength for sports and core definition."
                ),
                commonMistakes = listOf(
                    "Bending elbows and pulling handle with arms like a row.",
                    "Letting torso slouch forward."
                ),
                beginnerVariation = "Resistance Band Diagonal Chops",
                equipmentRequired = "Cable Machine with Single Handle",
                animationType = ExerciseAnimationType.RUSSIAN_TWISTS,
                animationAvailable = true
            ),

            // 34. CORE: Ab Wheel Rollout
            Exercise(
                id = "abs_ab_wheel",
                name = "Ab Wheel Rollout",
                category = ExerciseCategory.CORE,
                difficulty = DifficultyLevel.ADVANCED,
                defaultSets = 3,
                defaultReps = 10,
                targetMuscles = "Rectus Abdominis, Transverse Abdominis, Lats, Hip Flexors",
                primaryMuscles = listOf("Rectus Abdominis", "Transverse Abdominis"),
                secondaryMuscles = listOf("Latissimus Dorsi", "Shoulders"),
                instructions = listOf(
                    "Kneel on soft mat holding ab wheel handles directly under shoulders.",
                    "Round upper back slightly and brace core into hollow posture.",
                    "Roll wheel slowly forward in front of you, extending body toward floor.",
                    "Extend as far forward as core can support without lower back sagging.",
                    "Contract abs forcefully and pull wheel back to starting position."
                ),
                proTips = listOf(
                    "Do not sit back with hips first; pull with abs to return to start.",
                    "Only roll out as far as your lower back can stay braced without arching."
                ),
                commonMistakes = listOf(
                    "Lower back sagging into hyperextension causing spine pain.",
                    "Using hip flexors to push back instead of core contraction."
                ),
                beginnerVariation = "Roll out towards a wall that acts as a stopper",
                equipmentRequired = "Ab Roller Wheel & Knee Mat",
                animationType = ExerciseAnimationType.AB_WHEEL_ROLLOUT,
                animationAvailable = true
            ),

            // 35. FUNCTIONAL: Battle Ropes
            Exercise(
                id = "spec_battle_ropes",
                name = "Battle Ropes",
                category = ExerciseCategory.HIIT,
                difficulty = DifficultyLevel.INTERMEDIATE,
                defaultSets = 4,
                defaultReps = 30,
                isTimeBased = true,
                durationSeconds = 45,
                targetMuscles = "Shoulders, Arms, Core, Grip, High Aerobic Conditioning",
                primaryMuscles = listOf("Deltoids", "Forearms"),
                secondaryMuscles = listOf("Core", "Quadriceps", "Rhomboids"),
                instructions = listOf(
                    "Stand facing rope anchor with feet shoulder-width in athletic quarter squat.",
                    "Grip rope ends with neutral palms and elbows slightly bent.",
                    "Raise and slam arms in rapid alternating waves sending fluid pulses to anchor.",
                    "Brace core tightly and maintain steady high cadence."
                ),
                proTips = listOf(
                    "Keep knees soft and core braced so power transfers into the ropes.",
                    "Breathe steadily and do not hold your breath during wave intervals."
                ),
                commonMistakes = listOf(
                    "Standing straight up losing athletic base.",
                    "Moving shoulders stiffly instead of fluid arm whipping."
                ),
                beginnerVariation = "Double arm wave intervals for 20 seconds",
                equipmentRequired = "Heavy Battle Ropes & Anchor",
                animationType = ExerciseAnimationType.BATTLE_ROPES,
                animationAvailable = true
            ),

            // 36. GLUTES: Barbell Hip Thrust
            Exercise(
                id = "glutes_hip_thrust",
                name = "Barbell Hip Thrust",
                category = ExerciseCategory.GLUTES,
                difficulty = DifficultyLevel.INTERMEDIATE,
                defaultSets = 4,
                defaultReps = 12,
                targetMuscles = "Gluteus Maximus, Hamstrings, Adductors",
                primaryMuscles = listOf("Gluteus Maximus"),
                secondaryMuscles = listOf("Hamstrings", "Quadriceps"),
                instructions = listOf(
                    "Sit on floor with upper back resting against sturdy bench edge.",
                    "Roll padded barbell over hips with feet flat on floor shoulder-width.",
                    "Drive heels into floor and thrust hips upward until body forms straight table line.",
                    "Squeeze glutes hard at the top with chin tucked looking forward.",
                    "Lower hips smoothly back down."
                ),
                proTips = listOf(
                    "Keep chin tucked to chest throughout to protect lower back.",
                    "Full hip extension at top produces unmatched glute hypertrophy."
                ),
                commonMistakes = listOf(
                    "Hyperextending lumbar spine instead of extending through hips.",
                    "Feet placed too far forward or too close to hips."
                ),
                beginnerVariation = "Bodyweight Glute Bridges on floor",
                equipmentRequired = "Barbell with Bar Pad & Flat Bench",
                animationType = ExerciseAnimationType.HIP_THRUST_BARBELL,
                animationAvailable = true
            ),

            // 37. ARMS / FOREARMS: Farmer's Walk
            Exercise(
                id = "arms_farmers_walk",
                name = "Farmer's Walk",
                category = ExerciseCategory.FOREARMS,
                difficulty = DifficultyLevel.BEGINNER,
                defaultSets = 4,
                defaultReps = 40,
                isTimeBased = true,
                durationSeconds = 45,
                targetMuscles = "Forearms, Hand Grip Strength, Trapezius, Core Stabilizers",
                primaryMuscles = listOf("Forearms", "Trapezius"),
                secondaryMuscles = listOf("Core", "Quadriceps", "Calves"),
                instructions = listOf(
                    "Deadlift heavy dumbbell or kettlebell in each hand at sides.",
                    "Stand tall with chest proud, shoulders pulled down and back.",
                    "Walk forward with short, measured, deliberate heel-to-toe strides.",
                    "Keep weights completely steady without swaying or hitting thighs."
                ),
                proTips = listOf(
                    "Crush handles with maximum grip pressure.",
                    "Do not let shoulders roll forward under the heavy load."
                ),
                commonMistakes = listOf(
                    "Slouching shoulders forward and swaying side to side.",
                    "Walking too fast losing posture."
                ),
                beginnerVariation = "Moderate weight dumbbells across 20-second carries",
                equipmentRequired = "Heavy Dumbbells or Kettlebells",
                animationType = ExerciseAnimationType.FARMERS_WALK,
                animationAvailable = true
            ),

            // 38. CARDIO: Jump Rope
            Exercise(
                id = "cardio_jump_rope",
                name = "Jump Rope",
                category = ExerciseCategory.CARDIO,
                difficulty = DifficultyLevel.BEGINNER,
                defaultSets = 4,
                defaultReps = 60,
                isTimeBased = true,
                durationSeconds = 60,
                targetMuscles = "Calves, Cardiovascular Stamina, Forearms, Foot Agility",
                primaryMuscles = listOf("Calves"),
                secondaryMuscles = listOf("Forearms", "Shoulders", "Core"),
                instructions = listOf(
                    "Hold jump rope handles with elbows close to ribs.",
                    "Rotate rope using small wrist circles rather than flailing whole arms.",
                    "Jump only 1-2 inches off floor just enough for rope to clear feet.",
                    "Land softly on balls of feet with light knees."
                ),
                proTips = listOf(
                    "Keep jumps small and rhythmic to save energy and protect joints.",
                    "All turning power comes from wrists, not arms."
                ),
                commonMistakes = listOf(
                    "Jumping too high bending knees up high.",
                    "Swinging entire arms instead of flicking wrists."
                ),
                beginnerVariation = "Single basic bounce without speed rope",
                equipmentRequired = "Speed Jump Rope",
                animationType = ExerciseAnimationType.JUMP_ROPE,
                animationAvailable = true
            ),

            // 39. CHEST: Chest Dips
            Exercise(
                id = "chest_dips",
                name = "Chest Dips",
                category = ExerciseCategory.CHEST,
                difficulty = DifficultyLevel.ADVANCED,
                defaultSets = 3,
                defaultReps = 10,
                targetMuscles = "Lower Pectorals, Anterior Deltoids, Triceps",
                primaryMuscles = listOf("Lower Pectorals"),
                secondaryMuscles = listOf("Triceps", "Anterior Deltoids"),
                instructions = listOf(
                    "Mount parallel dip bars with locked elbows and crossed ankles.",
                    "Lean torso forward roughly 30 degrees to bias chest over triceps.",
                    "Lower body smoothly until elbows reach 90 degrees.",
                    "Drive through palms to return to top lockout."
                ),
                proTips = listOf(
                    "Keep elbows slightly flared at 45 degrees and lean forward.",
                    "Never drop down so deep that front shoulders feel sharp strain."
                ),
                commonMistakes = listOf(
                    "Staying completely upright which shifts tension to triceps.",
                    "Shrugging shoulders up to ears at bottom."
                ),
                beginnerVariation = "Resistance band assisted dips or bench dips",
                equipmentRequired = "Parallel Dip Bars",
                animationType = ExerciseAnimationType.CHEST_DIPS,
                animationAvailable = true
            ),

            // 40. CHEST: Incline Dumbbell Press
            Exercise(
                id = "chest_incline_dumbbell_press",
                name = "Incline Dumbbell Press",
                category = ExerciseCategory.CHEST,
                difficulty = DifficultyLevel.INTERMEDIATE,
                defaultSets = 4,
                defaultReps = 10,
                targetMuscles = "Upper Pectoralis (Clavicular Head), Anterior Deltoids, Triceps",
                primaryMuscles = listOf("Upper Pectorals"),
                secondaryMuscles = listOf("Anterior Deltoids", "Triceps"),
                instructions = listOf(
                    "Set bench to 30-degree incline angle.",
                    "Sit back with dumbbells at chest level, elbows angled at 45 degrees.",
                    "Press dumbbells smoothly overhead in natural converging arc.",
                    "Squeeze upper chest at top without clanking weights.",
                    "Lower under control over 3 seconds to deep stretch."
                ),
                proTips = listOf(
                    "Keep bench around 30 degrees; higher angles shift tension to front shoulders.",
                    "Retract shoulder blades into bench to protect rotator cuff."
                ),
                commonMistakes = listOf(
                    "Setting bench at 60 degrees turning it into a shoulder press.",
                    "Bouncing weights off shoulders."
                ),
                beginnerVariation = "Lighter Dumbbells or Incline Push-ups",
                equipmentRequired = "Incline Bench & Dumbbells",
                animationType = ExerciseAnimationType.INCLINE_DUMBBELL_PRESS,
                animationAvailable = true
            ),

            // 41. BACK: Barbell Bent-Over Row
            Exercise(
                id = "back_barbell_row",
                name = "Barbell Bent-Over Row",
                category = ExerciseCategory.BACK,
                difficulty = DifficultyLevel.INTERMEDIATE,
                defaultSets = 4,
                defaultReps = 8,
                targetMuscles = "Latissimus Dorsi, Rhomboids, Middle Trapezius, Biceps",
                primaryMuscles = listOf("Latissimus Dorsi", "Rhomboids"),
                secondaryMuscles = listOf("Biceps Brachii", "Posterior Deltoids", "Erector Spinae"),
                instructions = listOf(
                    "Hinge at hips with torso bent forward at 45-degree angle, spine flat.",
                    "Grip barbell with overhand grip just outside knees.",
                    "Pull barbell smoothly into belly button by driving elbows up and back.",
                    "Squeeze shoulder blades tightly together at top of stroke.",
                    "Lower bar under control to full arm hang."
                ),
                proTips = listOf(
                    "Keep chest out and lower back locked neutral throughout.",
                    "Think about elbow drive rather than pulling with hands."
                ),
                commonMistakes = listOf(
                    "Standing too upright turning row into a shrug.",
                    "Rounding lower back under load."
                ),
                beginnerVariation = "Single-arm Dumbbell Row with knee on bench",
                equipmentRequired = "Olympic Barbell & Weight Plates",
                animationType = ExerciseAnimationType.BARBELL_ROW,
                animationAvailable = true
            ),

            // 42. ABS: Bicycle Crunches
            Exercise(
                id = "abs_bicycle_crunches",
                name = "Bicycle Crunches",
                category = ExerciseCategory.ABS,
                difficulty = DifficultyLevel.BEGINNER,
                defaultSets = 3,
                defaultReps = 20,
                targetMuscles = "Rectus Abdominis, Internal & External Obliques",
                primaryMuscles = listOf("Rectus Abdominis", "Obliques"),
                secondaryMuscles = listOf("Hip Flexors"),
                instructions = listOf(
                    "Lie flat on back with hands lightly touching back of head, elbows wide.",
                    "Lift shoulders off floor and bring knees to 90 degrees.",
                    "Rotate right elbow toward left knee while extending right leg straight.",
                    "Switch sides smoothly in pedaling rhythm.",
                    "Focus on twisting through torso, not pulling neck."
                ),
                proTips = listOf(
                    "Never yank on neck; fingers rest lightly behind ears.",
                    "Slow, deliberate repetitions produce far higher muscle tension than rushing."
                ),
                commonMistakes = listOf(
                    "Yanking head and neck forward.",
                    "Flapping elbows back and forth without rotating shoulders."
                ),
                beginnerVariation = "Standard floor crunches",
                equipmentRequired = "Exercise Mat",
                animationType = ExerciseAnimationType.BICYCLE_CRUNCHES,
                animationAvailable = true
            ),

            // 43. ARMS: Preacher Curls
            Exercise(
                id = "biceps_preacher_curls",
                name = "Preacher Curls",
                category = ExerciseCategory.BICEPS,
                difficulty = DifficultyLevel.INTERMEDIATE,
                defaultSets = 3,
                defaultReps = 12,
                targetMuscles = "Biceps Brachii (Short Head & Lower Tie-In)",
                primaryMuscles = listOf("Biceps Brachii"),
                secondaryMuscles = listOf("Brachialis"),
                instructions = listOf(
                    "Sit at preacher bench with upper arms resting flush against slanted pad.",
                    "Grip EZ-curl bar with underhand grip shoulder-width.",
                    "Curl bar upward toward chin until forearms are vertical.",
                    "Squeeze biceps hard for 1 second at peak contraction.",
                    "Lower bar under control until arms are almost fully extended."
                ),
                proTips = listOf(
                    "Preacher bench eliminates all shoulder momentum for strict bicep work.",
                    "Do not snap elbows into hyper-extension at bottom; maintain slight soft bend."
                ),
                commonMistakes = listOf(
                    "Lifting hips up off seat to gain leverage.",
                    "Dropping weight down fast risking bicep tendon strain."
                ),
                beginnerVariation = "Dumbbell Preacher Curls with lighter weight",
                equipmentRequired = "Preacher Bench & EZ-Bar",
                animationType = ExerciseAnimationType.PREACHER_CURL,
                animationAvailable = true
            ),

            // 44. CHEST: Incline Push-ups
            Exercise(
                id = "chest_incline_pushups",
                name = "Incline Push-ups",
                category = ExerciseCategory.CHEST,
                difficulty = DifficultyLevel.BEGINNER,
                defaultSets = 3,
                defaultReps = 12,
                targetMuscles = "Lower Pectorals, Triceps, Anterior Deltoids",
                primaryMuscles = listOf("Lower Pectorals"),
                secondaryMuscles = listOf("Triceps", "Core"),
                instructions = listOf(
                    "Place hands on sturdy elevated bench shoulder-width apart.",
                    "Step feet back until torso forms rigid diagonal angle.",
                    "Lower chest towards bench edge under control.",
                    "Drive through palms to return to starting lockout."
                ),
                proTips = listOf("The higher the bench, the lower the resistance load."),
                commonMistakes = listOf("Dropping head forward before chest touches bench."),
                beginnerVariation = "Increase elevation angle or use wall push-ups",
                equipmentRequired = "Gym Bench / Elevated Surface",
                animationType = ExerciseAnimationType.PUSH_UP_INCLINE,
                animationAvailable = true
            ),

            // 45. LEGS: Walking Dumbbell Lunges
            Exercise(
                id = "legs_walking_lunges",
                name = "Walking Lunges",
                category = ExerciseCategory.LEGS,
                difficulty = DifficultyLevel.INTERMEDIATE,
                defaultSets = 3,
                defaultReps = 12,
                targetMuscles = "Quadriceps, Gluteus Maximus, Hamstrings, Core",
                primaryMuscles = listOf("Quadriceps", "Gluteus Maximus"),
                secondaryMuscles = listOf("Hamstrings", "Calves", "Core"),
                instructions = listOf(
                    "Stand tall holding dumbbell in each hand at sides.",
                    "Take a large step forward with right foot and lower hips.",
                    "Both knees bend to roughly 90 degrees, back knee hovering just off floor.",
                    "Drive through front heel to step forward directly into next stride with left leg.",
                    "Maintain upright posture and proud chest throughout."
                ),
                proTips = listOf(
                    "Step out far enough so front knee doesn't cave inward or drive violently past toes.",
                    "Keep core braced for seamless walking balance."
                ),
                commonMistakes = listOf(
                    "Slouching torso forward over front thigh.",
                    "Banging back knee hard into the ground."
                ),
                beginnerVariation = "Bodyweight Stationary Lunges",
                equipmentRequired = "Dumbbells",
                animationType = ExerciseAnimationType.LUNGES_WALKING,
                animationAvailable = true
            ),

            // 46. CORE: Mountain Climbers
            Exercise(
                id = "core_mountain_climbers",
                name = "Mountain Climbers",
                category = ExerciseCategory.CORE,
                difficulty = DifficultyLevel.BEGINNER,
                defaultSets = 3,
                defaultReps = 30,
                isTimeBased = true,
                durationSeconds = 45,
                targetMuscles = "Rectus Abdominis, Hip Flexors, Deltoids, Heart Rate",
                primaryMuscles = listOf("Rectus Abdominis", "Hip Flexors"),
                secondaryMuscles = listOf("Deltoids", "Quadriceps", "Calves"),
                instructions = listOf(
                    "Start in high push-up plank position with hands under shoulders.",
                    "Drive right knee forward toward chest without hips rising high.",
                    "Quickly step right foot back while driving left knee forward.",
                    "Continue alternating legs in continuous running cadence.",
                    "Keep core braced and shoulders stable over hands."
                ),
                proTips = listOf(
                    "Do not let hips bounce high in air; maintain low, flat plank line.",
                    "Focus on driving knees straight toward center of chest."
                ),
                commonMistakes = listOf(
                    "Hips piking up into a triangle.",
                    "Bouncing shoulders up and down."
                ),
                beginnerVariation = "Slow deliberate step-ins without jumping cadence",
                equipmentRequired = "Exercise Mat",
                animationType = ExerciseAnimationType.MOUNTAIN_CLIMBERS,
                animationAvailable = true
            ),

            // 47. FUNCTIONAL: Medicine Ball Slam
            Exercise(
                id = "functional_med_ball_slam",
                name = "Medicine Ball Slam",
                category = ExerciseCategory.FUNCTIONAL,
                difficulty = DifficultyLevel.BEGINNER,
                defaultSets = 4,
                defaultReps = 15,
                targetMuscles = "Triple Extension, Latissimus Dorsi, Abdominals, Explosive Power",
                primaryMuscles = listOf("Abdominals", "Lats"),
                secondaryMuscles = listOf("Shoulders", "Glutes"),
                instructions = listOf(
                    "Stand with feet shoulder-width holding non-bouncing slam ball.",
                    "Inhale and lift ball overhead with full triple extension (ankles, knees, hips).",
                    "Hinge forward explosively and slam ball forcefully into floor between feet.",
                    "Catch ball on slight bounce or retrieve and repeat immediately."
                ),
                proTips = listOf("Follow through with hips and core to generate maximum downward force."),
                commonMistakes = listOf("Bending at spine without hip hinge."),
                beginnerVariation = "Lighter 8lb medicine ball",
                equipmentRequired = "Non-Bouncing Medicine Slam Ball",
                animationType = ExerciseAnimationType.MEDICINE_BALL_SLAM,
                animationAvailable = true
            ),

            // 48. CARDIO: Jumping Jacks
            Exercise(
                id = "cardio_jumping_jacks",
                name = "Jumping Jacks",
                category = ExerciseCategory.CARDIO,
                difficulty = DifficultyLevel.BEGINNER,
                defaultSets = 3,
                defaultReps = 30,
                isTimeBased = true,
                durationSeconds = 45,
                targetMuscles = "Calves, Deltoids, Aerobic Capacity",
                primaryMuscles = listOf("Calves"),
                secondaryMuscles = listOf("Deltoids", "Quadriceps"),
                instructions = listOf(
                    "Stand upright with feet together and arms at sides.",
                    "Jump feet out wider than shoulder-width while raising arms overhead.",
                    "Jump back to starting position with feet together and arms at sides.",
                    "Maintain continuous, light, rhythmic bounce."
                ),
                proTips = listOf("Land softly on balls of feet with knees slightly unlocked."),
                commonMistakes = listOf("Stiff-legged landing creating joint shock."),
                beginnerVariation = "Step-jack tapping one foot out at a time without jumping",
                equipmentRequired = "Bodyweight",
                animationType = ExerciseAnimationType.JUMPING_JACKS,
                animationAvailable = true
            ),

            // 49. TRICEPS: Parallel Bar Dips
            Exercise(
                id = "arms_triceps_dips",
                name = "Parallel Bar Dips",
                category = ExerciseCategory.TRICEPS,
                difficulty = DifficultyLevel.ADVANCED,
                defaultSets = 3,
                defaultReps = 10,
                targetMuscles = "Triceps Brachii, Anterior Deltoids, Lower Pectorals",
                primaryMuscles = listOf("Triceps Brachii"),
                secondaryMuscles = listOf("Anterior Deltoids", "Pectoralis Major"),
                instructions = listOf(
                    "Grip parallel dip bars and press up to straight arm lockout.",
                    "Keep torso upright (vertical) to isolate triceps over chest.",
                    "Lower body under control until elbows reach 90 degrees.",
                    "Drive through palms and lock out elbows at top."
                ),
                proTips = listOf("Keep elbows tucked close to sides rather than flaring out."),
                commonMistakes = listOf("Leaning too far forward shifting work to chest."),
                beginnerVariation = "Bench dips with feet on floor",
                equipmentRequired = "Parallel Dip Station",
                animationType = ExerciseAnimationType.TRICEPS_DIPS,
                animationAvailable = true
            )
        )
    }
}
