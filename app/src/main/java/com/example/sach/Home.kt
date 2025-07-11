package com.example.sach

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: BookViewModel = viewModel()
) {
    //navController: NavHostController,
    //val navController = rememberNavController()
    val layoutDirection = LocalLayoutDirection.current
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(
                start = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateStartPadding(layoutDirection),
                end = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateEndPadding(layoutDirection),
            ),
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(
                    text = "Read Ease",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Recommended Section ---
            Text("Recommended for you", style = MaterialTheme.typography.titleMedium)
            Text(
                "Handpicked based on your reading preferences.",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(viewModel.books.take(5)) { book ->
                    BookCard(book = book, viewModel = viewModel)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("New Releases", style = MaterialTheme.typography.titleMedium)
            Text(
                "Newly released books spanning various genres.",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(viewModel.books.drop(5).take(5)) { book ->
                    BookCard(book = book, viewModel = viewModel)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Trending Now", style = MaterialTheme.typography.titleMedium)
            Text("Books that are popular this week.", style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(viewModel.books.drop(10).take(5)) { book ->
                    BookCard(book = book, viewModel = viewModel)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text("You May Also Like", style = MaterialTheme.typography.titleMedium)
            Text("Similar to what you've been reading.", style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(viewModel.books.drop(15).take(4)) { book ->
                    BookCard(book = book, viewModel = viewModel)
                }
            }
        }
    }
}


@Composable
fun BookCard(book: Book, viewModel: BookViewModel) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(260.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = painterResource(id = book.hinhanh),
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(book.tensach, style = MaterialTheme.typography.titleSmall)
            Text(book.tacgia, style = MaterialTheme.typography.bodySmall, maxLines = 1)
            Spacer(modifier = Modifier.height(4.dp))
            IconButton(
                onClick = { viewModel.toggleFavorite(book.id) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    imageVector = if (book.favorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Toggle Favorite"
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItemWithImage("Home", R.drawable.home_alt_2) {
            navController.navigate(NavigationItem.GET_START.route)
        }
        BottomNavItemWithImage("Search", R.drawable.search) {
            navController.navigate(NavigationItem.EXPLORE.route)
        }
        BottomNavItemWithImage("Favorites", R.drawable.bookmark_plus_alt) {
            navController.navigate(NavigationItem.SAVED.route)
        }
    }
}

@Composable
fun BottomNavItemWithImage(label: String, @DrawableRes iconRes: Int, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(80.dp)) {
        IconButton(onClick = onClick, modifier = Modifier.size(48.dp)) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}


//@Preview
//@Composable
//fun PreviewHomeScreen() {
//    HomeScreen()
//}