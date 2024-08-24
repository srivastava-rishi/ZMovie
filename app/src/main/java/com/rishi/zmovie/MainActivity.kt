package com.rishi.zmovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.rishi.zmovie.navigation.ZMovieNavGraph
import com.rishi.zmovie.navigation.ZMovieNavActions
import com.rishi.zmovie.ui.theme.ZMovieTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZMovieTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MovieApp(onFinish = {
                        finish()
                    })
                }
            }
        }
    }
}

@Composable
fun MovieApp(
    onFinish: () -> Unit
) {
    val navController = rememberNavController()
    val navActions: ZMovieNavActions = remember(navController) {
        ZMovieNavActions(navController, onFinish)
    }
    ZMovieNavGraph(
        navController = navController,
        navActions = navActions
    )
}
