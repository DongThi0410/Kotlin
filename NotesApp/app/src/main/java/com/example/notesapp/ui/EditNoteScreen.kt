package com.example.notesapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.notesapp.Btn
import com.example.notesapp.MyTextField
import com.example.notesapp.data.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun EditNoteScreen(
    id: Int,
    navController: NavController,
    noteViewModel: NoteViewModel = viewModel()
) {
    var note by remember { mutableStateOf<Note?>(null) }

    LaunchedEffect(id){
        note = noteViewModel.getNoteById(id)
    }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    LaunchedEffect (note){
        note?.let {
            title = it.title
            content = it.content
        }
    }
    Scaffold(topBar = {
        TopAppBar(title = { Text("Chỉnh sửa ghi chú") }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "back")
            }
        })
    }) { p ->
        Box(modifier = Modifier.padding(p)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                MyTextField("Tiêu đề", value = title, onValueChange = { title = it })
                Spacer(modifier = Modifier.padding(8.dp))
                MyTextField("Nội dung", value = content, onValueChange = { content = it })
                Spacer(modifier = Modifier.padding(8.dp))
                Btn("Lưu", onClick = {
                    note?.let {
                        val editedNote = note!!.copy(title = title, content = content)
                        noteViewModel.update(editedNote)
                        navController.popBackStack()
                    }
                })
            }
        }

    }
}

