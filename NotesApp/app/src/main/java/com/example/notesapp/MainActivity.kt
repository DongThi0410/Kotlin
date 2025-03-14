package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.app02.nav.AppNavGraph
import com.example.notesapp.data.NoteDatabase
import com.example.notesapp.data.NoteRepository
import com.example.notesapp.ui.NoteViewModel
import com.example.notesapp.ui.NotesScreen
import com.example.notesapp.ui.theme.NotesAppTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(applicationContext, NoteDatabase::class.java, "ltdd.db").build()
        val repository = NoteRepository(db.noteDao())
        val viewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NoteViewModel(repository) as T
            }
        }

        setContent {
            val navController = rememberNavController()

            NotesAppTheme {
                AppNavGraph(navController = navController, noteViewModel = viewModel(factory = viewModelFactory))
            }
        }
    }
}
