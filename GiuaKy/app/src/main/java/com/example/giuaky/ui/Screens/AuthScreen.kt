package com.example.giuaky.ui.Screens

import android.widget.Toast
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.giuaky.Btn
import com.example.giuaky.CommonScaffold
import com.example.giuaky.ui.viewModel.AuthViewModel


@Composable
fun AuthScreen(navController: NavController, viewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }
    val context = LocalContext.current

    CommonScaffold("Tài khoản", navController = navController) {
        p->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(p),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth(0.75f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedLeadingIconColor = Color.Black,
                    unfocusedLeadingIconColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "email")
                }
            )
            Spacer(modifier = Modifier.height(8.dp))


            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(0.75f),
                label = { Text(text = "Mật khẩu") },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedLeadingIconColor = Color.Black,
                    unfocusedLeadingIconColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "lock")
                },
                trailingIcon = {
                    val ic = if (passwordVisible.value) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }
                    var description = if (passwordVisible.value) {
                        "Ẩn"
                    } else {
                        "Hiện"
                    }
                    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                        Icon(imageVector = ic, contentDescription = description)
                    }
                },
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                ),
            )
            Btn("ĐĂNG NHẬP", onClick = {
                viewModel.login(email, password, context) { success, msg ->
                    message = msg
                    if (success) {
                        navController.navigate("home")
                    }else
                        Toast.makeText(context, "Sai tt dang nhap", Toast.LENGTH_SHORT).show()

                }
            })
            Btn("ĐĂNG KY", onClick = {
                viewModel.signUp(email, password, context) { success, msg ->
                    message = msg
                    if (success) {
                        Toast.makeText(context, "Dang ky thanh cong", Toast.LENGTH_SHORT).show()
                        navController.navigate("home")
                    }else
                        Toast.makeText(context, "Email da ton tai", Toast.LENGTH_SHORT).show()

                }
            })
        }
    }
}

@Preview
@Composable
fun PreviewAuthScreen() {
    val navController = rememberNavController()
    AuthScreen(navController , viewModel = AuthViewModel())
}