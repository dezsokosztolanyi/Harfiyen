package com.voyvo.dragdrop.data.use_cases.level

import com.voyvo.dragdrop.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadLevelUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke() : Flow<Int?> {
        return repository.readLevel()
    }
}