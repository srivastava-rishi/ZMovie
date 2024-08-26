package com.rishi.zmovie.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import com.rishi.zmovie.presentation.moviedetails.MovieDetailsScreen
import com.rishi.zmovie.presentation.movielist.MovieListScreen
import com.rishi.zmovie.util.animatedComposable

@Composable
fun ZMovieNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = ZMovieAppScreen.MovieListScreen.route,
    navActions: ZMovieNavActions
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        animatedComposable(ZMovieAppScreen.MovieListScreen.route) {
            MovieListScreen(
                onAction = navActions::navigateFromMovieListScreen
            )
        }

        animatedComposable(
            route = ZMovieAppScreen.MovieDetailsScreen.route,
            arguments = listOf(
                navArgument(ZMovieAppArgs.ARG_MOVIE_ID) { type = NavType.StringType }
            )
        ) {
            MovieDetailsScreen(
                onAction = navActions::navigateFromMovieDetailsScreen
            )
        }
    }
}
