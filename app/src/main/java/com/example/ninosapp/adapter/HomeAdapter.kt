package com.example.ninosapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ninosapp.databinding.HomeElementBinding
import com.example.ninosapp.model.Pet

class HomeAdapter(private val context: Context,
                  val pets: ArrayList<Pet>,
                  val onItemListener: OnItemListener
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HomeElementBinding.inflate(layoutInflater)
        return ViewHolder(binding, onItemListener)
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        holder.bindData(pets[position])
    }

    override fun getItemCount(): Int {
        return pets.size
    }

    interface OnItemListener {
        fun onItemClick(pet: Pet)
    }

    class ViewHolder(binding: HomeElementBinding, onItemListener: OnItemListener) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        private val binding = binding
        private val onItemListener = onItemListener
        private lateinit var pet: Pet

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onItemListener.onItemClick(pet)
        }

        fun bindData(item: Pet) {
            with(binding) {
                tvHomeName.text = item.name
                tvHomeAge.text = item.age.toString()
            }
            pet = item
        }
    }
}