package com.example.app02.nav

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.notesapp.ui.EditNoteScreen
import com.example.notesapp.ui.NoteViewModel

import com.example.notesapp.ui.NotesScreen

@Composable
fun AppNavGraph(navController: NavHostController, noteViewModel: NoteViewModel = viewModel()) {
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route){ NotesScreen(navController, noteViewModel) }
        composable(
            route = "edit/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: -1
            EditNoteScreen(id, navController, noteViewModel)
        }
    }
}
