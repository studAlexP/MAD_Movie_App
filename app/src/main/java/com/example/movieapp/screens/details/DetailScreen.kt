package com.example.movieapp.screens.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.movieapp.models.Movie
import com.example.movieapp.models.getMovies
import com.example.movieapp.viewmodels.MovieViewModel
import com.example.movieapp.widgets.MovieRow

@Composable
fun DetailScreen(
    viewModel: MovieViewModel,
    navController: NavController = rememberNavController(),
    movieId: String? = "Null"
) {
    val movie = filterMovie(movieId = movieId)

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

                    Text(text = movie.title)
                }
            }
        }
    ) {
        DetailScreenContent(movie, viewModel = viewModel) { movie ->
            if (!viewModel.checkFavorite(movie)) {
                viewModel.addMovie(movie)
            } else {
                viewModel.removeMovie(movie)
            }
        }
    }
}

@Composable
fun DetailScreenContent(
    movie: Movie,
    viewModel: MovieViewModel,
    onFavoriteClick: (Movie) -> Unit = {}
) {

    Column {
        MovieRow(movie, onFavoriteClick = { movie ->
            onFavoriteClick(movie)
        }, Favorite = viewModel.checkFavorite(movie))

        Text(
            text = "Movie Images",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth()
        )

        LazyRow {
            items(movie.images) { images ->
                AsyncImage(
                    model = images,
                    contentDescription = movie.images[0],
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(200.dp)
                        .width(200.dp)
                        .padding(10.dp)
                )
            }

        }
    }
}

fun filterMovie(movieId: String?): Movie {
    return getMovies().filter { movie -> movie.id == movieId }[0]
}