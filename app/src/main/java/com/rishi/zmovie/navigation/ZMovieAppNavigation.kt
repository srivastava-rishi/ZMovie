package com.rishi.zmovie.navigation

import com.rishi.zmovie.util.addRouteArgument


object ZMovieAppArgs {
    const val ARG_MOVIE_ID = "news_Id"
}

sealed class ZMovieAppScreen(val name: String, val route: String) {
    data object MovieListScreen : ZMovieAppScreen("movieList", "movieList")
    data object MovieDetailsScreen : ZMovieAppScreen(
        "movieDetails", "movieDetails"
            .addRouteArgument(ZMovieAppArgs.ARG_MOVIE_ID)
    )
}