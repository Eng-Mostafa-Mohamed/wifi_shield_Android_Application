package com.example.data.repoImpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.domain.repo.OnboardingRepository
import kotlinx.coroutines.flow.first

class OnboardingRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : OnboardingRepository {
    private val FIRST_TIME_KEY = booleanPreferencesKey("first_time_user")

    override suspend fun isFirstTimeUser(): Boolean {
        val prefs = dataStore.data.first()
        return prefs[FIRST_TIME_KEY] ?: true
    }

    override suspend fun setFirstTimeUser(value: Boolean) {
        dataStore.edit { prefs ->
            prefs[FIRST_TIME_KEY] = value
        }
    }
}
