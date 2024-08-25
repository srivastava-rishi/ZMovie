package com.rishi.zmovie.di

import com.google.gson.GsonBuilder
import com.rishi.zmovie.BuildConfig
import com.rishi.zmovie.data.ZMovieApiClientService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideZMovieApiClientService(): ZMovieApiClientService =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(ZMovieApiClientService::class.java)
}