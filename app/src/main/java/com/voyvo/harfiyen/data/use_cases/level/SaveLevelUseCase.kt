package com.voyvo.dragdrop.data.use_cases.level

import com.voyvo.dragdrop.repository.Repository

class SaveLevelUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(level : Int){
        repository.saveLevel(level = level)
    }
}
