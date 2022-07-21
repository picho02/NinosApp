package com.example.ninosapp.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ninosapp.R
import com.example.ninosapp.databinding.AdoptItemBinding
import com.example.ninosapp.model.MascotaAdoptable
import com.example.ninosapp.model.Pet
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class AdoptAdapter(
    val pets: ArrayList<MascotaAdoptable>,
    val onItemListener: AdoptAdapter.OnItemListener
    ): RecyclerView.Adapter<AdoptAdapter.ViewHolder>() {
    class ViewHolder(binding: AdoptItemBinding, onItemListener: OnItemListener) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener{
        private val binding = binding
        private val onItemListener = onItemListener
        private lateinit var pet: MascotaAdoptable

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onItemListener.onItemClick(pet)
        }

        fun bindData(item: MascotaAdoptable) {
            with(binding) {
                tvAdoptPetItemName.text = item.nombre
                tvAdoptPetItemOwner.text = item.idDuenio

                if (item.sexo){
                    ivAdoptPetItemGender.setImageResource(R.drawable.ic_male)
                }else{
                    ivAdoptPetItemGender.setImageResource(R.drawable.ic_female)
                }
                val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(item.foto)
                val localfile = File.createTempFile("tmp","jpg")
                storageRef.getFile(localfile).addOnSuccessListener {
                    val bitMap = BitmapFactory.decodeFile(localfile.absolutePath)
                    ivAdoptPetItem.setImageBitmap(bitMap)
                }
            }

            pet = item
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AdoptItemBinding.inflate(layoutInflater)
        return ViewHolder(binding,onItemListener)
    }

    override fun onBindViewHolder(holder: AdoptAdapter.ViewHolder, position: Int) {
        //Picasso.get().load(url).into(holder.cardAdoptImage)

        holder.bindData(pets[position])
    }

    override fun getItemCount(): Int {
        return  pets.size
    }
    interface OnItemListener {
        fun onItemClick(pet: MascotaAdoptable)
    }
}