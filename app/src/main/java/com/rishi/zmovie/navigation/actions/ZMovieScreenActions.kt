package com.rishi.zmovie.navigation.actions

sealed class ScreenActions

sealed class MovieListScreenActions : ScreenActions() {
    data class OpenMovieDetailListScreen(
        val newsId: String
    ) : MovieListScreenActions()
}

sealed class MovieDetailsScreenActions : ScreenActions() {
    data object OnBack : MovieDetailsScreenActions()
}
