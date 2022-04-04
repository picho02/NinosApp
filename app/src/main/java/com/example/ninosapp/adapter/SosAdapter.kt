package com.example.ninosapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ninosapp.databinding.HomeElementBinding
import com.example.ninosapp.databinding.SosElementBinding
import com.example.ninosapp.fragments.SosFragment
import com.example.ninosapp.model.Pet

class SosAdapter(private val context: Context,
                 val pets: ArrayList<Pet>,
                 val onItemListener: SosFragment
) : RecyclerView.Adapter<SosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):SosAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SosElementBinding.inflate(layoutInflater)
        return ViewHolder(binding, onItemListener)
    }

    override fun onBindViewHolder(holder: SosAdapter.ViewHolder, position: Int) {
        holder.bindData(pets[position])
    }

    override fun getItemCount(): Int {
        return pets.size
    }

    interface OnItemListener {
        fun onItemClick(pet: Pet)
    }

    class ViewHolder(binding: SosElementBinding, onItemListener: OnItemListener) :
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
                tvSOSName.text = item.name
                tvSOSLocation.text = item.age.toString()
                tvSOSDate.text = "Extraviado el : 20/12/2021"
            }
            pet = item
        }
    }
}
