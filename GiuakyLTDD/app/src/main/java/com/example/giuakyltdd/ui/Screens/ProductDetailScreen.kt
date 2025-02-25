package com.example.giuakyltdd.ui.Screens

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.giuakyltdd.DTO.Product
import com.example.giuakyltdd.ui.Btn
import com.example.giuakyltdd.ui.CommonScaffold
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@Composable
fun ProductDetailScreen(navController: NavController, productId: String) {
    val productState = remember { mutableStateOf<Product?>(null) }
    val database = FirebaseDatabase.getInstance().getReference("products/$productId")

    LaunchedEffect(productId) {
        database.get().addOnSuccessListener { snapshot ->
            productState.value = snapshot.getValue(Product::class.java)
        }.addOnFailureListener {
            Log.e("Firebase", "Không thể lấy dữ liệu sản phẩm: ${it.message}")
        }
    }
    CommonScaffold(title = "Chi tiết sản phẩm ", navController = navController) { p ->
        productState.value?.let { product ->
            Box(modifier = Modifier.padding(p)) {
                Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            tint = Color.Yellow,
                            contentDescription = "account"
                        )
                        Text(
                            text = " ${product.rating}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = rememberAsyncImagePainter(product.image),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Mô tả: ${product.description}",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 20.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween){
                        Text(
                            text = "Giá: ${product.price} VNĐ",
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        )
                        Btn("Thêm vào giỏ")
                    }

                }
            }
        } ?: CircularProgressIndicator()
    }
}
