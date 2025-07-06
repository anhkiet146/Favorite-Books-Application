package com.example.sach

enum class Sceen{
    GET_START,
    HOME,
    SAVED
}

sealed class NavigationItem(val route: String){
    object GET_START: NavigationItem(Sceen.GET_START.name)
    object HOME: NavigationItem(Sceen.HOME.name)
    object SAVED: NavigationItem(Sceen.SAVED.name)
    object DETAIL : NavigationItem("detail/{bookId}") {
        fun createRoute(bookId: Int) = "detail/$bookId"
    }
}
