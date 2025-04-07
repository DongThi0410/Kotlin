package com.example.giuaky.ui.Screens

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AddPhotoAlternate
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.giuaky.BoldTextComponent
import com.example.giuaky.Btn
import com.example.giuaky.CommonScaffold
import com.example.giuaky.MyTextField
import com.example.giuaky.NormalTextComponent
import com.example.giuaky.data.Note
import com.example.giuaky.ui.viewModel.updateData
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailNoteScreen(
    noteId: String, navController: NavController
) {
    val db = FirebaseFirestore.getInstance()
    val note = remember { mutableStateOf<Note?>(null) }
    val context = LocalContext.current


    LaunchedEffect(noteId) {
        if (noteId.isNotEmpty()) {
            db.collection("notes").document(noteId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        note.value = document.toObject(Note::class.java)
                    }
                }
        }
    }
    note.value?.let { noteData ->
        var title by remember { mutableStateOf(noteData.title) }
        var imageUrl by remember { mutableStateOf(noteData.imageUrl) }
        var desc by remember { mutableStateOf(noteData.desc) }
        val imageLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            if (uri != null) {
                val savedPath = saveImageToInternalStorage(context, uri)
                if (savedPath != null) {
                    imageUrl = savedPath

                    db.collection("notes").document(noteId)
                        .update("imageUrl", savedPath)
                        .addOnSuccessListener {
                            Log.d("Firebase", "succes")
                        }
                        .addOnFailureListener{
                            e ->
                            Log.d("Firebase", "Error")
                        }
                }
            }
        }


        CommonScaffold("Ghi chú", startAction = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back"
                )
            }
        }, actions = {

            IconButton(onClick = {
                imageLauncher.launch("image/*")
            }) {
                if(imageUrl.isEmpty()){
                    Icon(
                        imageVector = Icons.Default.AddPhotoAlternate,
                        contentDescription = "addImage"
                    )
                }
            }
            IconButton(onClick = {
                updateData(noteId, title, desc, imageUrl, context, navController)

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
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal
                        )
                    )
                    Divider(
                        modifier = Modifier.fillMaxWidth(), color = Color.Gray, thickness = 1.dp
                    )
                    TextField(
                        value = desc,
                        onValueChange = { desc = it },
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
                    Box(
                        modifier = Modifier
                            .size(450.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.LightGray)
                            .clickable { imageLauncher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "Note Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                }
            }

        }
    }
}

