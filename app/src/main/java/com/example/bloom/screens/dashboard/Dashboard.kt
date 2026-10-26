package com.example.bloom.screens.dashboard

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom.ui.theme.BackgroundColor
import com.example.bloom.ui.theme.BodyColor
import com.example.bloom.ui.theme.LoraFontFamily
import com.example.bloom.ui.theme.NunitoFontFamily
import com.example.bloom.ui.theme.PrimaryRose
import com.example.bloom.ui.theme.TextColor
import kotlinx.coroutines.launch
import java.util.Calendar
import com.example.bloom.R
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.text.style.TextOverflow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@SuppressLint("NonObservableLocale")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Dashboard(
    userName: String,
    isTipMarkedDone: Boolean,
    onMarkTipDone: suspend () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToNavTab: (String) -> Unit
) {
    var isTipHiddenForSession by remember { mutableStateOf(false) }
    var selectedBottomNav by remember { mutableStateOf("home") }
    val coroutineScope = rememberCoroutineScope()

    val greetingText = remember { getDynamicGreeting() }

    Scaffold(
        bottomBar = {
            BloomBottomNavigation(
                selectedTab = selectedBottomNav,
                onTabSelected = { tab ->
                    selectedBottomNav = tab
                    onNavigateToNavTab(tab)
                }
            )
        },
        containerColor = BackgroundColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = NunitoFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp
                            )
                        ) {
                            append("${greetingText.uppercase()}, ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontFamily = LoraFontFamily,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        ) {
                            append(if (userName.isNotBlank()) userName else "Friend")
                        }
                    },
                    color = TextColor
                )

                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(PrimaryRose.copy(alpha = 0.15f), shape = CircleShape)
                        .clip(CircleShape)
                        .clickable { onNavigateToProfile() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_profile),
                        contentDescription = "Profile",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = PrimaryRose.copy(alpha = 0.08f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Today's Goals",
                                fontSize = 22.sp,
                                fontFamily = LoraFontFamily,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold,
                                color = TextColor
                            )

                            Text(
                                text = LocalDate.now().format(
                                    DateTimeFormatter.ofPattern("EEEE, MMMM d", Locale.getDefault())
                                ),
                                fontSize = 13.sp,
                                fontFamily = NunitoFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                color = PrimaryRose
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Let's get blooming",
                                fontSize = 15.sp,
                                fontFamily = LoraFontFamily,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold,
                                color = TextColor
                            )

                            Text(
                                text = "0 of 5 complete",
                                fontSize = 12.sp,
                                fontFamily = NunitoFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                color = BodyColor
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            LinearProgressIndicator(
                                progress = { 0f },
                                modifier = Modifier
                                    .fillMaxWidth(0.95f)
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = PrimaryRose,
                                trackColor = PrimaryRose.copy(alpha = 0.2f),
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Box(
                            modifier = Modifier.size(150.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.flower),
                                contentDescription = "Flower",
                                modifier = Modifier
                                    .size(220.dp)
                                    .graphicsLayer(alpha = 0.5f)
                            )

                            Text(
                                text = "Start here",
                                fontSize = 16.sp,
                                fontFamily = LoraFontFamily,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold,
                                color = TextColor,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                val pagerState = rememberPagerState(pageCount = { 16 })

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "PCOS FAQ",
                        fontSize = 16.sp,
                        fontFamily = LoraFontFamily,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        color = TextColor
                    )
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth()
                ) { page ->
                    FaqCardItem(index = page + 1)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(16) { index ->
                        val isSelected = pagerState.currentPage == index
                        Box(
                            modifier = Modifier
                                .width(if (isSelected) 18.dp else 7.dp)
                                .height(7.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) PrimaryRose else PrimaryRose.copy(alpha = 0.25f)
                                )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Goal Progress",
                    fontSize = 20.sp,
                    fontFamily = LoraFontFamily,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    color = TextColor,
                    modifier = Modifier.padding(bottom = 2.dp)
                )

                GoalProgressTile(
                    title = "Meals",
                    subtitle = "0/3 meals",
                    iconRes = R.drawable.ic_meals,
                    accentColor = Color(0xFFB85C50),
                    onClick = { }
                )
                GoalProgressTile(
                    title = "Movement",
                    subtitle = "0/30 min",
                    iconRes = R.drawable.ic_movement,
                    accentColor = Color(0xFF6B9080),
                    onClick = { }
                )
                GoalProgressTile(
                    title = "Water",
                    subtitle = "0/8 glasses",
                    iconRes = R.drawable.ic_water,
                    accentColor = Color(0xFF4A829E),
                    onClick = { }
                )
                GoalProgressTile(
                    title = "Relaxation",
                    subtitle = "0/15 min",
                    iconRes = R.drawable.ic_relaxation,
                    accentColor = Color(0xFF8878B0),
                    onClick = { }
                )
                GoalProgressTile(
                    title = "Sleep",
                    subtitle = "0/8 hrs",
                    iconRes = R.drawable.ic_sleep,
                    accentColor = Color(0xFF56587A),
                    onClick = { }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (!isTipMarkedDone && !isTipHiddenForSession) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Today's tip",
                        fontSize = 20.sp,
                        fontFamily = LoraFontFamily,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        color = TextColor
                    )

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, PrimaryRose.copy(alpha = 0.2f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(
                                            PrimaryRose.copy(alpha = 0.10f),
                                            shape = RoundedCornerShape(12.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_sleep),
                                        contentDescription = "Sleep Tip",
                                        modifier = Modifier.size(22.dp)
                                    )
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Daily Sleep Tip",
                                        fontSize = 14.sp,
                                        fontFamily = NunitoFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        color = TextColor
                                    )
                                    Text(
                                        text = "Consistent 8-hour sleep cycles regulate cortisol and help balance PCOS hormone fluctuations.",
                                        fontSize = 12.sp,
                                        fontFamily = NunitoFontFamily,
                                        color = BodyColor,
                                        lineHeight = 16.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextButton(onClick = { isTipHiddenForSession = true }) {
                                    Text(
                                        text = "Remind me later",
                                        fontSize = 12.sp,
                                        fontFamily = NunitoFontFamily,
                                        color = BodyColor
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Button(
                                    onClick = {
                                        coroutineScope.launch {
                                            onMarkTipDone()
                                        }
                                    },
                                    shape = RoundedCornerShape(14.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryRose),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = "Mark as done",
                                        fontSize = 12.sp,
                                        fontFamily = NunitoFontFamily,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GoalProgressTile(
    title: String,
    subtitle: String,
    iconRes: Int,
    accentColor: Color,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .border(width = 4.dp, color = accentColor, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = title,
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(accentColor)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontFamily = NunitoFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = TextColor
                )
                Text(
                    text = subtitle,
                    fontSize = 13.sp,
                    fontFamily = NunitoFontFamily,
                    color = BodyColor
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = "View goal",
                modifier = Modifier.size(16.dp),
                colorFilter = ColorFilter.tint(TextColor.copy(alpha = 0.6f))
            )
        }
    }
}

@Composable
fun FaqCardItem(index: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "What exactly is PCOS?",
                    fontSize = 17.sp,
                    fontFamily = NunitoFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = TextColor
                )
                Text(
                    text = "PCOS stands for polycystic ovary syndrome...",
                    fontSize = 13.sp,
                    fontFamily = NunitoFontFamily,
                    color = BodyColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = "View details",
                modifier = Modifier.size(16.dp),
                colorFilter = ColorFilter.tint(TextColor.copy(alpha = 0.6f))
            )
        }
    }
}

@Composable
fun BloomBottomNavigation(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        val items = listOf(
            Triple("home", "Home", R.drawable.ic_home),
            Triple("insights", "Insights", R.drawable.ic_insights),
            Triple("learn", "Learn", R.drawable.ic_learn),
            Triple("settings", "Settings", R.drawable.ic_settings)
        )

        items.forEach { (tabKey, label, iconRes) ->
            val isSelected = selectedTab == tabKey
            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(tabKey) },
                icon = {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) PrimaryRose.copy(alpha = 0.15f) else Color.Transparent)
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = iconRes),
                            contentDescription = label,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                },
                label = {
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontFamily = NunitoFontFamily,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) PrimaryRose else BodyColor
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

private fun getDynamicGreeting(): String {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when (hour) {
        in 5..11 -> "Good Morning"
        in 12..16 -> "Good Afternoon"
        else -> "Good Evening"
    }
}