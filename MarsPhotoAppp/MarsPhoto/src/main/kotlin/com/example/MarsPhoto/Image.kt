package com.example.MarsPhoto

import jakarta.persistence.*

@Entity
@Table(name = "image")
data class Image(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(name = "image_url")
    val image_url: String = "",

    @Column(name = "des")
    val des: String = ""
)