package com.example.app02.nav

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object CourseList : Screen("list_course")
    object CourseDetail : Screen("detail/{courseId}") {
    }
}
