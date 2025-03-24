package com.example.giuaky.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.SignInPassword
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signUp(email: String, password: String, onResult: (Boolean, String) -> Unit) {
        auth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val signInMethods = task.result?.signInMethods
                    if (!signInMethods.isNullOrEmpty()) {
                        onResult(false, "Email đã được đăng ký!")
                    } else {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { signUpTask ->
                                if (signUpTask.isSuccessful) {
                                    onResult(true, "Đăng ký thành công!")
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

    fun login(email: String, password: String, onResult: (Boolean, String) -> Unit) {
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
                                    onResult(true, "Đăng nhập thành công!")
                                } else {
                                    onResult(false, loginTask.exception?.message ?: "Sai mật khẩu!")
                                }
                            }
                    }
                } else {
                    onResult(false, "Lỗi khi kiểm tra tài khoản!")
                }
            }
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}
