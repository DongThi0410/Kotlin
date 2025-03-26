package com.example.giuaky.ui.viewModel

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import com.example.giuaky.data.Note
import com.example.giuaky.nav.Screen
import com.google.firebase.firestore.FirebaseFirestore



    fun updateData(noteId: String, title: String, desc: String, imageUrl: String, context: Context, navController: NavController) {
        val db = FirebaseFirestore.getInstance()
        val noteData = mapOf(
            "title" to title,
            "desc" to desc,
            "imagesUrl" to imageUrl
        )

        db.collection("notes").document(noteId)
            .update(noteData)
            .addOnSuccessListener {
                Toast.makeText(context, "Dax luu", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Lỗi: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

fun deleteData(noteId: String, context: Context, navController: NavController) {
    val db = FirebaseFirestore.getInstance()

    db.collection("notes").document(noteId)
        .delete()
        .addOnSuccessListener {
            navController.navigate(Screen.Home.route)
            Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener { e -> Toast.makeText(context, "Lỗi: ${e.message}", Toast.LENGTH_SHORT).show() }
}

fun addData(title: String, desc: String, imageUrl: String, context: Context) {
    val db = FirebaseFirestore.getInstance()
    val dbNote = db.collection("notes")

    val newNoteRef = dbNote.document() // Tạo document trước để lấy ID
    val note = Note(id = newNoteRef.id, title = title, imageUrl = imageUrl, desc = desc)

    newNoteRef.set(note)
        .addOnSuccessListener {
            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener { e ->
            Toast.makeText(context, "Lỗi khi thêm khóa học: ${e.message}", Toast.LENGTH_SHORT)
                .show()
        }
}
