package com.example.foodfinder


import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LocationAdapter(
    val locList: List<Location>,
) : RecyclerView.Adapter<LocationAdapter.HallsViewHolder>() {

    class HallsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val locText: TextView = view.findViewById(R.id.locationText)
        var locId = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HallsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.location_cell_layout, parent, false)
        return HallsViewHolder(view)
    }

    override fun onBindViewHolder(holder: HallsViewHolder, position: Int) {
        val post: Location = locList[position]

        holder.locText.text = post.name
        holder.locId=post.id
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.locText.context, FoodActivity::class.java)
            intent.putExtra("location", holder.locId)
            holder.locText.context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return locList.size
    }
}