package com.example.crudfirebase.ui.Screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app02.nav.Screen
import com.example.crudfirebase.Btn
import com.example.crudfirebase.MyTextField
import com.example.crudfirebase.dto.Course
import com.example.giuakyltdd.ui.Screens.addData
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(courseId: String, navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val course = remember { mutableStateOf<Course?>(null) }
    val context = LocalContext.current

    LaunchedEffect(courseId) {
        if (courseId.isNotEmpty()) {
            db.collection("courses").document(courseId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        course.value = document.toObject(Course::class.java)
                    }
                }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(course.value?.name ?: "Chi tiết khóa học") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            course.value?.let { courseData ->
                var name by remember { mutableStateOf(courseData.name) }
                var duration by remember { mutableStateOf(courseData.duration) }
                var desc by remember { mutableStateOf(courseData.desc) }

                MyTextField(
                    value = name,
                    labelValue = "Tên khóa học",
                    onValueChange = { name = it }
                )
                Spacer(modifier = Modifier.height(8.dp))
                MyTextField(
                    value = duration,
                    labelValue = "Thời lượng khóa học",
                    onValueChange = { duration = it }
                )
                Spacer(modifier = Modifier.height(8.dp))
                MyTextField(
                    value = desc,
                    labelValue = "Mô tả khóa học",
                    onValueChange = { desc = it }
                )

                Spacer(modifier = Modifier.height(16.dp))
                Btn("Cập nhật thông tin") {
                    updateData(courseId, name, duration, desc, context, navController)
                }
            } ?: Text(text = "Đang tải dữ liệu...", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

fun updateData(courseId: String, name: String, duration: String, desc: String, context: Context, navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val courseData = mapOf(
        "name" to name,
        "duration" to duration,
        "desc" to desc
    )

    db.collection("courses").document(courseId)
        .update(courseData)
        .addOnSuccessListener {
            Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
        .addOnFailureListener { e ->
            Toast.makeText(context, "Lỗi: ${e.message}", Toast.LENGTH_SHORT).show()
        }
}

