package com.voyvo.dragdrop.data.use_cases.sound

import com.voyvo.dragdrop.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadSoundStateUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke() : Flow<Boolean?> {
        return repository.readSoundState()
    }
}