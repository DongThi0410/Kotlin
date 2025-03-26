package com.example.giuaky.ui.viewModel

import android.content.Context
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

    fun signUp(email: String, password: String,context: Context, onResult: (Boolean, String) -> Unit) {
        auth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val signInMethods = task.result?.signInMethods
                    if (!signInMethods.isNullOrEmpty()) {
                        onResult(false, "E")
                    } else {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { signUpTask ->
                                if (signUpTask.isSuccessful) {
                                    onResult(true, "")

                                } else {
                                    onResult(false, signUpTask.exception?.message ?: "Đăng ký thất bại!")
                                }
                            }
                    }
                } else {
                    onResult(false, "Lỗi khi kiểm tra tài khoản!")
                }
            }
    }

    fun login(email: String, password: String,context: Context, onResult: (Boolean, String) -> Unit) {
        auth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val signInMethods = task.result?.signInMethods
                    if (signInMethods.isNullOrEmpty()) {
                        onResult(false, "Tài khoản chưa tồn tại!")
                    } else {
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { loginTask ->
                                if (loginTask.isSuccessful) {
                                    onResult(true,"")
                                } else {
                                    onResult(false, "")
                                }
                            }
                    }
                } else {
                    onResult(false, "Lỗi  kiểm tra ")
                }
            }
    }
    fun logout(){
        auth.signOut()
        _isUserLoggedIn.value = false
    }
    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}
