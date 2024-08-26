package com.rishi.zmovie.domain

import com.rishi.zmovie.BuildConfig
import com.rishi.zmovie.data.models.TrendingMovie
import com.rishi.zmovie.data.repositories.TrendingMoviesRepository
import com.rishi.zmovie.presentation.movielist.MovieItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(private val repository: TrendingMoviesRepository) {


    private var cacheMovieResponse = mutableListOf<MovieItem>()

    operator fun invoke(language: String): Flow<List<MovieItem>?> = flow {
        repository.getTrendingMovies(language).collect { response ->
            val movies = response.body()?.results?.map { it.toMovieItem() } ?: emptyList()
            cacheMovieResponse.addAll(movies)
            emit(cacheMovieResponse)
        }
    }

    fun getMovieById(movieId: String): MovieItem? {
        return cacheMovieResponse.find { it.id == movieId }
    }
}

fun TrendingMovie.toMovieItem(): MovieItem {
    return MovieItem(
        id = this.id.toString(),
        title = this.title,
        description = this.overview,
        moviePoster = makeMoviePosterUrl(this.posterPath)
    )
}


fun makeMoviePosterUrl(imageId: String?): String {
    return imageId?.let { BuildConfig.IMAGE_BASE_URL + it } ?: ""
}