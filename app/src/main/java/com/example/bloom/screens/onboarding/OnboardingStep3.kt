package com.example.bloom.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom.R
import com.example.bloom.ui.theme.BackgroundColor
import com.example.bloom.ui.theme.BodyColor
import com.example.bloom.ui.theme.LoraFontFamily
import com.example.bloom.ui.theme.NunitoFontFamily
import com.example.bloom.ui.theme.PrimaryRose
import com.example.bloom.ui.theme.TextColor
import kotlinx.coroutines.launch

@Composable
fun OnboardingStep3(
    initialTrackers: Map<String, Boolean>,
    onBackClicked: () -> Unit,
    onContinueClicked: () -> Unit,
    onTrackerToggled: suspend (String, Boolean) -> Unit
) {
    var trackerStates by remember {
        mutableStateOf(
            initialTrackers.ifEmpty {
                mapOf(
                    "meals" to true,
                    "movement" to true,
                    "water" to true,
                    "relaxation" to true,
                    "sleep" to true,
                    "cycle" to true,
                    "symptom" to true
                )
            }
        )
    }

    LaunchedEffect(initialTrackers) {
        if (initialTrackers.isNotEmpty()) {
            trackerStates = initialTrackers
        }
    }

    val coroutineScope = rememberCoroutineScope()

    val isAnySelected = trackerStates.values.any { it }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
            .systemBarsPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .background(PrimaryRose.copy(alpha = 0.15f), shape = CircleShape)
                        .clip(CircleShape)
                        .clickable(onClick = onBackClicked),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back_arrow),
                        contentDescription = "Back",
                        tint = PrimaryRose,
                        modifier = Modifier.size(25.dp)
                    )
                }

                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProgressSegment(isActive = true, modifier = Modifier.weight(1f))
                    ProgressSegment(isActive = true, modifier = Modifier.weight(1f))
                    ProgressSegment(isActive = true, modifier = Modifier.weight(1f))
                    ProgressSegment(isActive = false, modifier = Modifier.weight(1f))
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(top = 16.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.flower),
                    contentDescription = null,
                    modifier = Modifier.size(45.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "What would you like to track daily?",
                    fontSize = 24.sp,
                    fontFamily = LoraFontFamily,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    color = TextColor,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Pick the goals that matter to you — only these will show up on your dashboard. Partial progress still counts, and your flower fills in as you go.",
                    fontSize = 13.sp,
                    fontFamily = NunitoFontFamily,
                    color = BodyColor,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        TrackerSwitchItem(
                            iconRes = R.drawable.ic_meals,
                            title = "Meals · 3 balanced meals",
                            subtitle = "Steadier blood sugar helps keep PCOS symptoms in check.",
                            checked = trackerStates["meals"] ?: true,
                            onCheckedChange = { isChecked ->
                                trackerStates = trackerStates.toMutableMap().apply { this["meals"] = isChecked }
                                coroutineScope.launch { onTrackerToggled("meals", isChecked) }
                            }
                        )
                        TrackerSwitchItem(
                            iconRes = R.drawable.ic_movement,
                            title = "Movement · 30 minutes",
                            subtitle = "Supports insulin sensitivity, a key lever in PCOS.",
                            checked = trackerStates["movement"] ?: true,
                            onCheckedChange = { isChecked ->
                                trackerStates = trackerStates.toMutableMap().apply { this["movement"] = isChecked }
                                coroutineScope.launch { onTrackerToggled("movement", isChecked) }
                            }
                        )
                        TrackerSwitchItem(
                            iconRes = R.drawable.ic_water,
                            title = "Water · 8 glasses",
                            subtitle = "Helps with energy and bloating.",
                            checked = trackerStates["water"] ?: true,
                            onCheckedChange = { isChecked ->
                                trackerStates = trackerStates.toMutableMap().apply { this["water"] = isChecked }
                                coroutineScope.launch { onTrackerToggled("water", isChecked) }
                            }
                        )
                        TrackerSwitchItem(
                            iconRes = R.drawable.ic_relaxation,
                            title = "Relaxation · 15 minutes",
                            subtitle = "Lower stress means lower cortisol, which can worsen symptoms.",
                            checked = trackerStates["relaxation"] ?: true,
                            onCheckedChange = { isChecked ->
                                trackerStates = trackerStates.toMutableMap().apply { this["relaxation"] = isChecked }
                                coroutineScope.launch { onTrackerToggled("relaxation", isChecked) }
                            }
                        )
                        TrackerSwitchItem(
                            iconRes = R.drawable.ic_sleep,
                            title = "Sleep · 8 hours",
                            subtitle = "Consistent sleep helps balance hormones over time.",
                            checked = trackerStates["sleep"] ?: true,
                            onCheckedChange = { isChecked ->
                                trackerStates = trackerStates.toMutableMap().apply { this["sleep"] = isChecked }
                                coroutineScope.launch { onTrackerToggled("sleep", isChecked) }
                            }
                        )
                        TrackerSwitchItem(
                            iconRes = R.drawable.ic_cycle,
                            title = "Cycle · period dates",
                            subtitle = "Spot your personal pattern and predict your next period.",
                            checked = trackerStates["cycle"] ?: true,
                            onCheckedChange = { isChecked ->
                                trackerStates = trackerStates.toMutableMap().apply { this["cycle"] = isChecked }
                                coroutineScope.launch { onTrackerToggled("cycle", isChecked) }
                            }
                        )
                        TrackerSwitchItem(
                            iconRes = R.drawable.ic_symptoms,
                            title = "Symptom check-in · bloating, skin, mood, sleep quality",
                            subtitle = "Track how you feel day to day to notice patterns over time.",
                            checked = trackerStates["symptom"] ?: true,
                            onCheckedChange = { isChecked ->
                                trackerStates = trackerStates.toMutableMap().apply { this["symptom"] = isChecked }
                                coroutineScope.launch { onTrackerToggled("symptom", isChecked) }
                            },
                            showDivider = false
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Targets and what you track can be adjusted any time in Settings.",
                    fontSize = 11.sp,
                    fontFamily = NunitoFontFamily,
                    color = BodyColor.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
            }

            Button(
                onClick = onContinueClicked,
                enabled = isAnySelected,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .padding(top = 8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryRose,
                    contentColor = Color.White,
                    disabledContainerColor = PrimaryRose.copy(alpha = 0.3f),
                    disabledContentColor = Color.White.copy(alpha = 0.6f)
                )
            ) {
                Text(
                    text = "Continue",
                    fontSize = 16.sp,
                    fontFamily = NunitoFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun TrackerSwitchItem(
    iconRes: Int,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    showDivider: Boolean = true
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(PrimaryRose.copy(alpha = 0.08f), shape = RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        fontSize = 13.sp,
                        fontFamily = NunitoFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = TextColor
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        fontSize = 11.sp,
                        fontFamily = NunitoFontFamily,
                        color = BodyColor,
                        lineHeight = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = PrimaryRose,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = PrimaryRose.copy(alpha = 0.2f),
                    uncheckedBorderColor = Color.Transparent
                )
            )
        }

        if (showDivider) {
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(
                thickness = 0.5.dp,
                color = PrimaryRose.copy(alpha = 0.15f)
            )
        }
    }
}