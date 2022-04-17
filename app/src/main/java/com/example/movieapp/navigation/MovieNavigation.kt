package com.example.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.screens.home.HomeScreen
import com.example.movieapp.screens.details.DetailScreen
import com.example.movieapp.screens.favorite.FavoriteScreen
import com.example.movieapp.viewmodels.MovieViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val movieViewModel: MovieViewModel = viewModel()

    NavHost(navController = navController, startDestination = MovieScreens.HomeScreen.name) {

        composable(MovieScreens.HomeScreen.name) {
            HomeScreen(
                navController = navController,
                viewModel = movieViewModel
            )
        }
        composable(
            route = MovieScreens.DetailScreen.name + "/{movieId}",
            arguments = listOf(navArgument("movieId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            DetailScreen(
                viewModel = movieViewModel,
                navController = navController,
                movieId = backStackEntry.arguments?.getString("movieId")
            )
        }
        composable(
            route = MovieScreens.FavoriteScreen.name
        ) { FavoriteScreen(navController = navController, viewModel = movieViewModel) }
    }
}