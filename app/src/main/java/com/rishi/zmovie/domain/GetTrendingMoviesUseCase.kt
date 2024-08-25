package com.rishi.zmovie.domain

import com.rishi.zmovie.data.repositories.TrendingMoviesRepository
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(private val repository: TrendingMoviesRepository) {
    operator fun invoke(language: String) = repository.getTrendingMovies(language)
}