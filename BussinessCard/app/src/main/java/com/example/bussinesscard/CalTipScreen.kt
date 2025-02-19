package com.example.bussinesscard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bussinesscard.ui.theme.GradientBtn
import java.text.NumberFormat
import kotlin.math.ceil

@Composable
fun CalTipScreen() {
    val cost = remember { mutableStateOf("") }
    val selectedTip = remember { mutableStateOf(18) }
    val tipOptions = listOf(15, 18, 20)
    val roundUp = remember { mutableStateOf(false) }
    val formattedTip = remember { mutableStateOf("") }
    fun calculateTip() {
        val costValue = cost.value.toDoubleOrNull() ?: 0.0
        val tipAmount = costValue * selectedTip.value / 100
        val finalTip = if (roundUp.value) ceil(tipAmount) else tipAmount

        // In giá trị để debug
        println("Cost: $costValue")
        println("Tip Percentage: ${selectedTip.value}%")
        println("Tip before rounding: $tipAmount")
        println("Round Up? ${roundUp.value}")
        println("Final Tip after rounding logic: $finalTip")

        // Định dạng lại số mà không làm tròn ngoài ý muốn
        formattedTip.value = "$${String.format("%.2f", finalTip)}"

        println("Formatted Tip: ${formattedTip.value}") // Kiểm tra giá trị cuối cùng
    }


    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = cost.value,
                onValueChange = { cost.value = it },
                label = { Text("Cost of service") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Blue,  // Viền dưới màu xanh khi focus
                    unfocusedIndicatorColor = Color.Gray, // Viền dưới màu xám khi không focus
                    focusedContainerColor = Color.Transparent, // Không có nền
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "How was the service?", color = Color.Gray)
            tipOptions.forEach { tip ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedTip.value = tip }
                        .padding(6.dp)
                ) {
                    RadioButton(
                        selected = (selectedTip.value == tip),
                        onClick = { selectedTip.value = tip }
                    )
                    Text(text = "$tip%", modifier = Modifier.padding(start = 8.dp))
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Round up tip?", modifier = Modifier.weight(1f))
                Switch(
                    checked = roundUp.value,
                    onCheckedChange = { roundUp.value = it }
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = formattedTip.value,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(
                        fontSize = 16.sp, fontStyle = FontStyle.Normal
                    ),
                    color = Color.Green,
                    textAlign = TextAlign.End
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { calculateTip() },
                    contentPadding = PaddingValues(),
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    shape = RoundedCornerShape(50.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .background(
                                GradientBtn,
                                shape = RoundedCornerShape(50.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("CALCULATOR")
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewOfCalTipScreen() {
    CalTipScreen()
}
