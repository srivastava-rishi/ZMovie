package com.rishi.zmovie.presentation.movielist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rishi.zmovie.domain.GetTrendingMoviesUseCase
import com.rishi.zmovie.util.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase
) : ViewModel() {

    var uiState by mutableStateOf(MovieListScreenUiState())
        private set

    var uiSideEffect by mutableStateOf<MovieListScreenSideEffects>(MovieListScreenSideEffects.NoEffect)
        private set


    init {
        getTrendingMovies()
    }

    private fun getTrendingMovies() {
        viewModelScope.launch {
            getTrendingMoviesUseCase.invoke(Constant.LANGUAGE).catch {
                uiState = uiState.copy(screenState = ScreenState.ERROR)
            }.collect {
                uiState = if (it.isNullOrEmpty()) {
                    uiState.copy(screenState = ScreenState.EMPTY)
                } else {
                    uiState.copy(movieList = it, filteredList = it, screenState = ScreenState.DEFAULT)
                }
            }
        }
    }

    fun performSearch(searchText: String) {
        uiState = uiState.copy(
            searchFieldValue = uiState.searchFieldValue.copy(text = searchText),
            filteredList = uiState.movieList.filter {
                searchText.isEmpty() || it.title?.contains(searchText, ignoreCase = true) == true
            }
        )
    }


    fun onEvent(event: MovieListScreenUIEvent) {
        when (event) {
            is MovieListScreenUIEvent.OpenMovieDetailsScreen -> {
                uiSideEffect = MovieListScreenSideEffects.OpenMovieDetailScreen(event.id)
            }

            is MovieListScreenUIEvent.SearchTextChanged -> {
                performSearch(event.text)
            }
        }
    }

    fun resetUiSideEffect() {
        uiSideEffect = MovieListScreenSideEffects.NoEffect
    }
}

data class MovieListScreenUiState(
    val screenState: ScreenState = ScreenState.LOADING,
    val movieList: List<MovieItem> = emptyList(),
    val filteredList: List<MovieItem> = emptyList(),
    val searchFieldValue: TextFieldValue = TextFieldValue()
)

sealed interface MovieListScreenUIEvent {
    data class OpenMovieDetailsScreen(val id: String) : MovieListScreenUIEvent
    data class SearchTextChanged(val text: String): MovieListScreenUIEvent
}

sealed interface MovieListScreenSideEffects {
    data object NoEffect : MovieListScreenSideEffects
    data class OpenMovieDetailScreen(val id: String) : MovieListScreenSideEffects
}


enum class ScreenState {
    LOADING,
    EMPTY,
    ERROR,
    DEFAULT
}

data class MovieItem(
    val id: String,
    val title: String? = null,
    val description: String? = null,
    val moviePoster: String? = null
)