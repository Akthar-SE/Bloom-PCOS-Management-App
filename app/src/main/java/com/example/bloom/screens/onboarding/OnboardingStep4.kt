package com.example.bloom.screens.onboarding

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom.ui.theme.BackgroundColor
import com.example.bloom.ui.theme.PrimaryRose
import kotlinx.coroutines.launch
import java.util.Calendar
import com.example.bloom.R
import com.example.bloom.ui.theme.BodyColor
import com.example.bloom.ui.theme.LoraFontFamily
import com.example.bloom.ui.theme.NunitoFontFamily
import com.example.bloom.ui.theme.TextColor

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun OnboardingStep4(
    initialReminderType: String,
    initialCustomTime: String,
    onBackClicked: () -> Unit,
    onFinishClicked: () -> Unit,
    onReminderSelected: suspend (String, String) -> Unit
) {
    var selectedType by remember { mutableStateOf(initialReminderType.ifEmpty { "Morning" }) }
    var customTime by remember { mutableStateOf(initialCustomTime.ifEmpty { "08:00 AM" }) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    @SuppressLint("DefaultLocale")
    fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->
                val formattedTime = String.format(
                    "%02d:%02d %s",
                    if (selectedHour == 0 || selectedHour == 12) 12 else selectedHour % 12,
                    selectedMinute,
                    if (selectedHour < 12) "AM" else "PM"
                )
                customTime = formattedTime
                selectedType = "Custom"
                coroutineScope.launch {
                    onReminderSelected("Custom", formattedTime)
                }
            },
            hour,
            minute,
            false
        )
        timePickerDialog.show()
    }

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
                    ProgressSegment(isActive = true, modifier = Modifier.weight(1f))
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
                    painter = painterResource(id = R.drawable.clock),
                    contentDescription = null,
                    modifier = Modifier.size(45.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "When should we remind you?",
                    fontSize = 24.sp,
                    fontFamily = LoraFontFamily,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    color = TextColor,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "We'll send one daily nudge to log how you're doing.",
                    fontSize = 13.sp,
                    fontFamily = NunitoFontFamily,
                    color = BodyColor,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                    Text(
                        text = "Pick a time of day",
                        fontSize = 14.sp,
                        fontFamily = NunitoFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = BodyColor,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    ReminderOptionCard(
                        title = "Morning",
                        subtitle = "7:30 AM — start the day on track",
                        isSelected = selectedType == "Morning",
                        onClick = {
                            selectedType = "Morning"
                            coroutineScope.launch { onReminderSelected("Morning", customTime) }
                        }
                    )
                    ReminderOptionCard(
                        title = "Afternoon",
                        subtitle = "1:00 PM — a midday check-in",
                        isSelected = selectedType == "Afternoon",
                        onClick = {
                            selectedType = "Afternoon"
                            coroutineScope.launch { onReminderSelected("Afternoon", customTime) }
                        }
                    )
                    ReminderOptionCard(
                        title = "Evening",
                        subtitle = "8:00 PM — wind down and reflect",
                        isSelected = selectedType == "Evening",
                        onClick = {
                            selectedType = "Evening"
                            coroutineScope.launch { onReminderSelected("Evening", customTime) }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                    Text(
                        text = "Or set a custom time",
                        fontSize = 14.sp,
                        fontFamily = NunitoFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = BodyColor,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .border(
                            width = if (selectedType == "Custom") 1.5.dp else 0.5.dp,
                            color = if (selectedType == "Custom") PrimaryRose else PrimaryRose.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .clickable { showTimePicker() }
                        .padding(horizontal = 20.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = customTime,
                            fontSize = 16.sp,
                            fontFamily = NunitoFontFamily,
                            fontWeight = FontWeight.Medium,
                            color = TextColor
                        )

                        Image(
                            painter = painterResource(id = R.drawable.ic_clock),
                            contentDescription = "Clock",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onFinishClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryRose,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Enter Bloom",
                        fontSize = 16.sp,
                        fontFamily = NunitoFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onFinishClicked) {
                    Text(
                        text = "I'll set this up later",
                        fontSize = 13.sp,
                        fontFamily = NunitoFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = BodyColor
                    )
                }
            }
        }
    }
}

@Composable
fun ReminderOptionCard(
    title: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(
            width = if (isSelected) 1.5.dp else 0.5.dp,
            color = if (isSelected) PrimaryRose else PrimaryRose.copy(alpha = 0.2f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 2.dp else 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontFamily = NunitoFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = TextColor
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    fontFamily = NunitoFontFamily,
                    color = BodyColor
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = PrimaryRose,
                    unselectedColor = PrimaryRose.copy(alpha = 0.4f)
                )
            )
        }
    }
}