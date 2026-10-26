package com.example.bloom.screens.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.bloom.data.UserPreferencesManager
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun OnboardingFlow(
    preferencesManager: UserPreferencesManager,
    onFinishOnboarding: () -> Unit
) {
    val savedName by preferencesManager.userNameFlow.collectAsState(initial = "")

    val savedTrackers by preferencesManager.trackersFlow.collectAsState(
        initial = mapOf(
            "meals" to true,
            "movement" to true,
            "water" to true,
            "relaxation" to true,
            "sleep" to true,
            "cycle" to true,
            "symptom" to true
        )
    )

    val savedReminderType by preferencesManager.reminderTypeFlow.collectAsState(initial = "Morning")
    val savedCustomTime by preferencesManager.customTimeFlow.collectAsState(initial = "08:00 AM")

    var currentStep by remember { mutableIntStateOf(1) }
    val coroutineScope = rememberCoroutineScope()

    when (currentStep) {
        1 -> OnboardingStep1(
            onNextClicked = { currentStep = 2 }
        )
        2 -> OnboardingStep2(
            initialName = savedName,
            onBackClicked = { currentStep = 1 },
            onContinueClicked = { name ->
                coroutineScope.launch {
                    preferencesManager.saveUserName(name)
                    currentStep = 3
                }
            },
            onNameChanged = { name ->
                coroutineScope.launch {
                    preferencesManager.saveUserName(name)
                }
            }
        )
        3 -> OnboardingStep3(
            initialTrackers = savedTrackers,
            onBackClicked = { currentStep = 2 },
            onContinueClicked = { currentStep = 4 },
            onTrackerToggled = { trackerKey, isEnabled ->
                coroutineScope.launch {
                    preferencesManager.saveTrackerPreference(trackerKey, isEnabled)
                }
            }
        )
        4 -> OnboardingStep4(
            initialReminderType = savedReminderType,
            initialCustomTime = savedCustomTime,
            onBackClicked = { currentStep = 3 },
            onFinishClicked = {
                coroutineScope.launch {
                    preferencesManager.saveOnboardingCompleted(true)
                    onFinishOnboarding()
                }
            },
            onReminderSelected = { type, customTime ->
                coroutineScope.launch {
                    preferencesManager.saveReminderPreferences(type, customTime)
                }
            }
        )
    }
}