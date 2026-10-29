package com.example.ui.screens

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BodybuildingProgram
import com.example.data.repository.FitnessRepository
import com.example.ui.theme.KineticNeon
import com.example.ui.theme.MetricCyan
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun WorkoutsScreen(
    repository: FitnessRepository,
    onStartWorkout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val programs = remember { repository.getPrograms() }
    var selectedCategory by remember { mutableStateOf("All") }

    val categories = listOf("All", "Beginner Programs", "Intermediate Programs", "Advanced Programs")
    val filteredPrograms = remember(selectedCategory) {
        if (selectedCategory == "All") programs else programs.filter { it.category == selectedCategory }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .testTag("workouts_screen")
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "TRAINING PROTOCOLS",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = KineticNeon
                        )
                        Text(
                            text = "Programs & Splits",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            color = TextPrimary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .background(SurfaceContainerHigh, RoundedCornerShape(10.dp))
                            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(10.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "${filteredPrograms.size} ROUTINES",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            color = KineticNeon
                        )
                    }
                }
            }

            // Category Filter Tabs
            item {
                ScrollableTabRow(
                    selectedTabIndex = categories.indexOf(selectedCategory).coerceAtLeast(0),
                    edgePadding = 0.dp,
                    containerColor = Color.Transparent,
                    indicator = { tabPositions ->
                        val index = categories.indexOf(selectedCategory).coerceAtLeast(0)
                        if (index < tabPositions.size) {
                            TabRowDefaults.SecondaryIndicator(
                                Modifier.tabIndicatorOffset(tabPositions[index]),
                                color = KineticNeon,
                                height = 2.dp
                            )
                        }
                    },
                    divider = {}
                ) {
                    categories.forEach { cat ->
                        val isSelected = selectedCategory == cat
                        Tab(
                            selected = isSelected,
                            onClick = { selectedCategory = cat },
                            text = {
                                Text(
                                    text = cat.replace(" Programs", "").uppercase(),
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) KineticNeon else TextSecondary
                                )
                            }
                        )
                    }
                }
            }

            // Programs Cards
            items(filteredPrograms, key = { it.id }) { prog ->
                ProgramCardItem(
                    program = prog,
                    onStart = onStartWorkout
                )
            }
        }
    }
}

@Composable
fun ProgramCardItem(
    program: BodybuildingProgram,
    onStart: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.White.copy(alpha = 0.06f), RoundedCornerShape(16.dp))
            .testTag("program_card_${program.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = program.category.uppercase(),
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = MetricCyan
                )

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box(
                        modifier = Modifier
                            .background(SurfaceContainerHigh, RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "${program.durationWeeks} WEEKS",
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = KineticNeon
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(SurfaceContainerHigh, RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "${program.daysPerWeek} DAYS/WK",
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            color = TextSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = program.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = program.description,
                fontSize = 12.sp,
                color = TextSecondary,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Equipment: ${program.requiredEquipment}",
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Schedule Pills
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                program.schedule.take(3).forEach { day ->
                    Text(
                        text = "• $day",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = onStart,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SurfaceContainerHigh,
                    contentColor = KineticNeon
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "START TODAY'S SESSION",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}
