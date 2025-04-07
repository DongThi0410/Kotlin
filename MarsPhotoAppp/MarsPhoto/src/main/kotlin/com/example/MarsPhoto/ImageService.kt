package com.example.MarsPhoto

import com.example.MarsPhoto.Image
import com.example.MarsPhoto.ImageRepository
import org.springframework.stereotype.Service

@Service
class ImageService(private val imageRepository: ImageRepository){
    fun getAllImage(): List<Image> {
        val list = imageRepository.findAll()
        list.forEach { println(it) }  // Log ra để kiểm tra imageUrl
        return list
    }

}