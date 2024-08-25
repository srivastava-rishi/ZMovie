package com.rishi.zmovie.data.sources.remote

import com.rishi.zmovie.BuildConfig
import com.rishi.zmovie.data.ZMovieApiClientService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TrendingMoviesRemoteDataSource @Inject constructor(
    private val service: ZMovieApiClientService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun getTrendingMovies(
        language: String
    ) = flow {
        val response = service.getTrendingMovies(
            language,
            BuildConfig.API_KEY
        )
        emit(response)
    }.flowOn(dispatcher)
}
