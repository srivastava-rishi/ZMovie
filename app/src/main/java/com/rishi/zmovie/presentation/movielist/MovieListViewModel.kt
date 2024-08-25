package com.rishi.zmovie.presentation.movielist

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rishi.zmovie.domain.GetTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            getTrendingMoviesUseCase.invoke("en-US").catch {
                Log.d("test67trjf", "catch  $it" )
            }.collect {
                Log.d("test67trjf", "result  ${it}" )
                Log.d("test67trjf", "result  ${it.body()}" )
            }
        }
    }

}

data class MovieListScreenUiState(
    val isLoading: Boolean = false
)

sealed interface MovieListScreenUIEvent {
    data class OpenMovieDetailsScreen(val id: String) : MovieListScreenUIEvent
}

sealed interface MovieListScreenSideEffects {
    data object NoEffect : MovieListScreenSideEffects
    data class OpenExampleDetailScreen(val id: String) : MovieListScreenSideEffects
}