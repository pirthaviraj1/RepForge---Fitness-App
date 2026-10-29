package com.example.data.db

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "workout_history")
data class WorkoutHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val routineTitle: String,
    val timestamp: Long = System.currentTimeMillis(),
    val durationSeconds: Int,
    val completedExercisesCount: Int,
    val totalSets: Int,
    val totalReps: Int,
    val totalVolumeKg: Int
)

@Entity(tableName = "favorite_exercises")
data class FavoriteExerciseEntity(
    @PrimaryKey val exerciseId: String,
    val addedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val name: String = "Alex",
    val goal: String = "Hypertrophy & Muscle Strength",
    val currentWeightKg: Float = 76.5f,
    val targetWeightKg: Float = 80.0f,
    val streakDays: Int = 7,
    val pushUpRecord: Int = 38,
    val plankRecordSeconds: Int = 120,
    val totalWorkoutsCompleted: Int = 24
)

@Dao
interface FitnessDao {
    @Query("SELECT * FROM workout_history ORDER BY timestamp DESC")
    fun getAllWorkoutHistory(): Flow<List<WorkoutHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutHistory(history: WorkoutHistoryEntity)

    @Query("SELECT COUNT(*) FROM workout_history")
    fun getTotalWorkoutsCount(): Flow<Int>

    @Query("SELECT * FROM favorite_exercises")
    fun getFavoriteExerciseIds(): Flow<List<FavoriteExerciseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteExerciseEntity)

    @Query("DELETE FROM favorite_exercises WHERE exerciseId = :exerciseId")
    suspend fun removeFavorite(exerciseId: String)

    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    fun getUserProfile(): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserProfile(profile: UserProfileEntity)
}

@Database(
    entities = [
        WorkoutHistoryEntity::class,
        FavoriteExerciseEntity::class,
        UserProfileEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fitnessDao(): FitnessDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "repforge_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
