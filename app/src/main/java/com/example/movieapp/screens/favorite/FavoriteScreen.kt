package com.example.movieapp.screens.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.viewmodels.MovieViewModel
import com.example.movieapp.widgets.MovieRow

@Composable
fun FavoriteScreen(
    navController: NavController = rememberNavController(),
    viewModel: MovieViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar {
                Row {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow Back",
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        }
                    )

                    Spacer(modifier = Modifier.width(20.dp))

                    Text(text = "My Favorite Movies")
                }
            }
        }
    )
    {
        FavoriteScreenContent(viewModel = viewModel)
    }
}

@Composable
fun FavoriteScreenContent(viewModel: MovieViewModel = viewModel()) {
    Column {
        for (i in viewModel.getAllMovies()) {
            MovieRow(movie = i, viewFavIcon = false)
        }
    }
}