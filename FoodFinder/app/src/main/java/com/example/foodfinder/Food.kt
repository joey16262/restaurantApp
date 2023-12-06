package com.example.foodfinder

import android.graphics.drawable.Drawable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Food (
    val id: Int,
    @Json(name = "name") val foodName : String,
    val description: String,
    val cuisine: String?,
    val address: String,
    val image: String?,
    val rating: Int,
    val location_id: Int,
    val reviews: List<String?>



)