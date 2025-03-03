package com.example.giuakyltdd.ui.Screens

import android.annotation.SuppressLint
import android.widget.Toast

import androidx.compose.ui.unit.dp
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.app02.nav.Screen
import com.example.crudfirebase.Btn
import com.example.crudfirebase.MyTextField
import com.example.crudfirebase.dto.Course
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import android.content.Context

@Composable
fun HomeScreen(
    context: Context,
navController: NavController
    ) {
    val name = remember { mutableStateOf("") }
    val duration = remember { mutableStateOf("") }
    val desc = remember { mutableStateOf("") }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            Spacer(modifier = Modifier.height(20.dp))



            MyTextField("Tên khóa học", value = name.value,
                onValueChange = { name.value = it  })
            Spacer(modifier = Modifier.height(10.dp))
            MyTextField("Thời gian diễn ra khóa học", value = duration.value,
                onValueChange = { duration.value = it })
            Spacer(modifier = Modifier.height(10.dp))

            MyTextField("Mô tả khóa học", value = desc.value,
                onValueChange = { desc.value = it })
            Spacer(modifier = Modifier.height(10.dp))



            Btn(
                value = "Thêm khóa học",
                onClick = {
                    addData(name.value, duration.value, desc.value, context)
                },
            )


            Btn(
                value = "Xem danh sách khóa học",
                onClick = {
                    navController.navigate(Screen.CourseList.route)
                },
            )


            Spacer(modifier = Modifier.height(16.dp))



        }


    }
}
fun addData(name: String, duration: String, desc: String, context: Context) {
    val db = FirebaseFirestore.getInstance()
    val dbCourse = db.collection("courses")

    val newCourseRef = dbCourse.document() // Tạo document trước để lấy ID
    val course = Course(id = newCourseRef.id, name = name, duration = duration, desc = desc)

    newCourseRef.set(course)
        .addOnSuccessListener {
            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener { e ->
            Toast.makeText(context, "Lỗi khi thêm khóa học: ${e.message}", Toast.LENGTH_SHORT).show()
        }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreviewOfHomeScreen() {
//    val navController = rememberNavController()
//    HomeScreen(navController = navController)
//}