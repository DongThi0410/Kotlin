package com.example.giuaky

import android.widget.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text
import java.lang.reflect.Modifier

@Composable
fun HomeScreen() {
    // Kết nối đến Firebase Database
    val database = Firebase.database
    val myRef = database.getReference("message")

    var text = remember { mutableStateOf("") }

    // Giao diện Compose
    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = text,
            onValueChange = { newText -> text = newText },
            label = { Text(text = "Enter your name") }
        )

        Button(
            onClick = { myRef.setValue(text) },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Submit")
        }
    }
}
