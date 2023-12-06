package com.example.foodfinder

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class FoodAdapter(
    val foodList: List<Food>,
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val diningName:TextView = view.findViewById(R.id.diningNameText)
        val addressName:TextView = view.findViewById(R.id.addressText2)
        var rating = 3
        var description = ""
        var locId = 0
        var restId = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.food_cells_layout, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val post: Food = foodList[position]
        holder.rating = post.rating
        holder.description = post.description
        holder.diningName.text = post.foodName
        holder.addressName.text = post.address
        holder.locId=post.location_id
        holder.restId=post.id
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.diningName.context, FoodViewActivity::class.java)
            intent.putExtra("foodName", holder.diningName.text)
            intent.putExtra("address", holder.addressName.text)
            intent.putExtra("descriptionText", holder.description)
            intent.putExtra("ratingText", holder.rating)
            intent.putExtra("locationID", holder.locId)
            intent.putExtra("restID", holder.restId)
            holder.diningName.context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return foodList.size
    }
}