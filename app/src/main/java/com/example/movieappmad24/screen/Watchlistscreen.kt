package com.example.movieappmad24.screen

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.widgets.SimpleTopAppBar
import com.example.movieappmad24.widgets.SimpleBottomAppBar

@Composable
fun WatchlistScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = "Your Watchlist",
                navigationIcon = null,
                onNavigationIconClick = null
            )
        },
        bottomBar = {
            SimpleBottomAppBar(navController = navController)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LazyColumn {
                val movies = getMovies()
                items(movies) { movie ->
                    MovieRow(movie = movie) { movieId ->
                        navController.navigate(Screen.DetailScreen.createRoute(movieId))
                    }
                }
            }
        }
    }
}