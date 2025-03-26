package com.example.giuaky.data
//import androidx.compose.ui.graphics.drawscope.Stroke
//import androidx.room.Entity
//import androidx.room.PrimaryKey

data class Note(
    var id: String? = null,
    val title: String="",
    val desc: String="",
    val imageUrl: String="",
)
