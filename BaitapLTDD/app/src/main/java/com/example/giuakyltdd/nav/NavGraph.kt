package com.example.giuakyltdd

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.app02.nav.Screen
import com.example.giuakyltdd.ui.Screens.HomeScreen
import com.example.giuakyltdd.ui.Screens.ProductDetailScreen
import com.example.giuakyltdd.ui.Screens.ProductListScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.ProductList.route) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.ProductList.route) { ProductListScreen(navController) }
        composable(Screen.ProductDetail.route) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailScreen(navController, productId)
        }

//        composable(Screen.MovieDetail.route) { backStackEntry ->
//            val movieId = backStackEntry.arguments?.getString("id")?.toLongOrNull()
//            if (movieId != null) {
//                MovieDetailScreen(movieId, navController)
//            }
//        }
    }
}
