package com.voyvo.dragdrop.data.use_cases

import com.voyvo.dragdrop.data.use_cases.level.ReadLevelUseCase
import com.voyvo.dragdrop.data.use_cases.level.SaveLevelUseCase
import com.voyvo.dragdrop.data.use_cases.max_level.ReadMaxLevelUseCase
import com.voyvo.dragdrop.data.use_cases.max_level.SaveMaxLevelUseCase
import com.voyvo.dragdrop.data.use_cases.sound.ReadSoundStateUseCase
import com.voyvo.dragdrop.data.use_cases.sound.SaveSoundStateUseCase

data class UseCases(
    val saveLevelUseCase: SaveLevelUseCase,
    val readLevelUseCase: ReadLevelUseCase,
    val saveMaxLevelUseCase: SaveMaxLevelUseCase,
    val readMaxLevelUseCase: ReadMaxLevelUseCase,
    val saveSoundStateUseCase: SaveSoundStateUseCase,
    val readSoundStateUseCase: ReadSoundStateUseCase
    )