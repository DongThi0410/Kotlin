package com.example.crudfirebase.ui.Screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.crudfirebase.CommonScaffold
import com.example.crudfirebase.Util.StorageUtil

@Composable
fun SingleImage(
    navController: NavController
) {
    var uri by remember { mutableStateOf<Uri?>(null) }
    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { pickedUri -> uri = pickedUri }
    )

    val context = LocalContext.current

    CommonScaffold(navController = navController) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Button(onClick = {
                singlePhotoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }) {
                Text("Chọn Ảnh")
            }

            AsyncImage(
                model = uri,
                contentDescription = "Ảnh đã chọn",
                modifier = Modifier.size(248.dp)
            )

            Button(onClick = {
                uri?.let {
                    StorageUtil.uploadToStorage(it, context, "image")
                } ?: Toast.makeText(context, "Bạn chưa chọn ảnh!", Toast.LENGTH_SHORT).show()
            }) {
                Text("Tải lên Firebase")
            }
        }
    }
}
