package com.rishi.zmovie.data.models

import com.google.gson.annotations.SerializedName

data class TrendingMoviesResponse(
    val page: Int?,
    val results: List<TrendingMovie>? = null,
    val total_pages: Int?,
    val total_results: Int?,
)

data class TrendingMovie(
    val id: Int,
    val title: String? = null,
    val overview: String? = null,
    @SerializedName("poster_path") val posterPath: String? = null
)