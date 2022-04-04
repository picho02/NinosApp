package com.example.ninosapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ninosapp.R
import com.example.ninosapp.model.Pet

class AdoptAdapter(val pets: ArrayList<Pet>): RecyclerView.Adapter<AdoptAdapter.ViewHolder>() {
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val cardAdoptImage : ImageView = itemView.findViewById(R.id.ivAdoptPetItem)
        val cardName : TextView = itemView.findViewById(R.id.tvAdoptPetItemName)
        val cardOwner: TextView = itemView.findViewById(R.id.tvAdoptPetItemOwner)
        val cardGender: ImageView = itemView.findViewById(R.id.ivAdoptPetItemGender)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adopt_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Picasso.get().load(url).into(holder.cardAdoptImage)
        holder.cardName.text = pets[position].name
        holder.cardOwner.text = pets[position].owner
    }

    override fun getItemCount(): Int {
        return  pets.size
    }
}