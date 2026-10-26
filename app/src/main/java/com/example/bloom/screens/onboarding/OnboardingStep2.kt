package com.example.bloom.screens.onboarding

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom.ui.theme.BackgroundColor
import com.example.bloom.ui.theme.BodyColor
import com.example.bloom.ui.theme.LoraFontFamily
import com.example.bloom.ui.theme.NunitoFontFamily
import com.example.bloom.ui.theme.PrimaryRose
import com.example.bloom.ui.theme.TextColor
import kotlinx.coroutines.launch
import com.example.bloom.R

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun OnboardingStep2(
    initialName: String,
    onBackClicked: () -> Unit,
    onContinueClicked: (String) -> Unit,
    onNameChanged: suspend (String) -> Unit
) {
    var nameInput by remember { mutableStateOf(initialName) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(initialName) {
        if (initialName.isNotBlank() && nameInput.isBlank()) {
            nameInput = initialName
        }
    }

    val nameRegex = Regex("""^[a-zA-Z\s]*$""")
    val isInputValid = nameInput.isNotBlank() && nameInput.matches(nameRegex)

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
                horizontalArrangement = Arrangement.spacedBy(14.dp)
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
                    ProgressSegment(isActive = false, modifier = Modifier.weight(1f))
                    ProgressSegment(isActive = false, modifier = Modifier.weight(1f))
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "A little about you",
                    fontSize = 28.sp,
                    fontFamily = LoraFontFamily,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    color = TextColor,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                val customTextSelectionColors = TextSelectionColors(
                    handleColor = PrimaryRose,
                    backgroundColor = PrimaryRose.copy(alpha = 0.4f)
                )

                CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = { newValue ->
                            if (newValue.matches(Regex("""^[a-zA-Z\s]*$"""))) {
                                nameInput = newValue
                                coroutineScope.launch {
                                    onNameChanged(newValue)
                                }
                            }
                        },
                        placeholder = {
                            Text(
                                text = "Your first name",
                                fontFamily = NunitoFontFamily,
                                color = BodyColor.copy(alpha = 0.6f)
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryRose,
                            unfocusedBorderColor = PrimaryRose.copy(alpha = 0.4f),
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            cursorColor = PrimaryRose
                        ),
                        textStyle = TextStyle(
                            fontFamily = NunitoFontFamily,
                            fontSize = 16.sp,
                            color = TextColor
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    )
                }
            }

            Button(
                onClick = { onContinueClicked(nameInput) },
                enabled = isInputValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .padding(bottom = 8.dp),
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