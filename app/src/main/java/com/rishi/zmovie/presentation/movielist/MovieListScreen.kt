package com.rishi.zmovie.presentation.movielist

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.rishi.zmovie.navigation.actions.MovieListScreenActions

@Composable
fun MovieListScreen(
    viewModel: MovieListViewModel = hiltViewModel(),
    onAction: (movieListScreenActions: MovieListScreenActions) -> Unit
) {
}


@Preview
@Composable
fun MovieListPreview() {
}