package com.example.giuaky.nav

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.giuaky.ui.Screens.AddNoteScreen
import com.example.giuaky.ui.Screens.AuthScreen
import com.example.giuaky.ui.Screens.DetailNoteScreen
import com.example.giuaky.ui.Screens.HomeScreen
import com.example.giuaky.ui.viewModel.AuthViewModel
import com.example.giuaky.ui.viewModel.NoteViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun AppNavGraph(
    navController: NavHostController, noteViewModel: NoteViewModel, authViewmodel: AuthViewModel
) {
    val startDestination =
        if (authViewmodel.isUserLoggedIn()) Screen.Home.route else Screen.Auth.route
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Auth.route) { AuthScreen(navController, authViewmodel) }
        composable(Screen.Home.route) { HomeScreen(navController, noteViewModel) }
        composable(Screen.Add.route) { AddNoteScreen(navController, noteViewModel) }
        composable(
            route = "edit/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: -1
            DetailNoteScreen(id, navController, noteViewModel)
        }
    }
}
