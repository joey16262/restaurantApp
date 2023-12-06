package com.example.foodfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView : RecyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)


        val APIURL = "http://34.172.140.190/locations/"


        val client = OkHttpClient()
        val request = Request.Builder().url(APIURL).get().build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                var res = response.body?.string()
                if (res != null) {
                    res = res.substring(14,res.length-1)
                }
                res?.let {
                    val locationList: List<Location>? = parseLocation(it)
                    locationList?.let {
                        runOnUiThread {
                            recyclerView.adapter = LocationAdapter(locationList)
                        }
                    }
                }

            }

        })
    }

    private fun parseLocation(locationJson: String): List<Location>? {
        return try {
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val locationListType = Types.newParameterizedType(List::class.java, Location::class.java)
            val jsonAdapter: JsonAdapter<List<Location>> = moshi.adapter(locationListType)
            jsonAdapter.fromJson(locationJson)
        } catch (e: Exception) {
            Log.d("TAG", "parseDate: ${e.message}")
            null
        }

    }
}