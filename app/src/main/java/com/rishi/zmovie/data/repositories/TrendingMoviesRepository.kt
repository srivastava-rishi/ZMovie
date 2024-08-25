package com.rishi.zmovie.data.repositories

import com.rishi.zmovie.data.models.TrendingMoviesResponse
import com.rishi.zmovie.data.sources.remote.TrendingMoviesRemoteDataSource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject


class TrendingMoviesRepository @Inject constructor(
    private val dataSource: TrendingMoviesRemoteDataSource,
) {
    fun getTrendingMovies(
        language: String
    ): Flow<Response<TrendingMoviesResponse>> {
        return dataSource.getTrendingMovies(language)
    }
}

