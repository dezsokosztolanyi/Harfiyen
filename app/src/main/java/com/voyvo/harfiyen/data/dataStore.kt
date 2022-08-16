package com.voyvo.dragdrop.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.voyvo.dragdrop.repository.DataStoreOperations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(
    name = "game_preferences"
)

class DataStoreOperationsImpl(context: Context) : DataStoreOperations {

    private object PreferencesKey {
        //key oluşturduk
        val levelKey = intPreferencesKey(name = "level_preferences")
        val soundKey = booleanPreferencesKey(name = "sound_preferences")
        val maxLevelKey = intPreferencesKey(name = "max_level_preferences")
    }

    //datastore objesi
    private val dataStore = context.dataStore

    override suspend fun saveCurrentLevel(level: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.levelKey] = level
        }
    }

    override fun readCurrentLevel(): Flow<Int?> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onBoardingState = preferences[PreferencesKey.levelKey]
                onBoardingState
            }
    }

    override suspend fun saveSoundState(boolean: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.soundKey] = boolean
        }
    }

    override fun readSoundState(): Flow<Boolean?> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onBoardingState = preferences[PreferencesKey.soundKey]
                onBoardingState
            }
    }

    override suspend fun saveReachedMaxLevel(level: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.maxLevelKey] = level
        }
    }

    override fun readReachedMaxLevel(): Flow<Int?> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onBoardingState = preferences[PreferencesKey.maxLevelKey]
                onBoardingState
            }
    }
}

