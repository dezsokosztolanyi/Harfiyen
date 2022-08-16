package com.voyvo.dragdrop.di

import android.content.Context
import com.voyvo.dragdrop.data.DataStoreOperationsImpl
import com.voyvo.dragdrop.data.use_cases.*
import com.voyvo.dragdrop.data.use_cases.level.ReadLevelUseCase
import com.voyvo.dragdrop.data.use_cases.level.SaveLevelUseCase
import com.voyvo.dragdrop.data.use_cases.max_level.ReadMaxLevelUseCase
import com.voyvo.dragdrop.data.use_cases.max_level.SaveMaxLevelUseCase
import com.voyvo.dragdrop.data.use_cases.sound.ReadSoundStateUseCase
import com.voyvo.dragdrop.data.use_cases.sound.SaveSoundStateUseCase
import com.voyvo.dragdrop.repository.DataStoreOperations
import com.voyvo.dragdrop.repository.Repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUseCases(repository: Repository) : UseCases {
        return UseCases(
            saveLevelUseCase = SaveLevelUseCase(repository = repository),
            readLevelUseCase = ReadLevelUseCase(repository = repository),
            saveMaxLevelUseCase = SaveMaxLevelUseCase(repository = repository),
            readMaxLevelUseCase = ReadMaxLevelUseCase(repository = repository),
            saveSoundStateUseCase = SaveSoundStateUseCase(repository = repository),
            readSoundStateUseCase = ReadSoundStateUseCase(repository = repository)
        )
    }

    @Provides
    @Singleton
    fun provideDataStoreOperations(@ApplicationContext context: Context) : DataStoreOperations {
        return DataStoreOperationsImpl(context = context)
    }
}