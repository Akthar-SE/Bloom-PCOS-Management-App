package com.example.bloom.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom.R
import com.example.bloom.ui.theme.BackgroundColor
import com.example.bloom.ui.theme.BodyColor
import com.example.bloom.ui.theme.InactivePink
import com.example.bloom.ui.theme.LoraFontFamily
import com.example.bloom.ui.theme.NunitoFontFamily
import com.example.bloom.ui.theme.PrimaryRose
import com.example.bloom.ui.theme.TextColor

@Composable
fun OnboardingStep1(
    onNextClicked: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
            .systemBarsPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        val maxWidth = maxWidth
        val maxHeight = maxHeight

        val logoSize = minOf(maxWidth, maxHeight) * 0.25f
        val starSizeLarge = minOf(maxWidth, maxHeight) * 0.065f
        val starSizeSmall = minOf(maxWidth, maxHeight) * 0.055f

        Image(
            painter = painterResource(id = R.drawable.star),
            contentDescription = null,
            modifier = Modifier
                .size(starSizeLarge)
                .align(Alignment.TopStart)
                .offset(x = maxWidth * 0.08f, y = maxHeight * 0.06f)
        )

        Image(
            painter = painterResource(id = R.drawable.star),
            contentDescription = null,
            alpha = 0.7f,
            modifier = Modifier
                .size(starSizeSmall)
                .align(Alignment.TopEnd)
                .offset(x = -(maxWidth * 0.08f), y = maxHeight * 0.08f)
        )

        Image(
            painter = painterResource(id = R.drawable.star),
            contentDescription = null,
            modifier = Modifier
                .size(starSizeLarge)
                .align(Alignment.BottomStart)
                .offset(x = maxWidth * 0.08f, y = -(maxHeight * 0.20f))
        )

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
                Spacer(modifier = Modifier.size(38.dp))

                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProgressSegment(isActive = true, modifier = Modifier.weight(1f))
                    ProgressSegment(isActive = false, modifier = Modifier.weight(1f))
                    ProgressSegment(isActive = false, modifier = Modifier.weight(1f))
                    ProgressSegment(isActive = false, modifier = Modifier.weight(1f))
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 16.dp)
                    .clip(RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Onboarding Logo",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(logoSize)
                            .padding(bottom = 20.dp)
                    )

                    Text(
                        text = "Welcome to Bloom",
                        fontSize = 32.sp,
                        fontFamily = LoraFontFamily,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        color = TextColor,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "A simple daily companion for managing\nPCOS — track your cycle, meals,\nmovement, water, relaxation and sleep.",
                        fontSize = 15.sp,
                        fontFamily = NunitoFontFamily,
                        color = BodyColor,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp
                    )
                }
            }

            Button(
                onClick = onNextClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryRose,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Get started",
                    fontSize = 16.sp,
                    fontFamily = NunitoFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ProgressSegment(
    isActive: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(5.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(if (isActive) PrimaryRose else InactivePink)
    )
}