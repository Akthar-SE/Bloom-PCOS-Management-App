package com.example.bloom.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.bloom.R
import com.example.bloom.ui.theme.BackgroundColor

@Preview(showBackground = true)
@Composable
fun Splash() {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
            .systemBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        val maxWidth = maxWidth
        val maxHeight = maxHeight

        val logoSize = minOf(maxWidth, maxHeight) * 0.35f
        val starSizeLarge = minOf(maxWidth, maxHeight) * 0.065f
        val starSizeSmall = minOf(maxWidth, maxHeight) * 0.055f

        Image(
            painter = painterResource(id = R.drawable.star),
            contentDescription = null,
            modifier = Modifier
                .size(starSizeLarge)
                .align(Alignment.TopStart)
                .offset(x = maxWidth * 0.12f, y = maxHeight * 0.12f)
        )

        Image(
            painter = painterResource(id = R.drawable.star),
            contentDescription = null,
            alpha = 0.7f,
            modifier = Modifier
                .size(starSizeSmall)
                .align(Alignment.TopEnd)
                .offset(x = -(maxWidth * 0.12f), y = maxHeight * 0.15f)
        )

        Image(
            painter = painterResource(id = R.drawable.star),
            contentDescription = null,
            modifier = Modifier
                .size(starSizeLarge)
                .align(Alignment.BottomStart)
                .offset(x = maxWidth * 0.10f, y = -(maxHeight * 0.22f))
        )

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Splash Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(logoSize)
        )
    }
}