package com.example.foodfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class FoodActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.food_layout)
        val locationId = intent.extras?.getInt("location")
        val backButton: Button = findViewById(R.id.button4)
        val addButton : Button = findViewById(R.id.button)

        val recyclerView : RecyclerView = findViewById(R.id.recycler2)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val APIURL = "http://34.172.140.190/restaurants/${locationId}/"


        val client = OkHttpClient()
        val request = Request.Builder().url(APIURL).get().build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                var res = response.body?.string()
                if (res != null) {

                    res = res.substring(16,res.length-1)
                }
                res?.let {
                    val foodList: List<Food>? = parseFood(it)
                    foodList?.let {
                        runOnUiThread {

                            recyclerView.adapter = FoodAdapter(foodList)
                        }
                    }
                }

            }

        })

        addButton.setOnClickListener {
            val intent = Intent(this, UploadActivity::class.java)
            intent.putExtra("locationID", locationId)
            startActivity(intent)
        }
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }


    private fun parseFood(foodJson: String): List<Food>? {
        return try {
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val foodListType = Types.newParameterizedType(List::class.java, Food::class.java)
            val jsonAdapter: JsonAdapter<List<Food>> = moshi.adapter(foodListType)
            jsonAdapter.fromJson(foodJson)
        } catch (e: Exception) {
            Log.d("TAG", "parseDate: ${e.message}")
            null
        }

    }
}