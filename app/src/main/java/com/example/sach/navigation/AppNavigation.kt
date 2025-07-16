package com.example.sach.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sach.ui.BookViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val bookViewModel: BookViewModel = viewModel()

    // Splash chỉ hiển thị 1 lần
    var isFirstLaunch by rememberSaveable { mutableStateOf(true) }

    AppNavHost(
        navController = navController,
        isFirstLaunch = isFirstLaunch,
        onSplashFinished = { isFirstLaunch = false },
        bookViewModel = bookViewModel
    )
}

