package com.example.sach.screens

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.sach.R
import com.example.sach.model.Book
import com.example.sach.navigation.NavigationItem
import com.example.sach.ui.BookViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import kotlin.system.exitProcess




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: BookViewModel = viewModel()
) {
    val layoutDirection = LocalLayoutDirection.current
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var backPressedTime by remember { mutableLongStateOf(0L) }

    BackHandler {
        val currentTime = System.currentTimeMillis()
        if (currentTime - backPressedTime < 2000) {
            // Nhấn lần 2 trong vòng 2 giây -> thoát ứng dụng
            exitProcess(0)
        } else {
            // Nhấn lần 1 -> cảnh báo
            backPressedTime = currentTime
            Toast.makeText(context, "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT).show()
        }
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
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ten_logo_removebg_preview),
                            contentDescription = null,
                            modifier = Modifier.size(170.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
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
            Text(
                text = "Recommended for you",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Handpicked based on your reading preferences.",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(viewModel.books.take(5)) { book ->
                    AnimatedVisibility(visible = true) {
                        BookCard(book = book, viewModel = viewModel, navController)
                    }
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
                    AnimatedVisibility(visible = true) {
                        BookCard(book = book, viewModel = viewModel, navController)
                    }
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
                    AnimatedVisibility(visible = true) {
                        BookCard(book = book, viewModel = viewModel, navController)
                    }
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
                    AnimatedVisibility(visible = true) {
                        BookCard(book = book, viewModel = viewModel, navController)
                    }
                }
            }
        }
    }
}


@Composable
fun BookCard(book: Book, viewModel: BookViewModel, navController: NavHostController) {
    val favoriteIds by viewModel.favoriteBookIds.collectAsState()
    val isFavorite = favoriteIds.contains(book.id.toString())

    Card(
        modifier = Modifier
            .width(160.dp)
            .height(280.dp)
            .clickable {
                navController.navigate(NavigationItem.DETAIL.createRoute(book.id))
            },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
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
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = book.tacgia,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(
                onClick = { viewModel.toggleFavorite(book.id) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
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



@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItemWithImage(
            label = "Home",
            iconRes = R.drawable.home_alt_2,
            selected = currentRoute == NavigationItem.HOME.route
        ) {
            if (currentRoute != NavigationItem.HOME.route) {
                navController.navigate(NavigationItem.HOME.route) {
                    popUpTo(NavigationItem.HOME.route) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }

        BottomNavItemWithImage(
            label = "Search",
            iconRes = R.drawable.search,
            selected = currentRoute == NavigationItem.EXPLORE.route
        ) {
            if (currentRoute != NavigationItem.EXPLORE.route) {
                navController.navigate(NavigationItem.EXPLORE.route) {
                    popUpTo(NavigationItem.EXPLORE.route) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }

        BottomNavItemWithImage(
            label = "Favorites",
            iconRes = R.drawable.bookmark_plus_alt,
            selected = currentRoute == NavigationItem.SAVED.route
        ) {
            if (currentRoute != NavigationItem.SAVED.route) {
                navController.navigate(NavigationItem.SAVED.route) {
                    popUpTo(NavigationItem.SAVED.route) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }
}


@Composable
fun BottomNavItemWithImage(
    label: String,
    @DrawableRes iconRes: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    val color = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
            .padding(vertical = 4.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onClick),
            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(color)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}





//@Preview
//@Composable
//fun PreviewHomeScreen() {
//    val bookViewModel: BookViewModel = viewModel()
//    HomeScreen()
//}