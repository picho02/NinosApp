package com.example.ninosapp.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ninosapp.R
import com.example.ninosapp.databinding.HomeElementBinding
import com.example.ninosapp.databinding.SosElementBinding
import com.example.ninosapp.fragments.SosFragment
import com.example.ninosapp.model.MascotaPerdida
import com.example.ninosapp.model.Pet
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class SosAdapter(private val context: Context,
                 val pets: ArrayList<MascotaPerdida>,
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
        fun onItemClick(pet: MascotaPerdida)
    }

    class ViewHolder(binding: SosElementBinding, onItemListener: OnItemListener) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        private val binding = binding
        private val onItemListener = onItemListener
        private lateinit var pet: MascotaPerdida

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onItemListener.onItemClick(pet)
        }

        fun bindData(item: MascotaPerdida) {
            with(binding) {
                tvSOSName.text = item.nombre
                tvSOSLocation.text = item.lugarExtravio
                tvSOSDate.text = item.fechaExtravio
                if (item.sexo){
                    sexoItemPerdido.setImageResource(R.drawable.ic_male)
                }else{
                    sexoItemPerdido.setImageResource(R.drawable.ic_female)
                }
                val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(item.foto)
                val localfile = File.createTempFile("tmp","jpg")
                storageRef.getFile(localfile).addOnSuccessListener {
                    val bitMap = BitmapFactory.decodeFile(localfile.absolutePath)
                    fotoItemPerdido.setImageBitmap(bitMap)
                }
            }
            pet = item
        }
    }
}
