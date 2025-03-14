package com.example.crudfirebase.Util

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

object AuthUtil {
    private val auth = FirebaseAuth.getInstance()

    fun signUpWithEmail(email: String, password: String, context: Context, onSuccess: () -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                    onSuccess()
                } else {
                    Toast.makeText(context, "Lỗi: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun signInWithEmail(email: String, password: String, context: Context, onSuccess: () -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
                    onSuccess()
                } else {
                    Toast.makeText(context, "Lỗi: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
