package com.example.app02.nav

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ProductList : Screen("productList")
    object ProductDetail : Screen("detail/{productId}") {
        fun createRoute(productId: String) = "detail/$productId"
    }
}
