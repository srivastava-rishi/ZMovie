package com.rishi.zmovie.presentation.moviedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rishi.zmovie.R
import com.rishi.zmovie.navigation.actions.MovieDetailsScreenActions
import com.rishi.zmovie.presentation.common.ErrorMessage
import com.rishi.zmovie.presentation.common.ProgressLoader
import com.rishi.zmovie.presentation.movielist.MovieItem
import com.rishi.zmovie.ui.theme.Grey600
import com.rishi.zmovie.ui.theme.ht1
import com.rishi.zmovie.ui.theme.paragraph
import com.rishi.zmovie.ui.theme.paragraphDefaultRegular
import com.rishi.zmovie.util.noRippleClickable


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailViewModel = hiltViewModel(),
    onAction: (movieDetailsScreenActions: MovieDetailsScreenActions) -> Unit
) {
    val onEvent = remember(key1 = viewModel) {
        return@remember viewModel::onEvent
    }

    when (viewModel.uiState.screenState) {

        ScreenState.LOADING -> {
            ProgressLoader()
        }

        ScreenState.EMPTY -> {
            ErrorMessage(errorMessage = stringResource(id = R.string.no_data_found))
        }

        ScreenState.DEFAULT -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        modifier = Modifier,
                        title = {
                            Row {
                                Spacer(modifier = Modifier.size(12.dp))
                                Icon(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .noRippleClickable {
                                            onEvent(MovieDetailScreenUIEvent.OnBack)
                                        },
                                    painter = painterResource(id = R.drawable.back),
                                    tint = Grey600,
                                    contentDescription = "Back Icon"
                                )
                            }
                        }
                    )
                }
            ) { innerPadding ->
                MovieDetailsContent(Modifier.padding(innerPadding), viewModel.uiState)
            }
        }

    }
    LaunchedEffect(key1 = viewModel.uiSideEffect) {
        handelSideEffects(
            sideEffects = viewModel.uiSideEffect,
            onAction = onAction
        )
        viewModel.resetUiSideEffect()
    }
}

@Composable
fun MovieDetailsContent(
    modifier: Modifier,
    uiState: MovieDetailScreenUiState
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp, end = 16.dp, top = 12.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data = uiState.data?.moviePoster)
                .error(R.drawable.pic)
                .placeholder(R.drawable.pic)
                .crossfade(true)
                .build(),
            contentDescription = "moviePoster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black)
        )
        Spacer(modifier = Modifier.size(24.dp))
        Text(
            text = uiState.data?.title.orEmpty(),
            style = MaterialTheme.typography.ht1
        )
        Spacer(modifier = Modifier.size(24.dp))
        Text(
            text = uiState.data?.description.orEmpty(),
            style = MaterialTheme.typography.paragraph
        )
    }
}


private fun handelSideEffects(
    sideEffects: MovieDetailScreenSideEffects,
    onAction: (actions: MovieDetailsScreenActions) -> Unit,
) {
    when (sideEffects) {
        is MovieDetailScreenSideEffects.Back -> {
            onAction(MovieDetailsScreenActions.OnBack)
        }

        else -> {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailPreview() {
    val uiState = MovieDetailScreenUiState(
        screenState = ScreenState.DEFAULT,
        data = MovieItem(
            id = "1",
            title = "Doctor Strange",
            description = "Doctor Strange is a 2016 American superhero film based on the Marvel Comics character of the same name. Produced by Marvel Studios and distributed by Walt Disney Studios Motion Pictures, it is the 14th film in the Marvel Cinematic Universe (MCU). The film was directed by Scott Derrickson from a screenplay he wrote with Jon Spaihts and C. Robert Cargill, and stars Benedict Cumberbatch as neurosurgeon Stephen Strange along with Chiwetel Ejiofor, Rachel McAdams, Benedict Wong, Michael Stuhlbarg, Benjamin Bratt, Scott Adkins, Mads Mikkelsen, and Tilda Swinton. In the film, Strange learns the mystic arts after a career-ending car crash.",
            moviePoster = null
        )
    )
    MovieDetailsContent(
        modifier = Modifier,
        uiState = uiState
    )
}