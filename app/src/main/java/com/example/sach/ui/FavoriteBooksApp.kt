package com.example.sach.ui

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.sach.ui.screens.GetStartScreen
import com.example.sach.ui.screens.BookDetails
import com.example.sach.ui.screens.ExploreScreen
import com.example.sach.ui.screens.HomeScreen
import com.example.sach.ui.screens.SavedScreen
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

enum class Screen {
    SPLASH,
    HOME,
    SAVED,
    EXPLORE,
    DETAIL
}

sealed class NavigationItem(val route: String) {
    object Splash : NavigationItem(Screen.SPLASH.name.lowercase())
    object Home : NavigationItem(Screen.HOME.name.lowercase())
    object Saved : NavigationItem(Screen.SAVED.name.lowercase())
    object Explore : NavigationItem(Screen.EXPLORE.name.lowercase())
    object Detail : NavigationItem("${Screen.DETAIL.name.lowercase()}/{bookId}") {
        fun createRoute(bookId: Int) = "${Screen.DETAIL.name.lowercase()}/$bookId"
    }
}

@Composable
fun FavoriteBooksApp(bookViewModel: BookViewModel = viewModel()) {
    val navController = rememberNavController()
    var isFirstLaunch by rememberSaveable { mutableStateOf(true) }

    AppNavHost(
        navController = navController,
        isFirstLaunch = isFirstLaunch,
        onSplashFinished = { isFirstLaunch = false },
        bookViewModel = bookViewModel
    )
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    isFirstLaunch: Boolean,
    onSplashFinished: () -> Unit,
    bookViewModel: BookViewModel = viewModel()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if (isFirstLaunch) NavigationItem.Splash.route else NavigationItem.Home.route
    ) {
        composable(NavigationItem.Splash.route) {
            GetStartScreen(navController, onSplashFinished)
        }
        composable(NavigationItem.Home.route) {
            HomeScreen(navController, bookViewModel)
        }
        composable(NavigationItem.Saved.route) {
            SavedScreen(navController, bookViewModel)
        }
        composable(NavigationItem.Explore.route) {
            ExploreScreen(navController, bookViewModel)
        }
        composable(
            route = NavigationItem.Detail.route,
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId")
            BookDetails(navController, bookId = bookId, viewModel = bookViewModel)
        }
    }
}
