package com.rishi.zmovie.navigation

import androidx.navigation.NavController
import com.rishi.zmovie.navigation.ZMovieAppArgs.ARG_MOVIE_ID
import com.rishi.zmovie.navigation.actions.MovieDetailsScreenActions
import com.rishi.zmovie.navigation.actions.MovieListScreenActions
import com.rishi.zmovie.util.withArg

class ZMovieNavActions(
    private val navController: NavController,
    private val onFinish: () -> Unit
) {
    private fun back() {
        navController.popBackStack()
    }

    private fun finishActivity() {
        onFinish()
    }

    fun navigateFromMovieListScreen(actions: MovieListScreenActions) {
        when (actions) {
            is MovieListScreenActions.OpenMovieDetailListScreen -> {
                navController.navigate(
                    ZMovieAppScreen.MovieDetailsScreen.name
                        .withArg(ARG_MOVIE_ID, actions.movieId)
                )
            }
        }
    }

    fun navigateFromMovieDetailsScreen(actions: MovieDetailsScreenActions) {
        when (actions) {
            is MovieDetailsScreenActions.OnBack -> {
                back()
            }
        }
    }
}