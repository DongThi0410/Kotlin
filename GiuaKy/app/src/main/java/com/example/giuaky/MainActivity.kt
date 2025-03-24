package com.example.giuaky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.giuaky.nav.AppNavGraph
import com.example.giuaky.ui.viewModel.NoteViewModel
import com.example.giuaky.repository.NoteRepository
import com.example.giuaky.data.db
import com.example.giuaky.ui.viewModel.AuthViewModel
import com.example.notesapp.ui.theme.GiuaKyTheme
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseAppCheck.getInstance().installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance()
        )
        val db = Room.databaseBuilder(applicationContext, db::class.java, "ltdd.db").build()
        val repository = NoteRepository(db.noteDao())
        val viewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NoteViewModel(repository) as T
            }

        }

        setContent {

            val navController = rememberNavController()
            val noteViewModel: NoteViewModel = viewModel(factory = viewModelFactory)
            val authViewModel: AuthViewModel = viewModel()

            GiuaKyTheme {
                AppNavGraph(navController, noteViewModel, authViewModel)
            }
        }
    }
}
