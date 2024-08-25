package com.rishi.zmovie.di

import com.rishi.zmovie.data.repositories.TrendingMoviesRepository
import com.rishi.zmovie.data.sources.remote.TrendingMoviesRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun trendingMoviesRepository(
        dataSource: TrendingMoviesRemoteDataSource
    ) = TrendingMoviesRepository(dataSource)
}