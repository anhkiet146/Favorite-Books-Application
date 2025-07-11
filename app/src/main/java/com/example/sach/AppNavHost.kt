package com.example.sach

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.HOME.route,
    bookViewModel: BookViewModel = viewModel()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.HOME.route) {
            GetStartScreen(navController)
        }
        composable(NavigationItem.GET_START.route) {
            HomeScreen(navController, viewModel = bookViewModel)
        }
        composable(NavigationItem.SAVED.route) {
            SavedScreen(navController, viewModel = bookViewModel)
        }
        composable(
            route = NavigationItem.DETAIL.route,
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId")
            BookDetails(navController, bookId = bookId, viewModel = bookViewModel)
        }
        composable(NavigationItem.EXPLORE.route) {
            ExploreScreen(navController, viewModel = bookViewModel)
        }
    }
}
