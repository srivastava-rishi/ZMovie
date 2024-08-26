package com.rishi.zmovie.di

import com.rishi.zmovie.data.repositories.TrendingMoviesRepository
import com.rishi.zmovie.domain.GetTrendingMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped


@Module
@InstallIn(ActivityRetainedComponent::class)
object ActivityScopeUseCaseModule {

    @Provides
    @ActivityRetainedScoped
    fun getViewRequestUseCase(
        repository: TrendingMoviesRepository,
    ) = GetTrendingMoviesUseCase(repository)
}