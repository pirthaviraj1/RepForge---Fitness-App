package com.example.ai

import android.util.Log
import com.example.BuildConfig
import com.example.data.model.ChatMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class GeminiCoachService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())

    suspend fun getCoachResponse(userQuery: String, conversationHistory: List<ChatMessage>): ChatMessage = withContext(Dispatchers.IO) {
        val queryLower = userQuery.lowercase()
        val currentTime = timeFormat.format(Date())

        // Safety Trigger Check (Section 13: Injury/Pain Detection)
        val isPainQuery = queryLower.contains("pain") || queryLower.contains("dard") ||
                queryLower.contains("hurt") || queryLower.contains("injury") ||
                queryLower.contains("shoulder") && queryLower.contains("karun")

        // Try Gemini API if API key is provided
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (!apiKey.isNullOrBlank() && apiKey != "MY_GEMINI_API_KEY") {
            try {
                val apiResponse = callGeminiRestApi(userQuery, conversationHistory, apiKey)
                if (!apiResponse.isNullOrBlank()) {
                    return@withContext ChatMessage(
                        sender = "marcus",
                        text = apiResponse,
                        timestamp = currentTime,
                        isSafetyAlert = isPainQuery,
                        actionSuggestion = if (isPainQuery) "Switch to Incline or Knee Push-ups today" else null
                    )
                }
            } catch (e: Exception) {
                Log.e("GeminiCoachService", "Gemini API call failed, falling back to local intelligence", e)
            }
        }

        // Domain-specific instant fitness intelligence matching Marcus Coach persona
        val responseText: String
        var isSafety = false
        var suggestion: String? = null

        when {
            isPainQuery && (queryLower.contains("shoulder") || queryLower.contains("push")) -> {
                isSafety = true
                suggestion = "Switch to Incline or Knee Push-ups today"
                responseText = "Stop immediately if you feel sharp pain! Check your hand placement: hands should be slightly wider than shoulder-width, and elbows tucked at a 45-degree angle, never flared out at 90 degrees. If discomfort persists, switch to incline push-ups today."
            }
            queryLower.contains("pain") || queryLower.contains("dard") || queryLower.contains("injury") -> {
                isSafety = true
                suggestion = "Take 24h rest or consult a physical therapist"
                responseText = "Attention! Number one rule: Never train through sharp acute pain. Stop this exercise immediately, apply gentle ice or mobility if needed, and let the affected joint recover. Long-term consistency comes from staying healthy and injury-free."
            }
            queryLower.contains("progressive overload") || queryLower.contains("overload") -> {
                responseText = "Progressive overload is the fundamental driver of muscle growth! This means challenging your muscles systematically over time: (1) Add 1-2 extra reps with good form, (2) Increase resistance by 2-5%, or (3) Improve your tempo with controlled 3-second eccentrics."
            }
            queryLower.contains("vegetarian") || queryLower.contains("veg protein") || queryLower.contains("protein") -> {
                responseText = "Top high-protein vegetarian sources:\n• Cottage Cheese (Paneer) – 18g protein / 100g\n• Soya Chunks – 52g protein / 100g\n• Cooked Lentils & Chickpeas – 14g / cup\n• Greek Yogurt – 15g / cup\n• Whey or Plant Protein Isolate."
            }
            queryLower.contains("squat") && (queryLower.contains("mistake") || queryLower.contains("form")) -> {
                responseText = "Avoid these 3 common squat mistakes:\n1. Knees caving inward (drive knees out in line with toes).\n2. Heels lifting off the ground (drive through your whole foot).\n3. Rounding your lower back (keep your chest upright and spine neutral)."
            }
            queryLower.contains("rest") || queryLower.contains("recovery") || queryLower.contains("sleep") -> {
                responseText = "Muscles are stimulated during workouts, but they repair and grow during rest and deep sleep! Aim for 7 to 8 hours of quality sleep for peak growth hormone release and muscle recovery."
            }
            queryLower.contains("warm") || queryLower.contains("warmup") -> {
                responseText = "Always perform 5 to 7 minutes of dynamic warmup before lifting: Arm circles, jumping jacks, hip openers, and 1 to 2 light warmup sets. This primes your nervous system and lubricates your joints."
            }
            else -> {
                responseText = "Great question! Proper form and deliberate mind-muscle connection always beat lifting too heavy too fast. Control the lowering phase with purpose and drive upward with power. Keep up the strong effort!"
            }
        }

        ChatMessage(
            sender = "marcus",
            text = responseText,
            timestamp = currentTime,
            isSafetyAlert = isSafety,
            actionSuggestion = suggestion
        )
    }

    private fun callGeminiRestApi(query: String, history: List<ChatMessage>, apiKey: String): String? {
        val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"

        val contentsArray = JSONArray()

        // System prompt as first turn or systemInstruction
        val systemContent = JSONObject().apply {
            put("role", "user")
            put("parts", JSONArray().put(JSONObject().put("text",
                "You are an elite personal fitness coach and biomechanics specialist. " +
                        "You communicate in motivating, clear, professional English. " +
                        "If the user reports pain or symptoms, immediately prioritize safety: instruct stopping, checking form (elbow tuck, neutral spine), and substituting regressions. " +
                        "Keep answers energetic, concise, and scientifically grounded."
            )))
        }
        contentsArray.put(systemContent)

        // Recent history
        val recentHistory = history.takeLast(4)
        for (msg in recentHistory) {
            val role = if (msg.sender == "user") "user" else "model"
            contentsArray.put(JSONObject().apply {
                put("role", role)
                put("parts", JSONArray().put(JSONObject().put("text", msg.text)))
            })
        }

        // Current query
        contentsArray.put(JSONObject().apply {
            put("role", "user")
            put("parts", JSONArray().put(JSONObject().put("text", query)))
        })

        val requestJson = JSONObject().apply {
            put("contents", contentsArray)
            put("generationConfig", JSONObject().apply {
                put("temperature", 0.7)
                put("maxOutputTokens", 500)
            })
        }

        val body = requestJson.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        val response = client.newCall(request).execute()
        val responseBody = response.body?.string() ?: return null

        val responseJson = JSONObject(responseBody)
        val candidates = responseJson.optJSONArray("candidates") ?: return null
        if (candidates.length() > 0) {
            val candidate = candidates.getJSONObject(0)
            val content = candidate.optJSONObject("content")
            val parts = content?.optJSONArray("parts")
            if (parts != null && parts.length() > 0) {
                return parts.getJSONObject(0).optString("text")
            }
        }
        return null
    }
}
