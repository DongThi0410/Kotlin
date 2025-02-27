package com.example.giuakyltdd.ui.Screens

import androidx.compose.ui.unit.dp
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.app02.nav.Screen

@Composable
fun HomeScreen(
    navController: NavController,

    ) {
    Column(modifier = Modifier.padding(16.dp)) {
        Button(
            onClick = { navController.navigate(Screen.ProductList.route) },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Products List")
        }
    }
}
