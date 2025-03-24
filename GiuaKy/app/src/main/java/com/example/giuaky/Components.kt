package com.example.giuaky

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.*

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.giuaky.nav.Screen
import com.example.giuaky.ui.theme.Purple80
import com.example.giuaky.ui.theme.PurpleGrey80
import com.example.giuaky.ui.theme.backgroundGray
import com.example.giuaky.ui.theme.border
import com.example.giuaky.ui.theme.btn
import com.example.giuaky.ui.theme.gray


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonScaffold(
    title: String = "", navController: NavController, startAction: @Composable RowScope.() -> Unit = {}, actions: @Composable RowScope.() -> Unit = {}, content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = { Row(content = startAction) },
                actions = actions,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundGray
                )
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(modifier = Modifier.fillMaxWidth()){
                    Box(modifier = Modifier
                        .weight(1f)
                        .clickable { navController.navigate(Screen.Home.route) }, contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "home"
                        )
                    }
                    Box(modifier = Modifier
                        .weight(1f)
                        .clickable { }, contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "add product"
                        )
                    }
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "account"
                        )
                    }
                }
            }
        }

    ) {padding ->
        content(padding)
    }
}
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
        modifier = Modifier.fillMaxWidth(0.75f),
        colors = TextFieldDefaults.colors(
            focusedLeadingIconColor = Color.Black,
            unfocusedLeadingIconColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            focusedIndicatorColor = Purple80,
            unfocusedIndicatorColor = Color.Gray,
            unfocusedPlaceholderColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
     )
    Spacer(modifier = Modifier.height(8.dp))

}

@Composable
fun Btn(
    value: String,
    onClick: () -> Unit

    ) {
    Box(
        modifier = Modifier.fillMaxWidth(0.5f),
        contentAlignment = Alignment.Center
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onClick,
            contentPadding = PaddingValues(),
            colors = ButtonDefaults.buttonColors(Color.LightGray),
            shape = RoundedCornerShape(50.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(35.dp)
                    .background(
                        Color.LightGray,
                        shape = RoundedCornerShape(50.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                    Text(value, color = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

    }
}
@Composable
fun NormalTextComponent(value: String, textAlign: TextAlign = TextAlign.Center) {

    Text(
        text = value,
        style = TextStyle(
            fontSize = 16.sp, fontStyle = FontStyle.Normal
        ),
        color = Color.White,
        textAlign = textAlign
    )
}
@Composable
fun BoldTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth(),
        style = TextStyle(
            fontSize = 20.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Normal
        ),
        color = colorResource(id = R.color.white),
        textAlign = TextAlign.Center,

    )
    Spacer(
        modifier = Modifier.height(2.dp)
    )

}
