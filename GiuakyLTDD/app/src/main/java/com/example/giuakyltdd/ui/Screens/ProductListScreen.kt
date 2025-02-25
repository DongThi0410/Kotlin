package com.example.giuakyltdd.ui.Screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.app02.nav.Screen
import com.example.giuakyltdd.DTO.Product
import com.example.giuakyltdd.ui.Btn
import com.example.giuakyltdd.ui.CommonScaffold
import com.example.giuakyltdd.ui.theme.Purple40
import com.example.giuakyltdd.ui.theme.gray
import com.google.firebase.database.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    navController: NavController,

    ) {
    val products = remember { mutableStateListOf<Product>() }
    val database = FirebaseDatabase.getInstance().getReference("products")

    LaunchedEffect(Unit) {

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                products.clear()
                for (productSnapshot in snapshot.children) {
                    val product = productSnapshot.getValue(Product::class.java)
                    product?.let {
                        it.id = productSnapshot.key ?: ""
                        products.add(it)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "loi  lấy dữ liệu: ${error.message}")
            }
        })
    }

    CommonScaffold(navController = navController) {
            padding ->
        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {
            items(products) { product ->
                ProductItem(navController, product)
            }
        }
    }
}

@Composable
fun ProductItem(navController: NavController, product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate(Screen.ProductDetail.createRoute(product.id))
            }
        ,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Giá: ${product.price} VNĐ",
                    style = MaterialTheme.typography.bodyMedium
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
                Button(
                    colors = ButtonDefaults.buttonColors(gray),
                    onClick = {  },
                    contentPadding = PaddingValues(),
                    shape = RoundedCornerShape(50.dp),

                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = " ${product.price} VND",
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
                        )
                        Icon(
                            imageVector = Icons.Default.Add,
                            tint = Color.Black,
                            contentDescription = "addToCart"
                        )
                    }
                }
            }
        }
    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreviewOfDetailScreen() {
//    val navController = rememberNavController() // Tạo NavController giả để tránh lỗi
//    ProductListScreen(navController = navController)
//}
