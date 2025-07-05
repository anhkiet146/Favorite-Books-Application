<<<<<<< HEAD
package com.example.sach

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val bookViewModel: BookViewModel = viewModel()
    AppNavHost(navController = navController, bookViewModel = bookViewModel)
=======
package com.example.sach

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val bookViewModel: BookViewModel = viewModel()
    AppNavHost(navController = navController, bookViewModel = bookViewModel)
>>>>>>> thien
}