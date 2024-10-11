package com.study.selamatkan.data.di

import com.study.selamatkan.data.domain.usecase.HealthInteractor
import com.study.selamatkan.data.domain.usecase.HealthUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideHealthUseCase(healthInteractor: HealthInteractor): HealthUseCase
}