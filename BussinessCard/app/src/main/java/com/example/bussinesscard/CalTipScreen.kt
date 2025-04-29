package com.example.bussinesscard
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

        val finalTip = if (roundUp.value) {
            val fractionalPart = tipAmount - tipAmount.toInt()
            if (fractionalPart < 0.5) {
                tipAmount.toInt() + 0.5
            } else {
                ceil(tipAmount)
            }
        } else {
            tipAmount
        }

        println("Cost: $costValue")
        println("Tip Percentage: ${selectedTip.value}%")
        println("Tip before rounding: $tipAmount")
        println("Round Up? ${roundUp.value}")
        println("Final Tip after rounding logic: $finalTip")

        formattedTip.value = "$${String.format("%.2f", finalTip)}"

        println("Formatted Tip: ${formattedTip.value}")
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = cost.value,
                onValueChange = { cost.value = it },
                label = { Text("Cost of service") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Blue,
                    unfocusedIndicatorColor = Color.Gray,
                    focusedContainerColor = Color.Transparent,
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

            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = formattedTip.value,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Normal
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
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .background(
                                brush = GradientBtn, // <- bạn cần tự khai báo cái này nhé
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
