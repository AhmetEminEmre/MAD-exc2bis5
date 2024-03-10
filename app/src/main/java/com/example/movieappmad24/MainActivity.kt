package com.example.movieappmad24
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import com.example.movieappmad24.ui.theme.LightColorScheme
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Scaffold
import androidx.compose.ui.text.style.TextAlign
import coil.compose.rememberAsyncImagePainter
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.Color



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppMAD24Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = LightColorScheme.primary
                ) {
                    TopAppNavigationBar()
                }
            }
        }
    }
}

//TopBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppNavigationBar() {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LightColorScheme.primaryContainer,
                    titleContentColor = LightColorScheme.primary,
                ),
                title = {
                    Text("Movie App", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                },
            )
        },
        bottomBar = {
            BottomAppNavigationBar()
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            MovieList(movies = getMovies())
        }
    }
}

@Composable
fun BottomAppNavigationBar() {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = false,
            onClick = { }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Star, contentDescription = "Watchlist") },
            label = { Text("Watchlist") },
            selected = false,
            onClick = { }
        )
    }
}

@Composable
fun MovieList(movies: List<Movie> = getMovies()){
    LazyColumn {
        items(movies) { movie ->
            FilmItem(movie)
        }
    }
}



@Composable
fun MoviesLayout(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(getMovies()) { movie ->
            FilmItem(movie = movie)
        }
    }
}

@Composable
fun FilmItem(movie: Movie) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize(),
        shape = RoundedCornerShape(8.dp)
    ) {
        FilmContent(movie, expanded) { expanded = !expanded }
    }
}

@Composable
fun FilmContent(movie: Movie, expanded: Boolean, onExpand: () -> Unit) {
    Column {
        MovieBanner(movie)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(4.dp)
            )
            ExpandIcon(expanded, onExpand)
        }
        MovieDetailsSection(movie, expanded)
    }
}

@Composable
fun MovieBanner(movie: Movie) {
    val imagePainter = rememberAsyncImagePainter(model = movie.images.firstOrNull())
    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = imagePainter,
            contentDescription = "Film Thumbnail",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        FavoriteIndicator()
    }
}

@Composable
fun ExpandIcon(expanded: Boolean, onClick: () -> Unit) {
    Icon(
        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
        contentDescription = if (expanded) "Show less" else "Show more",
        modifier = Modifier.clickable { onClick() }
    )
}

@Composable
fun MovieDetailsSection(movie: Movie, expanded: Boolean) {
    AnimatedVisibility(visible = expanded) {
        Column(modifier = Modifier.padding(start = 12.dp, end = 24.dp, top = 8.dp, bottom = 8.dp)) {
            Text("Director: ${movie.director}", style = MaterialTheme.typography.bodySmall)
            Text("Released: ${movie.year}", style = MaterialTheme.typography.bodySmall)
            Text("Genre: ${movie.genre}", style = MaterialTheme.typography.bodySmall)
            Text("Actors: ${movie.actors}", style = MaterialTheme.typography.bodySmall)
            Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 4.dp))
            Text("Plot: ${movie.plot}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun FavoriteIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Icon(
            Icons.Filled.FavoriteBorder,
            contentDescription = "Add to Favorites",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

//@Preview
//@Composable
//fun DefaultPreview(){
//    MovieAppMAD24Theme {
//        MovieDisplay(movies = getMovies())
//    }
//}