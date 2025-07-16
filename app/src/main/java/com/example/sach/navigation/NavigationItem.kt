package com.example.sach.navigation

sealed class NavigationItem(val route: String) {
    object SPLASH : NavigationItem("splash") // dùng đúng tên route trong startDestination
    object HOME : NavigationItem("home")
    object SAVED : NavigationItem("saved")
    object EXPLORE : NavigationItem("explore")
    object DETAIL : NavigationItem("detail/{bookId}") {
        fun createRoute(bookId: Int) = "detail/$bookId"
    }
}


