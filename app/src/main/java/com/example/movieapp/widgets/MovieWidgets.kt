package com.example.movieapp.widgets

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieapp.models.Movie

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MovieRow(
    movie: Movie,
    onItemClick: (String) -> Unit = {},
    onFavoriteClick: (Movie) -> Unit = {},
    viewFavIcon: Boolean = true,
    Favorite: Boolean = false
) {

    var showInfo by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable {
                onItemClick(movie.id)
            }
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 6.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp)
            ) {
                AsyncImage(
                    model = movie.images[0],
                    contentDescription = movie.images[0],
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape)
                )

            }

            Column {
                Text(text = movie.title, style = MaterialTheme.typography.h5)
                Text(text = "Director: ${movie.director}")
                Text(text = "Released: ${movie.year}")

                AnimatedVisibility(visible = showInfo, enter = fadeIn(), exit = fadeOut()) {
                    Column {
                        Text(text = "Plot: ${movie.plot}")
                        Text(text = "Actors: ${movie.actors}")
                        Text(text = "Genre: ${movie.genre}")
                        Text(text = "Rating: ${movie.rating}")
                    }
                }

                if (!showInfo) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Arrow Down",
                        modifier = Modifier.clickable(onClick = { showInfo = !showInfo })
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Arrow Up",
                        modifier = Modifier.clickable(onClick = { showInfo = !showInfo })
                    )
                }
            }
            if (viewFavIcon) {
                FavoriteIcon(movie, onFavoriteClick = { movie ->
                    onFavoriteClick(movie)
                }, Favorite = Favorite)
            }
        }
    }
}

@Composable
fun FavoriteIcon(
    movie: Movie,
    onFavoriteClick: (Movie) -> Unit = {},
    Favorite: Boolean

) {
    var isFavorite by remember { mutableStateOf(Favorite) }
    IconButton(onClick = {
        isFavorite = !isFavorite
        onFavoriteClick(movie)

    }) {

        if (!isFavorite) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Favorite Icon Border",
            )
        } else {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favorite Icon",
            )
        }
    }
}