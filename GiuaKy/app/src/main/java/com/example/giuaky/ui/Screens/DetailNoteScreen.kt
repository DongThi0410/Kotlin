package com.example.giuaky.ui.Screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.giuaky.BoldTextComponent
import com.example.giuaky.ui.viewModel.NoteViewModel
import com.example.giuaky.Btn
import com.example.giuaky.CommonScaffold
import com.example.giuaky.MyTextField
import com.example.giuaky.NormalTextComponent
import com.example.giuaky.data.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailNoteScreen(
    id: Int, navController: NavController, noteViewModel: NoteViewModel = viewModel()
) {
    var note by remember { mutableStateOf<Note?>(null) }

    LaunchedEffect(id) {
        note = noteViewModel.getNoteById(id)
    }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf("") }

    LaunchedEffect(note) {
        note?.let {
            title = it.title
            content = it.content
            imageUri = it.imageUrl
        }
    }
    CommonScaffold("Ghi chú", startAction = {
            IconButton(onClick = {navController.popBackStack()}) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back"
                )
            }
    }, actions = {

        IconButton(onClick = {
            note?.let {
                val editedNote = note!!.copy(title = title, content = content)
                noteViewModel.update(editedNote)
                navController.popBackStack()
            }
        }) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "save"
            )
        }
    }, navController = navController) { p ->

        Box(modifier = Modifier.padding(p)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        textColor = Color.Black
                    ),
                    textStyle = TextStyle(
                        fontSize = 22.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Normal
                    )
                )
                Divider(
                    modifier = Modifier.fillMaxWidth(), color = Color.Gray, thickness = 1.dp
                )
                TextField(
                    value = content,
                    onValueChange = { content = it },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        textColor = Color.Black
                    ),
                    textStyle = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    )
                )
                val bitmap = remember(imageUri) {
                    BitmapFactory.decodeFile(imageUri)
                }

                bitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "",
                        modifier = Modifier
                            .size(450.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

    }
}

