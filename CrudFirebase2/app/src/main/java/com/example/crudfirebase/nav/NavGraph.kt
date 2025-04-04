package com.example.giuakyltdd

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.app02.nav.Screen
import com.example.crudfirebase.dto.Course
import com.example.crudfirebase.ui.Screens.DetailScreen
import com.example.crudfirebase.ui.Screens.ListCoursesScreen
import com.example.crudfirebase.ui.Screens.SingleImage
import com.example.giuakyltdd.ui.Screens.HomeScreen

@Composable
fun AppNavGraph(context: Context, navController: NavHostController) {
    val courses = remember { mutableStateListOf<Course?>() }

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen(context, navController) }
        composable(Screen.CourseList.route) { ListCoursesScreen(courses, navController, context) }
        composable(Screen.SingleImage.route) { SingleImage( navController) }
        composable(Screen.CourseDetail.route) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId")
            if (!courseId.isNullOrBlank()) {
                DetailScreen(courseId, navController)
            } else {
                Log.e("Navigation", "courseId không hợp lệ!")
            }
        }
    }
}
