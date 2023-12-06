package com.example.foodfinder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import okhttp3.MediaType.Companion.toMediaTypeOrNull

class UploadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.upload_layout)
        val backButton: Button = findViewById(R.id.button5)
        val uploadButton: Button = findViewById(R.id.button2)
        val ratingBar:RatingBar = findViewById(R.id.ratingBar2)
        val ratingScale:TextView = findViewById(R.id.ratingScale)

        val foodName: EditText = findViewById(R.id.descriptionText)
        val foodDescription: EditText = findViewById(R.id.cuisineText)
        val foodAddress: EditText = findViewById(R.id.addressText)
        val locID = intent.extras?.getInt("locationID")
        ratingBar.rating = 3f
        ratingBar.stepSize = 1f
        val intent = Intent(this, FoodActivity::class.java)
        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            ratingScale.text = rating.toString()
            when (ratingBar.rating.toInt()){
                1 -> ratingScale.text = "Very Bad"
                2 -> ratingScale.text = "Bad"
                3 -> ratingScale.text = "Good"
                4 -> ratingScale.text = "Great"
                5 -> ratingScale.text = "Awesome"
            }
        }
        backButton.setOnClickListener {
            val intent = Intent(this, FoodActivity::class.java)
            intent.putExtra("location", locID)
            startActivity(intent)
        }

        uploadButton.setOnClickListener {
            val foodPost = PostRequest(
                name = foodName.text.toString(),
                description = foodDescription.text.toString(),
                location_id = locID,
                address = foodAddress.text.toString(),
                rating = ratingBar.rating.toInt()
            )
            val json = Gson().toJson(foodPost)
            val okHttpClient = OkHttpClient()

            val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)
            Log.d("HERE", json.toString())
            val request = Request.Builder()
                .post(body)
                .url("http://34.172.140.190/restaurants/")
                .build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("Failed", e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.d("Upload", response.toString())
                    intent.putExtra("location", locID)
                    startActivity(intent)
                }
            })
        }
    }
}