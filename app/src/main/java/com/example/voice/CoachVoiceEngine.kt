package com.example.voice

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class CoachVoiceEngine(private val context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isInitialized = false

    var isVoiceEnabled: Boolean = true
    var speechRate: Float = 1.0f
        set(value) {
            field = value
            tts?.setSpeechRate(value)
        }
    var voiceTone: String = "Energetic Coach" // Energetic Coach or Calm Instructional
    var language: String = "English"

    init {
        tts = TextToSpeech(context.applicationContext, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isInitialized = true
            // Configure natural pitch and speech rate for a clear, encouraging coach
            tts?.setPitch(1.05f)
            tts?.setSpeechRate(speechRate)

            // Set Language strictly to English (US)
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                tts?.setLanguage(Locale.ENGLISH)
            }

            // Try to set a clear English female voice if available on device
            try {
                val femaleVoice = tts?.voices?.firstOrNull { voice ->
                    voice.locale.language.equals("en", ignoreCase = true) &&
                        (voice.name.contains("female", ignoreCase = true) ||
                         voice.name.contains("#female", ignoreCase = true) ||
                         voice.name.contains("en-us-x-sfg", ignoreCase = true) ||
                         voice.name.contains("en-us-x-tpf", ignoreCase = true) ||
                         voice.name.contains("en-us-x-iob", ignoreCase = true))
                } ?: tts?.voices?.firstOrNull { it.locale.language.equals("en", ignoreCase = true) }
                if (femaleVoice != null) {
                    tts?.voice = femaleVoice
                }
            } catch (e: Exception) {
                Log.w("CoachVoiceEngine", "Could not set specific voice: ${e.message}")
            }
        } else {
            Log.e("CoachVoiceEngine", "TextToSpeech initialization failed: $status")
        }
    }

    fun speak(text: String, queueMode: Int = TextToSpeech.QUEUE_FLUSH) {
        if (!isVoiceEnabled || !isInitialized) return
        tts?.speak(text, queueMode, null, "CoachVoiceUtterance_${System.currentTimeMillis()}")
    }

    fun stop() {
        tts?.stop()
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }

    // Pre-scripted clear English guidance
    fun speakWorkoutStart(workoutTitle: String) {
        speak("Welcome! Today we are working on $workoutTitle. Bring your best energy and let's get started!")
    }

    fun speakExerciseIntro(exerciseName: String, sets: Int, reps: Int, isTime: Boolean = false) {
        if (isTime) {
            speak("Next exercise is $exerciseName. Target is $reps seconds. Get into position and brace your core.")
        } else {
            speak("Get into position for $exerciseName. Keep your body aligned and core tight. Let's do this!")
        }
    }

    fun speakFormCue(cue: String) {
        speak(cue)
    }

    fun speakRepCount(repNumber: Int) {
        val message = when (repNumber) {
            1 -> "One!"
            2 -> "Two, controlled!"
            3 -> "Three!"
            5 -> "Five, halfway through!"
            8 -> "Eight, drive up!"
            10 -> "Ten, squeeze at the peak!"
            12 -> "Twelve, strong finish!"
            else -> "$repNumber"
        }
        speak(message)
    }

    fun speakWorkTimerStart(exerciseName: String, seconds: Int, setNumber: Int = 1) {
        speak("Timer started! Perform $exerciseName for $seconds seconds. Go!")
    }

    fun playStopBuzzer() {
        try {
            val toneGen = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100)
            toneGen.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 650)
        } catch (e: Exception) {
            Log.e("CoachVoiceEngine", "Tone play failed", e)
        }
    }

    fun triggerStopVibration() {
        try {
            val timings = longArrayOf(0, 450, 150, 450, 150, 650)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    (context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager)?.defaultVibrator
                } else {
                    @Suppress("DEPRECATION")
                    context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                }
                vibrator?.vibrate(VibrationEffect.createWaveform(timings, -1))
            } else {
                @Suppress("DEPRECATION")
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                vibrator?.vibrate(timings, -1)
            }
        } catch (e: Exception) {
            Log.e("CoachVoiceEngine", "Stop vibration failed", e)
        }
    }

    fun speakStopSignal(setNumber: Int = 1, isFinalSet: Boolean = true, restSeconds: Int = 0) {
        playStopBuzzer()
        triggerStopVibration()
        speak("STOP! STOP! Time is up! Exercise complete! Great work!")
    }

    fun speakWorkTimerComplete(setNumber: Int, restSeconds: Int) {
        speakStopSignal(setNumber, true, restSeconds)
    }

    fun speakCountdownTick(secondsRemaining: Int) {
        if (secondsRemaining in 1..3) {
            speak("$secondsRemaining")
        }
    }

    fun speakRestCountdown(seconds: Int) {
        speak("Great work! Rest for $seconds seconds. Breathe deeply and hydrate.")
    }

    fun speakRestComplete() {
        speak("Rest complete! Shake it out and get ready.")
    }

    fun speakWorkoutComplete(totalExercises: Int, totalReps: Int) {
        speak("Outstanding session! Today's workout is complete. $totalExercises exercises finished. Keep building your strength!")
    }

    fun triggerVibration() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                val vibrator = vibratorManager?.defaultVibrator
                vibrator?.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator?.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator?.vibrate(200)
                }
            }
        } catch (e: Exception) {
            Log.e("CoachVoiceEngine", "Vibration failed", e)
        }
    }
}
