package com.example.movieappmad24.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, movieId: String?) {
    val movie = getMovies().find { it.id == movieId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(movie?.title ?: "") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            movie?.let { MovieRow(movie = it) }

            Spacer(modifier = Modifier.height(10.dp))

            LazyRow {
                movie?.images?.let { images ->
                    items(images) { imageUrl ->
                        Card(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(200.dp)
                        ) {
                            MovieImage(imageUrl = imageUrl)
                        }
                    }
                }
            }
        }
    }
}