package com.example.sach.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.sach.GetStartScreen
import com.example.sach.screens.BookDetails
import com.example.sach.screens.ExploreScreen
import com.example.sach.screens.HomeScreen
import com.example.sach.screens.SavedScreen
import com.example.sach.ui.BookViewModel

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
        startDestination = if (isFirstLaunch) NavigationItem.SPLASH.route else NavigationItem.HOME.route
    ) {
        composable(NavigationItem.SPLASH.route) {
            GetStartScreen(navController, onSplashFinished)
        }
        composable(NavigationItem.HOME.route) {
            HomeScreen(navController, bookViewModel)
        }
        composable(NavigationItem.SAVED.route) {
            SavedScreen(navController, bookViewModel)
        }
        composable(NavigationItem.EXPLORE.route) {
            ExploreScreen(navController, bookViewModel)
        }
        composable(
            route = NavigationItem.DETAIL.route,
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId")
            BookDetails(navController, bookId = bookId, viewModel = bookViewModel)
        }
    }
}
