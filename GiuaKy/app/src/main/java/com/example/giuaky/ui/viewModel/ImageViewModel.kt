package com.example.giuaky.ui.viewModel;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giuaky.data.Image
import com.example.giuaky.repository.ImageRepo;
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ImageViewModel(private val repository: ImageRepo) : ViewModel() {
    private val _image = MutableStateFlow<List<Image>>(emptyList())
    val images: StateFlow<List<Image>> get() = _image

    init {
        viewModelScope.launch {
            repository.getAllImages().collect {
                _image.value = it
            }
        }
    }
    fun addImage(image: Image) = viewModelScope.launch {
        viewModelScope.launch {
            repository.insert(image)
        }
    }

    suspend fun getImageByNoteId(id: Int): Image? {
        return repository.getImageByNotetId(id)
    }
}
