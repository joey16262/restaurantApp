package com.example.foodfinder

import android.graphics.drawable.Drawable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Reviews (
    val date_created : String,
    val service : Int,
    val food: Int,
    val decor: Int,
    val description: String,
)
