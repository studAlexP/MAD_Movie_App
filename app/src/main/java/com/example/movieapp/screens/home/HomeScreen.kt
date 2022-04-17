package com.example.movieapp.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.models.Movie
import com.example.movieapp.models.getMovies
import com.example.movieapp.navigation.MovieScreens
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.viewmodels.MovieViewModel
import com.example.movieapp.widgets.MovieRow


@Composable
fun HomeScreenContent(
    navController: NavController,
    movieList: List<Movie> = getMovies(),
    viewModel: MovieViewModel,
    onFavoriteClick: (Movie) -> Unit = {}
) {
    LazyColumn {
        items(movieList) { movies ->
            MovieRow(movie = movies, onFavoriteClick = { movie ->
                onFavoriteClick(movie)
            }, onItemClick = { movieId ->
                navController.navigate(MovieScreens.DetailScreen.name + "/$movieId")
            }, Favorite = viewModel.checkFavorite(movies))
        }
    }
}

@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    viewModel: MovieViewModel
) {

    var showMenu by remember {
        mutableStateOf(false)
    }

    MovieAppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Movies") },
                    actions = {
                        IconButton(onClick = { showMenu = !showMenu }) {
                            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
                        }

                        DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                            DropdownMenuItem(onClick = { navController.navigate(MovieScreens.FavoriteScreen.name) }) {
                                Row {
                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = "Favorite",
                                        modifier = Modifier
                                            .padding(4.dp)

                                    )
                                    Text(
                                        text = "Favorites",
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .width(100.dp)
                                    )
                                }
                            }
                        }
                    }
                )
            }
        ) {
            HomeScreenContent(
                navController = navController,
                viewModel = viewModel,
                onFavoriteClick = { movie ->
                    if (!viewModel.checkFavorite(movie)) {
                        viewModel.addMovie(movie)
                    } else {
                        viewModel.removeMovie(movie)
                    }
                })
        }
    }
}