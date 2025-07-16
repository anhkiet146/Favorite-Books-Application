package com.example.sach.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sach.model.Book
import com.example.sach.navigation.NavigationItem
import com.example.sach.ui.BookViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SavedScreen(
    navController: NavHostController,
    viewModel: BookViewModel = viewModel()
) {
    val favoriteIds by viewModel.favoriteBookIds.collectAsState()
    val allBooks = viewModel.getAllBooks()
    var searchQuery by remember { mutableStateOf("") }
    val layoutDirection = LocalLayoutDirection.current
    val filteredBooks = allBooks.filter {
        it.id.toString() in favoriteIds &&
                (it.tensach.contains(searchQuery, ignoreCase = true) ||
                        it.tacgia.contains(searchQuery, ignoreCase = true))
    }
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
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                onBackClick = { navController.popBackStack() }
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
                .padding(12.dp)
        ) {
            Text(
                text = "My Collections",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (filteredBooks.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No favorite books yet.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredBooks) { book ->
                        BookCollectionItem(
                            book = book,
                            isFavorite = favoriteIds.contains(book.id.toString()),
                            navController,
                            onFavoriteClick = {
                                viewModel.toggleFavorite(book.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BookCollectionItem(
    book: Book,
    isFavorite: Boolean,
    navController: NavHostController,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clickable {
                navController.navigate(NavigationItem.DETAIL.createRoute(book.id))
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = book.hinhanh),
                contentDescription = null,
                modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = book.tensach,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = book.tacgia,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
                Text(
                    text = "Năm xuất bản: ${book.namxb}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier.align(Alignment.Top)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Toggle Favorite",
                    tint = if (isFavorite)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

