package com.voyvo.dragdrop.repository

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(private val dataStore: DataStoreOperations) {

    suspend fun saveLevel(level : Int){
        dataStore.saveCurrentLevel(level = level)
    }

    fun readLevel() : Flow<Int?>{
        return dataStore.readCurrentLevel()
    }

    suspend fun saveMaxLevel(level : Int){
        dataStore.saveReachedMaxLevel(level = level)
    }

    fun readMaxLevel() : Flow<Int?>{
        return dataStore.readReachedMaxLevel()
    }


    suspend fun saveSoundState(boolean: Boolean){
        dataStore.saveSoundState(boolean)
    }

    fun readSoundState() : Flow<Boolean?> {
        return dataStore.readSoundState()
    }
}