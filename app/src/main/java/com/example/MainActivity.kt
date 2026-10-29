package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.ai.GeminiCoachService
import com.example.data.db.AppDatabase
import com.example.data.repository.FitnessRepository
import com.example.ui.navigation.RepForgeApp
import com.example.ui.theme.RepForgeTheme
import com.example.voice.CoachVoiceEngine

class MainActivity : ComponentActivity() {

    private lateinit var voiceEngine: CoachVoiceEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(this)
        val repository = FitnessRepository(database)
        val coachService = GeminiCoachService()
        voiceEngine = CoachVoiceEngine(this)

        setContent {
            RepForgeTheme {
                RepForgeApp(
                    repository = repository,
                    coachService = coachService,
                    voiceEngine = voiceEngine
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        voiceEngine.shutdown()
    }
}
