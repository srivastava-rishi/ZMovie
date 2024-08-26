package com.rishi.zmovie.presentation.movielist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rishi.zmovie.R
import com.rishi.zmovie.navigation.actions.MovieListScreenActions
import com.rishi.zmovie.presentation.common.CustomTextField
import com.rishi.zmovie.presentation.common.ErrorMessage
import com.rishi.zmovie.presentation.common.ProgressLoader
import com.rishi.zmovie.ui.theme.Grey
import com.rishi.zmovie.ui.theme.Grey200
import com.rishi.zmovie.ui.theme.Grey400
import com.rishi.zmovie.ui.theme.paragraph
import com.rishi.zmovie.ui.theme.paragraphDefaultRegular
import com.rishi.zmovie.util.noRippleClickable

@Composable
fun MovieListScreen(
    viewModel: MovieListViewModel = hiltViewModel(),
    onAction: (movieListScreenActions: MovieListScreenActions) -> Unit
) {
    val onEvent = remember(key1 = viewModel) {
        return@remember viewModel::onEvent
    }

    MovieListContent(viewModel.uiState, onEvent)

    LaunchedEffect(key1 = viewModel.uiSideEffect) {
        handelSideEffects(
            sideEffects = viewModel.uiSideEffect,
            onAction = onAction
        )
        viewModel.resetUiSideEffect()
    }
}

@Composable
fun MovieListContent(
    uiState: MovieListScreenUiState,
    onEvent: (MovieListScreenUIEvent) -> Unit
) {

    when (uiState.screenState) {
        ScreenState.LOADING -> {
            ProgressLoader()
        }

        ScreenState.EMPTY -> {
            ErrorMessage(errorMessage = stringResource(id = R.string.no_data_available))
        }

        ScreenState.ERROR -> {
            ErrorMessage(
                errorMessage = stringResource(id = R.string.something_went_wrong),
                error = true
            )
        }

        ScreenState.DEFAULT -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Grey)
                    .padding(start = 16.dp, end = 16.dp , bottom = 24.dp)
            ) {
                Spacer(modifier = Modifier.size(24.dp))
                CustomTextField(
                    textFieldState = uiState.searchFieldValue,
                    allowSpecialCharacters = false,
                    placeHolder = {
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = stringResource(id = R.string.search_movies),
                            style = MaterialTheme.typography.paragraph,
                            color = Grey400
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search_16px),
                            tint = Grey400,
                            contentDescription = ""
                        )
                    },
                    onValueChange = {
                        onEvent(MovieListScreenUIEvent.SearchTextChanged(it))
                    }
                )
                Spacer(modifier = Modifier.size(24.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.filteredList) {
                        MovieItem(
                            data = it,
                            onClick = {
                                onEvent(MovieListScreenUIEvent.OpenMovieDetailsScreen(it))
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun MovieItem(
    data: MovieItem,
    onClick: (id: String) -> Unit
) {
    Column(
        modifier = Modifier.noRippleClickable {
            onClick(data.id)
        }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data = data.moviePoster)
                .error(R.drawable.pic)
                .placeholder(R.drawable.pic)
                .crossfade(true)
                .build(),
            contentDescription = "movieIcon",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(172.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black)
        )

        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = data.title.orEmpty(),
            style = MaterialTheme.typography.paragraphDefaultRegular
        )
    }
}

@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    searchFieldValue: TextFieldValue
) {
    Row(
        modifier = modifier
            .padding(top = 24.dp)
            .fillMaxWidth()
            .height(56.dp)
            .border(
                width = 1.dp,
                color = Grey200,
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
    }
}

private fun handelSideEffects(
    sideEffects: MovieListScreenSideEffects,
    onAction: (actions: MovieListScreenActions) -> Unit,
) {
    when (sideEffects) {
        is MovieListScreenSideEffects.OpenMovieDetailScreen -> {
            onAction(MovieListScreenActions.OpenMovieDetailListScreen(sideEffects.id))
        }

        else -> {

        }
    }
}


@Preview(showBackground = true)
@Composable
fun MovieListPreview() {
    val uiState = MovieListScreenUiState(
        screenState = ScreenState.DEFAULT,
        filteredList = listOf(
            MovieItem(
                id = "1",
                title = "Doctor Strange",
                description = "Doctor Strange is a 2016 American superhero film based on the Marvel Comics character of the same name. Produced by Marvel Studios and distributed by Walt Disney Studios Motion Pictures, it is the 14th film in the Marvel Cinematic Universe (MCU). The film was directed by Scott Derrickson from a screenplay he wrote with Jon Spaihts and C. Robert Cargill, and stars Benedict Cumberbatch as neurosurgeon Stephen Strange along with Chiwetel Ejiofor, Rachel McAdams, Benedict Wong, Michael Stuhlbarg, Benjamin Bratt, Scott Adkins, Mads Mikkelsen, and Tilda Swinton. In the film, Strange learns the mystic arts after a career-ending car crash.",
                moviePoster = null
            ),
            MovieItem(
                id = "2",
                title = "Doctor Strange",
                description = "Doctor Strange is a 2016 American superhero film based on the Marvel Comics character of the same name. Produced by Marvel Studios and distributed by Walt Disney Studios Motion Pictures, it is the 14th film in the Marvel Cinematic Universe (MCU). The film was directed by Scott Derrickson from a screenplay he wrote with Jon Spaihts and C. Robert Cargill, and stars Benedict Cumberbatch as neurosurgeon Stephen Strange along with Chiwetel Ejiofor, Rachel McAdams, Benedict Wong, Michael Stuhlbarg, Benjamin Bratt, Scott Adkins, Mads Mikkelsen, and Tilda Swinton. In the film, Strange learns the mystic arts after a career-ending car crash.",
                moviePoster = null
            ),
            MovieItem(
                id = "3",
                title = "Doctor Strange",
                description = "Doctor Strange is a 2016 American superhero film based on the Marvel Comics character of the same name. Produced by Marvel Studios and distributed by Walt Disney Studios Motion Pictures, it is the 14th film in the Marvel Cinematic Universe (MCU). The film was directed by Scott Derrickson from a screenplay he wrote with Jon Spaihts and C. Robert Cargill, and stars Benedict Cumberbatch as neurosurgeon Stephen Strange along with Chiwetel Ejiofor, Rachel McAdams, Benedict Wong, Michael Stuhlbarg, Benjamin Bratt, Scott Adkins, Mads Mikkelsen, and Tilda Swinton. In the film, Strange learns the mystic arts after a career-ending car crash.",
                moviePoster = null
            ),
            MovieItem(
                id = "4",
                title = "Doctor Strange",
                description = "Doctor Strange is a 2016 American superhero film based on the Marvel Comics character of the same name. Produced by Marvel Studios and distributed by Walt Disney Studios Motion Pictures, it is the 14th film in the Marvel Cinematic Universe (MCU). The film was directed by Scott Derrickson from a screenplay he wrote with Jon Spaihts and C. Robert Cargill, and stars Benedict Cumberbatch as neurosurgeon Stephen Strange along with Chiwetel Ejiofor, Rachel McAdams, Benedict Wong, Michael Stuhlbarg, Benjamin Bratt, Scott Adkins, Mads Mikkelsen, and Tilda Swinton. In the film, Strange learns the mystic arts after a career-ending car crash.",
                moviePoster = null
            )
        )
    )

    MovieListContent(uiState, {})
}