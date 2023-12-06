package com.example.foodfinder


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Half.toFloat
import android.util.Log
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import java.net.URI

class FoodViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.food_view_layout)
        val backButton: Button = findViewById(R.id.button3)
        val deleteButton: Button = findViewById(R.id.deleteButton)
        val foodName: TextView = findViewById(R.id.foodName)
        val address: TextView = findViewById(R.id.addressText)
        val description:TextView = findViewById(R.id.descriptionText2)
        val ratingBar:RatingBar = findViewById(R.id.ratingBar)
        val ratingScale:TextView=findViewById(R.id.ratingScale2)
        //val intent = Intent(this, MainActivity::class.java)

        val diningName = intent.extras?.getString("foodName")
        val addressName = intent.extras?.getString("address")
        val descriptionName = intent.extras?.getString("descriptionText")
        val rating = intent.extras?.getInt("ratingText")
        val locID = intent.extras?.getInt("locationID")
        val restID = intent.extras?.getInt("restID")
        foodName.text = diningName
        address.text = addressName
        description.text = descriptionName
        if (rating != null) {
            ratingBar.setRating(rating.toFloat())
            ratingScale.text = rating.toString()
            when (ratingBar.getRating().toInt()){
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

        deleteButton.setOnClickListener {

            val okHttpClient = OkHttpClient()

            val request = Request.Builder()
                .delete()
                .url("http://34.172.140.190/restaurants/${restID}")
                .build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("Failed", e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.d("Upload", response.toString())
//                    intent.putExtra("location", locID)
//                    startActivity(intent)
                }
            })
        }
    }


}