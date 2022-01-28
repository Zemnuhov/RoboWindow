package com.robo.window.data.dagger

import com.robo.window.data.implementations.ConnectionRepositoryImpl
import com.robo.window.domain.repository.ConnectionRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaggerRepositoryModule {

    @Provides
    @Singleton
    fun provideConnectionRepository(): ConnectionRepository {
        return ConnectionRepositoryImpl()
    }
}