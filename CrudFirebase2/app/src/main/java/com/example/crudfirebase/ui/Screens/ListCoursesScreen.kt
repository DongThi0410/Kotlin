package com.example.crudfirebase.ui.Screens

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import com.example.crudfirebase.dto.Course
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.app02.nav.Screen
import com.example.crudfirebase.Btn
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("UnrememberedMutableState")
@Composable
fun ListCoursesScreen(courses: SnapshotStateList<Course?>, navController: NavController, context: Context) {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // 🔹 Danh sách khóa học
    val coursesList = remember { mutableStateListOf<Course?>() }

    LaunchedEffect(Unit) {
        db.collection("courses")
            .get()
            .addOnSuccessListener { result ->
                courses.clear()
                for (document in result) {
                    val course = document.toObject(Course::class.java)
                    course?.id = document.id
                    courses.add(course)
                }
            }
            .addOnFailureListener { exception ->
                println("Lỗi khi lấy dữ liệu: ${exception.message}")
            }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        itemsIndexed(courses) { index, item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        item?.id?.let { courseId ->
                            if (courseId.isNotBlank()) {
                                navController.navigate("detail/$courseId") // ✅ Điều hướng đúng cách
                            } else {
                                Log.e("Navigation", "courseId trống hoặc null!")
                            }
                        } ?: Log.e("Navigation", "item.id là null!")

                    },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(0.85f)) {
                        item?.name?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }

                        item?.desc?.let {
                            Text(text = it, style = MaterialTheme.typography.bodyMedium)
                        }
                        item?.duration?.let {
                            Text(text = it, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                    Box(modifier = Modifier.weight(0.25f)) {
                        Button(
                            colors = ButtonDefaults.buttonColors(Color.Red),
                            onClick = {
                                item?.id?.let { id ->
                                    deleteData(id, context, navController)
                                }                            },
                            contentPadding = PaddingValues(),
                            shape = RoundedCornerShape(50.dp),

                            ) {

                            Icon(
                                imageVector = Icons.Default.Delete,
                                tint = Color.White,
                                contentDescription = "removeFromCart"
                            )
                        }
                    }
                }
            }
        }
    }
}
fun deleteData(courseId: String, context: Context, navController: NavController) {
    val db = FirebaseFirestore.getInstance()

    db.collection("courses").document(courseId)
        .delete()
        .addOnSuccessListener {
            navController.navigate(Screen.CourseList.route)
            Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener { e -> Toast.makeText(context, "Lỗi: ${e.message}", Toast.LENGTH_SHORT).show() }
}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreviewOfCourseScreen() {
//    val courses = remember { mutableStateListOf<Course?>() }
//    ListCoursesScreen(courses)
//}