package com.rishi.zmovie.navigation


object ZMovieAppArgs {
    const val ARG_MOVIE_ID = "news_Id"
}

sealed class ZMovieAppScreen(val name: String, val route: String) {
    data object MovieListScreen : ZMovieAppScreen("movieList", "movieList")
    data object MovieDetailsScreen : ZMovieAppScreen("movieDetails", "movieDetails")
}