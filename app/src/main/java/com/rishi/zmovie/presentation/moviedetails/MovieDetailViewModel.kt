package com.rishi.zmovie.presentation.moviedetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.rishi.zmovie.domain.GetTrendingMoviesUseCase
import com.rishi.zmovie.navigation.ZMovieAppArgs
import com.rishi.zmovie.presentation.movielist.MovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase
) : ViewModel() {

    private var movieId: String = savedStateHandle[ZMovieAppArgs.ARG_MOVIE_ID]!!

    var uiState by mutableStateOf(MovieDetailScreenUiState())
        private set

    var uiSideEffect by mutableStateOf<MovieDetailScreenSideEffects>(MovieDetailScreenSideEffects.NoEffect)
        private set

    init {
        setMovieData()
    }


    private fun setMovieData() {
        val data = getTrendingMoviesUseCase.getMovieById(movieId)
        uiState = if (data != null) {
            uiState.copy(screenState = ScreenState.DEFAULT, data = data)
        } else {
            uiState.copy(screenState = ScreenState.EMPTY)
        }
    }

    fun onEvent(event: MovieDetailScreenUIEvent) {
        when (event) {
            is MovieDetailScreenUIEvent.OnBack -> {
                uiSideEffect = MovieDetailScreenSideEffects.Back
            }
        }
    }

    fun resetUiSideEffect() {
        uiSideEffect = MovieDetailScreenSideEffects.NoEffect
    }

}

data class MovieDetailScreenUiState(
    val screenState: ScreenState = ScreenState.LOADING,
    val data: MovieItem? = null
)

enum class ScreenState {
    LOADING,
    EMPTY,
    DEFAULT
}

sealed interface MovieDetailScreenUIEvent {
    data object OnBack : MovieDetailScreenUIEvent
}

sealed interface MovieDetailScreenSideEffects {
    data object NoEffect : MovieDetailScreenSideEffects
    data object Back : MovieDetailScreenSideEffects
}
