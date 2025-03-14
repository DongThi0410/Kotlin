package com.example.app02.nav

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Edit : Screen("edit/{id}")

}
