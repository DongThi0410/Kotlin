package com.example.learnkotlin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.learnkotlin.ui.theme.Purple40

@Composable
fun CalcScreen() {
    val numb1 = remember { mutableStateOf("") }
    val numb2 = remember { mutableStateOf("") }
    val result = remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = numb1.value,
                onValueChange = { numb1.value = it },
                label = { Text("Nhập số thứ nhất") },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White
                )
            )
            OutlinedTextField(
                value = numb2.value,
                onValueChange = { numb2.value = it },
                label = { Text("Nhập số thứ hai") },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White, shape = RoundedCornerShape(20.dp))
                    .height(60.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (result.value.isEmpty()) "" else result.value,
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val operatorColors = ButtonDefaults.buttonColors(containerColor = Purple40)

                fun calculateResult(operator: String) {
                    val num1 = numb1.value.toFloatOrNull()
                    val num2 = numb2.value.toFloatOrNull()
                    result.value = if (num1 != null && num2 != null) {
                        when (operator) {
                            "+" -> (num1 + num2).toString()
                            "-" -> (num1 - num2).toString()
                            "*" -> (num1 * num2).toString()
                            "÷" -> (num1 / num2).toString()
                            else -> "Lỗi"
                        }
                    } else {
                        "Vui lòng nhập số hợp lệ"
                    }
                }

                Button(
                    onClick = { calculateResult("+") },
                    contentPadding = PaddingValues(),
                    colors = operatorColors,
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(45.dp)
                        .padding(4.dp)
                ) {
                    Text("+", color = Color.White)
                }

                Button(
                    onClick = { calculateResult("-") },
                    contentPadding = PaddingValues(),
                    colors = operatorColors,
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(45.dp)
                        .padding(4.dp)
                ) {
                    Text("-", color = Color.White)
                }

                Button(
                    onClick = { calculateResult("*") },
                    contentPadding = PaddingValues(),
                    colors = operatorColors,
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(45.dp)
                        .padding(4.dp)
                ) {
                    Text("*", color = Color.White)
                }

                Button(
                    onClick = { calculateResult("÷") },
                    contentPadding = PaddingValues(),
                    colors = operatorColors,
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(45.dp)
                        .padding(4.dp)
                ) {
                    Text("÷", color = Color.White)
                }
            }
        }
    }
}
@Preview
@Composable
fun DefaultCacl() {
    CalcScreen()
}