package com.example.foodfinder

import android.graphics.drawable.Drawable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodInfo (
    val food: PostRequest
)