package com.example.crudfirebase.dto

import kotlin.time.Duration

data class Course (
    var id: String? = null,
    val name: String = "",
    val duration: String = "",
    val desc: String = ""
)