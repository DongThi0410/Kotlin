package com.example.learnkotlin

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.learnkotlin.ui.theme.Purple40
@Composable
fun CalcScreen() {
    var display by remember { mutableStateOf("0") }
    var lastNumber by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf("") }
    var isNewInput by remember { mutableStateOf(true) }

    fun onDelete() {
        if (display.length > 1) {
            display = display.dropLast(1)
        } else {
            display = "0"
        }
    }


    fun onButtonClick(value: String) {
        when (value) {
            "C" -> {
                display = "0"
                lastNumber = ""
                operator = ""
                isNewInput = true
            }
            "+", "-", "×", "÷" -> {
                lastNumber = display
                operator = value
                isNewInput = true
            }
            "=" -> {
                val num1 = lastNumber.toFloatOrNull() ?: 0f
                val num2 = display.toFloatOrNull() ?: 0f
                val result = when (operator) {
                    "+" -> num1 + num2
                    "-" -> num1 - num2
                    "×" -> num1 * num2
                    "÷" -> if (num2 != 0f) num1 / num2 else "Error"
                    else -> "Error"
                }
                display = result.toString()
                isNewInput = true
            }
            "del" ->{
                onDelete()
            }
            else -> {
                if (isNewInput) {
                    display = value
                    isNewInput = false
                } else {
                    display = if (display == "0") value else display + value
                }
            }
        }
    }

    Scaffold(
        topBar = {
            Text(
                text = display,
                fontSize = 40.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                color = Color.Black
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val buttons = listOf(
                listOf("7", "8", "9", "÷"),
                listOf("4", "5", "6", "×"),
                listOf("1", "2", "3", "-"),
                listOf("0", "C", "del", "+")
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { onButtonClick("=") },
                    modifier = Modifier
                        .size(80.dp)
                        .padding(5.dp),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                    colors = ButtonDefaults.buttonColors(
                        Color.LightGray
                    )
                ) {
                    Text(
                        "=",
                        fontSize = 24.sp,
                        color = Color.Black
                    )
                }
            }
            for (row in buttons) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (btn in row) {
                        Button(
                            onClick = { onButtonClick(btn) },
                            modifier = Modifier
                                .size(80.dp),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, Color.Gray),
                            colors = ButtonDefaults.buttonColors(
                                 Color.LightGray
                            )
                        ) {
                            Text(btn, fontSize = 20.sp)
                        }
                    }
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