package com.example.giuaky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.giuaky.nav.AppNavGraph
//import com.example.giuaky.repository.NoteRepository
import com.example.giuaky.ui.viewModel.AuthViewModel
import com.example.notesapp.ui.theme.GiuaKyTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        val appCheck = FirebaseAppCheck.getInstance()
        appCheck.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance()
        )
        setContent {

            val navController = rememberNavController()
            val context = LocalContext.current

            GiuaKyTheme {
                AppNavGraph(context, navController, )
            }
        }


    }
}
