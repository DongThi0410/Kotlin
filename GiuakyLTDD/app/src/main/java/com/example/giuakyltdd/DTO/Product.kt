package com.example.giuakyltdd.DTO

data class Product(
    var id: String = "",
    val name: String = "",
    val price: Int = 0,
    val description: String = "",
    val rating: Double = 0.0,
    val image: String = ""
) {
}