package com.example.ninosapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ninosapp.R
import com.example.ninosapp.adapter.AdoptAdapter
import com.example.ninosapp.model.Pet
import com.example.ninosapp.views.MainActivity
import kotlinx.android.synthetic.main.fragment_refugio.*

class RefugioFragment : Fragment(R.layout.fragment_refugio) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_refugio, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val datos = ArrayList<Pet>()
        for (i in 1 until 20){
            val petTmp = Pet(i,"Mascota $i",i,"macho","raza $i","si","Dueño $i","talla $i")
            datos.add(petTmp)
        }
        val adapter = AdoptAdapter(datos)
        val gridLayout = GridLayoutManager(requireContext(),2)
        rvAdopt.layoutManager =gridLayout
        rvAdopt.adapter = adapter
    }

}