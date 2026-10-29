package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ai.GeminiCoachService
import com.example.data.model.ChatMessage
import com.example.ui.theme.CriticalRed
import com.example.ui.theme.KineticNeon
import com.example.ui.theme.MetricCyan
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.voice.CoachVoiceEngine
import kotlinx.coroutines.launch

@Composable
fun CoachChatScreen(
    coachService: GeminiCoachService,
    voiceEngine: CoachVoiceEngine,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val messages = remember {
        mutableStateListOf(
            ChatMessage(
                sender = "marcus",
                text = "Welcome to your training session! Coach here. Today is focused on building strength and endurance. If you have any questions on form, sets, or recovery, let me know. Let's get started!",
                timestamp = "6:30 PM"
            ),
            ChatMessage(
                sender = "user",
                text = "My shoulder feels slightly tight during push-ups, what should I adjust?",
                timestamp = "6:32 PM"
            ),
            ChatMessage(
                sender = "marcus",
                text = "Stop immediately if you feel sharp pain! Check your hand position: hands should be slightly wider than shoulder-width, and elbows tucked at a 45-degree angle, never flared out at 90 degrees. If discomfort persists, switch to incline push-ups today.",
                timestamp = "6:33 PM",
                isSafetyAlert = true,
                actionSuggestion = "Switch to Incline Push-ups today"
            )
        )
    }

    var inputText by remember { mutableStateOf("") }
    var isSending by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    val quickChips = listOf(
        "⚡ How to progressive overload?",
        "🥗 Best vegetarian protein sources?",
        "📐 Form check: Common push-up mistakes",
        "🩹 Shoulder clicks during bench press",
        "😴 Rest days kyun important hain?"
    )

    LaunchedEffect(messages.size) {
        listState.animateScrollToItem((messages.size - 1).coerceAtLeast(0))
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .testTag("coach_chat_screen")
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceContainerLow)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("chat_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TextPrimary
                )
            }

            // Coach Marcus Avatar
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .border(2.dp, KineticNeon, CircleShape)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_coach_avatar),
                    contentDescription = "Coach Marcus",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Coach Marcus",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .background(KineticNeon.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 4.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = "AI COACH",
                            fontSize = 8.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = KineticNeon
                        )
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(KineticNeon, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Online • Natural Neural Voice",
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                }
            }

            // Voice Test Button
            IconButton(
                onClick = {
                    voiceEngine.speak("Alex! Train smart, keep your body straight and never compromise on form. Let's do this!")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.VolumeUp,
                    contentDescription = "Test Coach Voice",
                    tint = KineticNeon
                )
            }
        }

        // Active Blueprint Summary Card (Section 15 / Screenshot 2)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ACTIVE BLUEPRINT",
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = MetricCyan
                    )
                    Text(
                        text = "WEEK 3 OF 8",
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = KineticNeon
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Hypertrophy Split • Phase 2",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Schedule Days Pill Bar (M, T, W, T, F, S, S)
                val days = listOf(
                    "M" to "Chest/Tri",
                    "T" to "Cardio",
                    "W" to "Back/Bi",
                    "T" to "Mobility",
                    "F" to "Legs/Core",
                    "S" to "Circuit",
                    "S" to "Rest"
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    days.forEachIndexed { index, (dayLetter, split) ->
                        val isToday = index == 0 // Monday Chest/Tri
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .background(
                                    if (isToday) KineticNeon.copy(alpha = 0.2f) else SurfaceContainerHigh,
                                    RoundedCornerShape(6.dp)
                                )
                                .border(
                                    if (isToday) 1.dp else 0.dp,
                                    if (isToday) KineticNeon else Color.Transparent,
                                    RoundedCornerShape(6.dp)
                                )
                                .padding(horizontal = 6.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = dayLetter,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isToday) KineticNeon else TextPrimary
                            )
                            Text(
                                text = if (isToday) "TODAY" else split.take(3),
                                fontSize = 8.sp,
                                fontFamily = FontFamily.Monospace,
                                color = if (isToday) KineticNeon else TextMuted
                            )
                        }
                    }
                }
            }
        }

        // Messages List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(messages) { message ->
                if (message.sender == "user") {
                    UserMessageBubble(message)
                } else {
                    MarcusMessageBubble(
                        message = message,
                        onSpeak = { voiceEngine.speak(message.text) }
                    )
                }
            }

            if (isSending) {
                item {
                    Row(
                        modifier = Modifier.padding(start = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Marcus is formulating biomechanical advice...",
                            fontSize = 12.sp,
                            color = KineticNeon,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }

        // Quick Suggestion Chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(quickChips) { chipText ->
                Box(
                    modifier = Modifier
                        .background(SurfaceContainerHigh, RoundedCornerShape(16.dp))
                        .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                        .clickable {
                            inputText = chipText.replace(Regex("^[⚡🥗📐🩹😴]\\s*"), "")
                        }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = chipText,
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
            }
        }

        // Chat Input Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceContainerLow)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    voiceEngine.speak("I'm listening, Alex. Ask your question in Hinglish or English.")
                },
                modifier = Modifier
                    .size(40.dp)
                    .background(SurfaceContainerHigh, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Voice Input",
                    tint = KineticNeon,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = {
                    Text(
                        text = "Ask Marcus (English / Hinglish)...",
                        fontSize = 13.sp,
                        color = TextMuted
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("chat_input_field"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = KineticNeon,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.12f),
                    focusedContainerColor = SurfaceContainerHigh,
                    unfocusedContainerColor = SurfaceContainerHigh,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                shape = RoundedCornerShape(20.dp),
                maxLines = 3
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = {
                    val query = inputText.trim()
                    if (query.isNotBlank() && !isSending) {
                        val userMsg = ChatMessage(
                            sender = "user",
                            text = query,
                            timestamp = "Just now"
                        )
                        messages.add(userMsg)
                        inputText = ""
                        isSending = true

                        scope.launch {
                            val reply = coachService.getCoachResponse(query, messages)
                            messages.add(reply)
                            isSending = false
                            voiceEngine.speak(reply.text)
                        }
                    }
                },
                modifier = Modifier
                    .size(42.dp)
                    .background(KineticNeon, CircleShape)
                    .testTag("chat_send_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun MarcusMessageBubble(
    message: ChatMessage,
    onSpeak: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .border(1.5.dp, KineticNeon, CircleShape)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_coach_avatar),
                contentDescription = "Coach Marcus",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.fillMaxWidth(0.92f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Coach Marcus",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = KineticNeon
                )
                Text(
                    text = message.timestamp,
                    fontSize = 10.sp,
                    color = TextMuted,
                    fontFamily = FontFamily.Monospace
                )
                IconButton(
                    onClick = onSpeak,
                    modifier = Modifier.size(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Speak message",
                        tint = KineticNeon,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (message.isSafetyAlert) CriticalRed.copy(alpha = 0.12f) else SurfaceContainerHigh
                ),
                shape = RoundedCornerShape(
                    topStart = 4.dp,
                    topEnd = 16.dp,
                    bottomEnd = 16.dp,
                    bottomStart = 16.dp
                ),
                modifier = Modifier.border(
                    width = 1.dp,
                    color = if (message.isSafetyAlert) CriticalRed.copy(alpha = 0.6f) else Color.White.copy(alpha = 0.08f),
                    shape = RoundedCornerShape(
                        topStart = 4.dp,
                        topEnd = 16.dp,
                        bottomEnd = 16.dp,
                        bottomStart = 16.dp
                    )
                )
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    if (message.isSafetyAlert) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Safety Alert",
                                tint = CriticalRed,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "SAFETY & JOINT INTEGRITY ALERT",
                                color = CriticalRed,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Text(
                        text = message.text,
                        fontSize = 13.sp,
                        color = TextPrimary,
                        lineHeight = 20.sp
                    )

                    message.actionSuggestion?.let { suggestion ->
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .background(KineticNeon.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                                .border(1.dp, KineticNeon.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "⚡ Suggested: $suggestion",
                                color = KineticNeon,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserMessageBubble(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.fillMaxWidth(0.85f)
        ) {
            Text(
                text = "Alex • ${message.timestamp}",
                fontSize = 10.sp,
                color = TextMuted,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(2.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = KineticNeon.copy(alpha = 0.15f)),
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 4.dp,
                    bottomEnd = 16.dp,
                    bottomStart = 16.dp
                ),
                modifier = Modifier.border(
                    1.dp,
                    KineticNeon.copy(alpha = 0.35f),
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 4.dp,
                        bottomEnd = 16.dp,
                        bottomStart = 16.dp
                    )
                )
            ) {
                Text(
                    text = message.text,
                    fontSize = 13.sp,
                    color = TextPrimary,
                    modifier = Modifier.padding(12.dp),
                    lineHeight = 20.sp
                )
            }
        }
    }
}
