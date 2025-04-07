package com.example.marsphotoapp.api

import com.example.marsphotoapp.dto.Image
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    @GET("image")
    suspend fun getImage(): List<Image>

}