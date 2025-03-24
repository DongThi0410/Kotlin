package com.example.giuaky.repository

import com.example.giuaky.data.Image
import com.example.giuaky.data.ImageDao
import kotlinx.coroutines.flow.Flow

class ImageRepo(private val imageDao: ImageDao) {
    fun getAllImages(): Flow<List<Image>> = imageDao.getAllImage()

    suspend fun getImageByNotetId(id: Int): Image? = imageDao.getImageByNoteId(id)

    suspend fun insert(image: Image){
        imageDao.insert(image)
    }
}