package com.example.notesapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app02.nav.Screen
import com.example.notesapp.Btn
import com.example.notesapp.MyTextField


@Composable
fun NotesScreen(navController: NavController, viewModel: NoteViewModel) {
    val notes by viewModel.notes.collectAsState()
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        MyTextField(
            "Tiêu đề",
            value = title,
            onValueChange = { title = it }
        )
        MyTextField(
            "Nội dung",
            value = content,
            onValueChange = { content = it }
        )
        Btn("Thêm ghi chú", onClick = {
            if (title.isNotEmpty() && content.isNotEmpty()) {
                viewModel.addNote(title, content); title = ""; content = ""
            }
        })
        Spacer(modifier = Modifier.height(16.dp))
        notes.forEach { note ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        note.id.let { id ->
                            navController.navigate("edit/$id")
                        }
                    },
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(note.title, style = MaterialTheme.typography.titleSmall)
                        Text(note.content, style = MaterialTheme.typography.bodyMedium)
                    }

                    IconButton(onClick = { viewModel.deleteNote(note) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Del")
                    }

                }
            }
        }
    }
}