package com.voyvo.dragdrop.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {

    suspend fun saveCurrentLevel(level : Int)
    fun readCurrentLevel() : Flow<Int?>
    suspend fun saveReachedMaxLevel(level : Int)
    fun readReachedMaxLevel() : Flow<Int?>
    suspend fun saveSoundState(boolean: Boolean)
    fun readSoundState() : Flow<Boolean?>
}