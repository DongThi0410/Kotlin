package com.example.crudfirebase.Util

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.util.UUID

class StorageUtil {
    companion object {
        fun uploadToStorage(uri: Uri, context: Context, type: String) {
            val storage = FirebaseStorage.getInstance()
            val db = FirebaseFirestore.getInstance()
            val storageRef = storage.reference
            val uniqueImageName = UUID.randomUUID().toString()
            val spaceRef = storageRef.child("images/$uniqueImageName.jpg")

            val uploadTask = spaceRef.putFile(uri)

            uploadTask.addOnFailureListener {
                Log.e("FirebaseUpload", "Upload failed: ${it.message}")
                Toast.makeText(context, "Tải lên thất bại!", Toast.LENGTH_SHORT).show()
            }.addOnSuccessListener { taskSnapshot ->
                spaceRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    val imageData = hashMapOf(
                        "id" to uniqueImageName,
                        "url" to downloadUri.toString(),
                        "uploaded_at" to com.google.firebase.firestore.FieldValue.serverTimestamp(),
                        "size" to taskSnapshot.metadata?.sizeBytes,
                        "type" to type
                    )

                    db.collection("images").document(uniqueImageName).set(imageData)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Tải lên thành công!", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Log.e("FirebaseUpload", "Lưu Firestore thất bại: ${e.message}")
                            Toast.makeText(context, "Lưu dữ liệu thất bại!", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }
    }
}
