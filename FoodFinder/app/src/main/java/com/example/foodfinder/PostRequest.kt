package com.example.foodfinder

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostRequest(
    val name: String,
    val description: String,
    val location_id: Int?,
    val address: String,
    val rating: Int
)