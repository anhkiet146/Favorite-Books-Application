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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController




@OptIn(ExperimentalMaterial3Api::class)
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
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.ten_logo),
                        contentDescription = null,
                        modifier = Modifier.size(120.dp),
                        contentScale = ContentScale.Fit
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
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

            Spacer(modifier = Modifier.height(16.dp))

            // --- Recommended Section ---
            Text(
                text = "Recommended for you",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
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

            Text(
                text = "New Releases",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
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

            Text(
                text = "Trending Now",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text("Books that are popular this week.", style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(viewModel.books.drop(10).take(5)) { book ->
                    BookCard(book = book, viewModel = viewModel)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "You May Also Like",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
                )
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
            .height(280.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = book.hinhanh),
                    contentDescription = null,
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = book.tensach,
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 18.sp
                )
                Text(
                    text = book.tacgia,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1
                )
            }

            // Icon nằm cố định góc dưới phải
            IconButton(
                onClick = { viewModel.toggleFavorite(book.id) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
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