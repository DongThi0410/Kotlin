package com.example.MarsPhoto

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Collections.emptyList

@RestController
@RequestMapping("image")
class ImageController(private val imageService: ImageService) {
    @GetMapping
     fun getAllImage(): ResponseEntity<List<Image>>{
        return try {
            val images = imageService.getAllImage()
            ResponseEntity.ok(images)
        } catch (e:Exception){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(emptyList())
        }
    }
}