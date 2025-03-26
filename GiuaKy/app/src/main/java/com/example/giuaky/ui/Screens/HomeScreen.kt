package com.example.giuaky.ui.Screens


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.giuaky.CommonScaffold
import com.example.giuaky.data.Note
import com.example.giuaky.nav.Screen
import com.example.giuaky.ui.theme.Purple80
import com.example.giuaky.ui.theme.gray
import com.example.giuaky.ui.viewModel.deleteData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.FileOutputStream


@Composable
fun HomeScreen(notes: SnapshotStateList<Note?>,navController: NavController, context: Context) {
    val db = FirebaseFirestore.getInstance()
//    val notes = remember { mutableStateListOf<Note>() }

    LaunchedEffect(Unit) {
        db.collection("notes").addSnapshotListener { result, error ->
            if (error != null) {
                Log.e("Firestore", "Lỗi: ${error.message}")
                return@addSnapshotListener
            }

            notes.clear()
            result?.documents?.forEach { document ->
                val note = document.toObject(Note::class.java)
                note?.id = document.id
                notes.add(note)
            }
        }
    }


    CommonScaffold(
        actions = {
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { navController.navigate(Screen.Add.route) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add")
            }
        },
        navController = navController
    ) { p ->
        LazyColumn(modifier = Modifier.padding(p)) {
            if (notes.isEmpty()) {
                item {
                    Text(
                        "",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                items(notes) { note ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                    note?.id?.let { id -> navController.navigate("edit/$id") }
                            },
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val imageUrlState = rememberUpdatedState(note?.imageUrl)
                            AsyncImage(
                                model = imageUrlState.value,
                                contentDescription = "Ảnh ghi chú",
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.padding(4.dp)) {
                                note?.title?.let {
                                    Text(
                                        note.title,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium)
                                    )
                                }
                                note?.desc?.let {
                                    Text(note.desc, style = MaterialTheme.typography.bodyMedium)
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.7f)
                                        .padding(4.dp)
                                        .background(Color.LightGray, shape = RoundedCornerShape(50.dp))
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        IconButton(onClick = {
                                            note?.id?.let { id -> deleteData(id, context, navController) }
                                        }) {
                                            Icon(Icons.Default.Delete, contentDescription = "Xóa", tint = Color.Gray)
                                        }
                                        IconButton(onClick = {
                                            note?.id?.let { id -> navController.navigate("edit/$id") }
                                        }) {
                                            Icon(Icons.Default.Edit, contentDescription = "Sửa", tint = Color.Gray)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)

        val filename = "IMG_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, filename)

        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)

        outputStream.flush()
        outputStream.close()

        file.absolutePath // trả về đường dẫn ảnh đã lưu
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

