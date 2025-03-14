package com.example.notesapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app02.nav.Screen
import com.example.notesapp.ui.theme.PurpleGrey80

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun MyTextField(
    labelValue: String, value: String,
    onValueChange: (String) -> Unit
) {
    var textValue = remember { mutableStateOf("") }

    OutlinedTextField(value = value,
        onValueChange = onValueChange,
        label = { Text(text = labelValue) },
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedLeadingIconColor = Color.Black,
            unfocusedLeadingIconColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.Black,
            unfocusedPlaceholderColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
     )
}

@Composable
fun Btn(
    value: String,
    onClick: () -> Unit,

) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClick,
            contentPadding = PaddingValues(),
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            shape = RoundedCornerShape(50.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .background(
                        Color.Gray,
                        shape = RoundedCornerShape(50.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                    Text(value, color = Color.White)
            }
        }
    }
}
