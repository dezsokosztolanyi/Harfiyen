package com.voyvo.dragdrop.data.use_cases.max_level

import com.voyvo.dragdrop.repository.Repository

class SaveMaxLevelUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(level : Int){
        repository.saveMaxLevel(level = level)
    }
}