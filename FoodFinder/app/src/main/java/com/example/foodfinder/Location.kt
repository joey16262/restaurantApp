package com.example.foodfinder

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location (
    val id: Int,
    val name : String,

)
