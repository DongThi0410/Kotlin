package com.example.giuaky.nav

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Add : Screen("add")
    object Auth : Screen("auth")
    object Edit : Screen("edit/{id}")

}
