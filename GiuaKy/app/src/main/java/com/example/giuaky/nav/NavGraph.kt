package com.example.giuaky.nav

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.giuaky.data.Note
import com.example.giuaky.ui.Screens.AddNoteScreen
import com.example.giuaky.ui.Screens.AuthScreen
import com.example.giuaky.ui.Screens.DetailNoteScreen
import com.example.giuaky.ui.Screens.HomeScreen
import com.example.giuaky.ui.viewModel.AuthViewModel


@Composable
fun AppNavGraph(
    context: Context, navController: NavHostController,
) {
    val authViewmodel: AuthViewModel = viewModel()
    val startDestination =
        if (authViewmodel.isUserLoggedIn()) Screen.Home.route else Screen.Auth.route
    val notes = remember { mutableStateListOf<Note?>() }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Auth.route) { AuthScreen(navController, authViewmodel) }
        composable(Screen.Home.route) { HomeScreen(notes, navController, context,) }
        composable(Screen.Add.route) { AddNoteScreen(context, navController) }
        composable(
            route = Screen.Edit.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("id")?.let { noteId ->
                DetailNoteScreen(noteId, navController)
            }
        }
    }
}
