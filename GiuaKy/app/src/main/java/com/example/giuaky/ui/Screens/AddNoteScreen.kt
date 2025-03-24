package com.example.giuaky.ui.Screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.giuaky.Btn
import com.example.giuaky.CommonScaffold
import com.example.giuaky.MyTextField
import com.example.giuaky.ui.viewModel.NoteViewModel

@Composable
fun AddNoteScreen(navController: NavController, viewModel: NoteViewModel) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri
        }
    }

    CommonScaffold("Thêm ghi chú", startAction = {
        IconButton(onClick = {
            navController.popBackStack()
        }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = ""
            )
        }
    }, actions = {
        IconButton(onClick = {
            launcher.launch("image/*")
        }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "addImage"
            )
        }
        IconButton(onClick = {
            if (title.isNotEmpty() && content.isNotEmpty()) {
                val imagePath = imageUri?.let { saveImageToInternalStorage(context, it) } ?: ""

                viewModel.addNote(title, content, imagePath)
                title = ""; content = ""; imageUri = null
            }
            navController.popBackStack()
        }) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "createNote"
            )
        }
    }, navController = navController) { p ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(p)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                label = { Text("Tiêu đề") },
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
                modifier = Modifier.fillMaxWidth().padding(4.dp), color = Color.Gray, thickness = 1.dp
            )
            TextField(
                label = { Text("Nội dung") },
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

            imageUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "ảnh đã chọn",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(450.dp),
                    contentScale = ContentScale.Fit
                )
            }

        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreviewOfDetailScreen() {
//    val navController = rememberNavController()
//    AddNoteScreen(navController = navController)
//}
