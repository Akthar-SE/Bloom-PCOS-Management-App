package com.example.bloom.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey // Changed to boolean for completion status
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "bloom_preferences")

class UserPreferencesManager(private val context: Context) {

    companion object {
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val ONBOARDING_COMPLETED_KEY = booleanPreferencesKey("onboarding_status")

        val TRACK_MEALS_KEY = booleanPreferencesKey("track_meals")
        val TRACK_MOVEMENT_KEY = booleanPreferencesKey("track_movement")
        val TRACK_WATER_KEY = booleanPreferencesKey("track_water")
        val TRACK_RELAXATION_KEY = booleanPreferencesKey("track_relaxation")
        val TRACK_SLEEP_KEY = booleanPreferencesKey("track_sleep")
        val TRACK_CYCLE_KEY = booleanPreferencesKey("track_cycle")
        val TRACK_SYMPTOM_KEY = booleanPreferencesKey("track_symptom")

        val REMINDER_TYPE_KEY = stringPreferencesKey("reminder_type")
        val CUSTOM_TIME_KEY = stringPreferencesKey("custom_time")

        val TIP_SLEEP_DONE_KEY = booleanPreferencesKey("tip_sleep_done")
    }

    val userNameFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USER_NAME_KEY] ?: ""
        }

    suspend fun saveUserName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = name
        }
    }

    val onboardingCompletedFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[ONBOARDING_COMPLETED_KEY] ?: false
        }

    suspend fun saveOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED_KEY] = completed
        }
    }

    val trackersFlow: Flow<Map<String, Boolean>> = context.dataStore.data
        .map { preferences ->
            mapOf(
                "meals" to (preferences[TRACK_MEALS_KEY] ?: true),
                "movement" to (preferences[TRACK_MOVEMENT_KEY] ?: true),
                "water" to (preferences[TRACK_WATER_KEY] ?: true),
                "relaxation" to (preferences[TRACK_RELAXATION_KEY] ?: true),
                "sleep" to (preferences[TRACK_SLEEP_KEY] ?: true),
                "cycle" to (preferences[TRACK_CYCLE_KEY] ?: true),
                "symptom" to (preferences[TRACK_SYMPTOM_KEY] ?: true)
            )
        }

    suspend fun saveTrackerPreference(key: String, isEnabled: Boolean) {
        context.dataStore.edit { preferences ->
            when (key) {
                "meals" -> preferences[TRACK_MEALS_KEY] = isEnabled
                "movement" -> preferences[TRACK_MOVEMENT_KEY] = isEnabled
                "water" -> preferences[TRACK_WATER_KEY] = isEnabled
                "relaxation" -> preferences[TRACK_RELAXATION_KEY] = isEnabled
                "sleep" -> preferences[TRACK_SLEEP_KEY] = isEnabled
                "cycle" -> preferences[TRACK_CYCLE_KEY] = isEnabled
                "symptom" -> preferences[TRACK_SYMPTOM_KEY] = isEnabled
            }
        }
    }

    val reminderTypeFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[REMINDER_TYPE_KEY] ?: "Morning"
        }

    val customTimeFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[CUSTOM_TIME_KEY] ?: "08:00 AM"
        }

    suspend fun saveReminderPreferences(type: String, time: String) {
        context.dataStore.edit { preferences ->
            preferences[REMINDER_TYPE_KEY] = type
            preferences[CUSTOM_TIME_KEY] = time
        }
    }

    val isSleepTipDoneFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[TIP_SLEEP_DONE_KEY] ?: false
        }

    suspend fun setSleepTipDone(done: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[TIP_SLEEP_DONE_KEY] = done
        }
    }
}