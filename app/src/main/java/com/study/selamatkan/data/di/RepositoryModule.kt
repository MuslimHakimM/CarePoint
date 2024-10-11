package com.study.selamatkan.data.di

import com.study.selamatkan.data.repository.HealthRepository
import com.study.selamatkan.data.repository.IHealthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(healthRepository: HealthRepository): IHealthRepository
}