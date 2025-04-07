package com.example.marsphotoapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app02.network.api.RetrofitInstance
import com.example.marsphotoapp.dto.Image
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ImageViewModel : ViewModel() {
    private val _image = MutableStateFlow<List<Image>>(emptyList())
    val image = _image.asStateFlow()

    fun fetchImage(){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.apiService.getImage()
                _image.value = response
            }catch (e: Exception){
                Log.e("", "${e.message}")
            }
        }
    }
}