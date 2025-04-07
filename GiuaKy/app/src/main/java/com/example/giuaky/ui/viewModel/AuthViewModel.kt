package com.example.giuaky.ui.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.SignInPassword
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _isUserLoggedIn = MutableLiveData(auth.currentUser != null)
    val isUserLoggedIn: LiveData<Boolean> = _isUserLoggedIn

    fun signUp(
        email: String,
        password: String,
        context: Context,
        onResult: (Boolean, String) -> Unit
    ) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { signUpTask ->
                if (signUpTask.isSuccessful) {
                    onResult(true, "")

                } else {
                    onResult(false, signUpTask.exception?.message ?: "Đăng ký thất bại!")
                }
            }
    }


    fun login(
        email: String,
        password: String,
        context: Context,
        onResult: (Boolean, String) -> Unit
    ) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { loginTask ->
                if (loginTask.isSuccessful) {
                    onResult(true, "")
                } else {
                    val errorMessage = loginTask.exception?.message ?: "Lỗi không xác định"
                    Log.e("FirebaseAuth", "Lỗi đăng nhập: $errorMessage")
                    onResult(false, errorMessage)
                }
            }

    }

    fun logout() {
        auth.signOut()
        _isUserLoggedIn.value = false
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}
