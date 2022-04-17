package com.example.movieapp.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.movieapp.models.Movie

class MovieViewModel : ViewModel() {
    private var movies = mutableStateListOf<Movie>()

    fun addMovie(movie: Movie) {
        movies.add(movie)
    }

    fun removeMovie(movie: Movie) {
        movies.remove(movie)
    }

    fun getAllMovies(): List<Movie> {
        return movies
    }

    fun checkFavorite(movie: Movie): Boolean {
        return movies.contains(movie)
    }
}