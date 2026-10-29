package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.FoodItem
import com.example.data.model.MealRecommendation
import com.example.data.repository.FitnessRepository
import com.example.ui.theme.KineticNeon
import com.example.ui.theme.KineticNeonLight
import com.example.ui.theme.MetricCyan
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun NutritionScreen(
    repository: FitnessRepository,
    modifier: Modifier = Modifier
) {
    val foods = remember { repository.getFoodItems() }
    val meals = remember { repository.getMealRecommendations() }

    var waterGlassesDrank by remember { mutableIntStateOf(6) }
    var selectedSection by remember { mutableStateOf("Meals") } // Meals or Food Library

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .padding(16.dp)
            .testTag("nutrition_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Column {
                Text(
                    text = "PRECISION FUEL",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = KineticNeon
                )
                Text(
                    text = "Nutrition & Macros",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = TextPrimary
                )
            }
        }

        // Daily Macro Target Card (Section 10)
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, KineticNeon.copy(alpha = 0.3f), RoundedCornerShape(18.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "CALORIC TARGET",
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace,
                                color = TextMuted
                            )
                            Text(
                                text = "1,840 / 2,300 kcal",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black,
                                fontFamily = FontFamily.Monospace,
                                color = TextPrimary
                            )
                        }
                        Box(
                            modifier = Modifier
                                .background(KineticNeon.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "80% CONSUMED",
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                color = KineticNeon
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Macro bars (Protein, Carbs, Fats)
                    MacroProgressBar(
                        label = "PROTEIN (HYPERTROPHY)",
                        current = 145f,
                        target = 160f,
                        unit = "g",
                        color = KineticNeon
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    MacroProgressBar(
                        label = "CARBOHYDRATES (GLYCOGEN)",
                        current = 180f,
                        target = 220f,
                        unit = "g",
                        color = MetricCyan
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    MacroProgressBar(
                        label = "HEALTHY FATS (HORMONES)",
                        current = 42f,
                        target = 55f,
                        unit = "g",
                        color = Color(0xFFFBBF24)
                    )
                }
            }
        }

        // Hydration Tracker (Section 10)
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(14.dp))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(MetricCyan.copy(alpha = 0.15f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalDrink,
                                contentDescription = null,
                                tint = MetricCyan,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Hydration Intake",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = "$waterGlassesDrank of 8 glasses (${(waterGlassesDrank * 0.3f)}L / 2.5L)",
                                fontSize = 11.sp,
                                color = TextSecondary,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    IconButton(
                        onClick = { if (waterGlassesDrank < 12) waterGlassesDrank++ },
                        modifier = Modifier
                            .size(36.dp)
                            .background(MetricCyan, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Water Glass",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        // Tabs: Meal Plans vs Food Library
        item {
            val tabs = listOf("Meals", "Food Library & Alternatives")
            ScrollableTabRow(
                selectedTabIndex = if (selectedSection == "Meals") 0 else 1,
                edgePadding = 0.dp,
                containerColor = Color.Transparent,
                indicator = { tabPositions ->
                    val index = if (selectedSection == "Meals") 0 else 1
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[index]),
                        color = KineticNeon,
                        height = 2.dp
                    )
                },
                divider = {}
            ) {
                tabs.forEach { title ->
                    val isSel = (title == "Meals" && selectedSection == "Meals") ||
                            (title != "Meals" && selectedSection != "Meals")
                    Tab(
                        selected = isSel,
                        onClick = { selectedSection = if (title == "Meals") "Meals" else "Foods" },
                        text = {
                            Text(
                                text = title.uppercase(),
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSel) KineticNeon else TextSecondary
                            )
                        }
                    )
                }
            }
        }

        if (selectedSection == "Meals") {
            items(meals) { meal ->
                MealCardItem(meal)
            }
        } else {
            items(foods) { food ->
                FoodCardItem(food)
            }
        }
    }
}

@Composable
fun MacroProgressBar(
    label: String,
    current: Float,
    target: Float,
    unit: String,
    color: Color
) {
    val progress = (current / target).coerceIn(0f, 1f)
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                color = TextMuted
            )
            Text(
                text = "${current.toInt()}/${target.toInt()} $unit (${(progress * 100).toInt()}%)",
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(CircleShape),
            color = color,
            trackColor = SurfaceContainerHigh
        )
    }
}

@Composable
fun MealCardItem(meal: MealRecommendation) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.White.copy(alpha = 0.06f), RoundedCornerShape(14.dp))
    ) {
        Column {
            meal.imageUrl?.let { url ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                ) {
                    AsyncImage(
                        model = url,
                        contentDescription = meal.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = meal.mealType.uppercase(),
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = KineticNeon
                    )
                    Text(
                        text = "${meal.proteinGrams}g Protein • ${meal.calories} kcal",
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = MetricCyan
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = meal.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = meal.description,
                    fontSize = 12.sp,
                    color = TextSecondary,
                    lineHeight = 17.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Vegetarian swap alternative
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceContainerHigh, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.SwapHoriz,
                        contentDescription = "Alternative",
                        tint = KineticNeon,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Veg Alternative: ${meal.alternative}",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }
            }
        }
    }
}

@Composable
fun FoodCardItem(food: FoodItem) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(12.dp))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = food.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "${food.calories} kcal",
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = KineticNeon
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Serving: ${food.servingSize}",
                fontSize = 11.sp,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Macro details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "P: ${food.proteinGrams}g",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = KineticNeon
                )
                Text(
                    text = "C: ${food.carbsGrams}g",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    color = MetricCyan
                )
                Text(
                    text = "F: ${food.fatsGrams}g",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color(0xFFFBBF24)
                )
            }

            food.vegetarianAlternative?.let { alt ->
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Swap: $alt",
                    fontSize = 10.sp,
                    color = TextSecondary
                )
            }
        }
    }
}
