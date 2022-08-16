package com.voyvo.dragdrop.data.use_cases.max_level

import com.voyvo.dragdrop.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadMaxLevelUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke() : Flow<Int?> {
        return repository.readMaxLevel()
    }
}