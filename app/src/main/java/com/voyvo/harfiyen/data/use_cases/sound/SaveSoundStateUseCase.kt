package com.voyvo.dragdrop.data.use_cases.sound

import com.voyvo.dragdrop.repository.Repository

class SaveSoundStateUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(boolean: Boolean){
        repository.saveSoundState(boolean = boolean)
    }
}