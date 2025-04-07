package com.example.marsphotoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

import com.example.marsphotoapp.ui.theme.MarsPhotoAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarsPhotoAppTheme {
                val imageViewModel: ImageViewModel = viewModel()
                val images by imageViewModel.image.collectAsState()
                LaunchedEffect(Unit){
                    imageViewModel.fetchImage()
                }
                Scaffold(
                    topBar = { TopAppBar(title = { Text("Mars Photo") }) },
                    modifier = Modifier.fillMaxSize()
                ) { p ->
                    Box(modifier = Modifier.padding(p)) {
                        LazyColumn {
                            items(images) { image ->
                                Text(
                                    text = image.des,
                                    style = TextStyle(
                                        fontSize = 16.sp, fontStyle = FontStyle.Normal
                                    ),
                                    color = Color.Black,
                                )
                                Image(
                                    painter = rememberAsyncImagePainter(model = image.image_url),
                                    contentDescription = image.des,
                                    modifier = Modifier
                                        .size(160.dp)
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .aspectRatio(2f / 3f)
                                        .clip(
                                            RoundedCornerShape(12.dp)
                                        )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

