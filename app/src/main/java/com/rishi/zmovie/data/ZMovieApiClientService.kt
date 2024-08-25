package com.rishi.zmovie.data

import com.rishi.zmovie.data.models.TrendingMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ZMovieApiClientService {

    @GET(PATH_TRENDING_DATA)
    suspend fun getTrendingMovies(
        @Query("language") language: String,
        @Query("api_key") apiKey: String
    ): Response<TrendingMoviesResponse>

    companion object {
        // ***************** //
        private const val PATH_TRENDING_DATA = "trending/movie/week"
    }
}