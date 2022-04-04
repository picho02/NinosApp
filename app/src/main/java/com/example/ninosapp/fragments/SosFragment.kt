package com.example.ninosapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ninosapp.R
import com.example.ninosapp.adapter.HomeAdapter
import com.example.ninosapp.adapter.SosAdapter
import com.example.ninosapp.databinding.FragmentHomeBinding
import com.example.ninosapp.databinding.FragmentSosBinding
import com.example.ninosapp.model.Pet
import com.example.ninosapp.views.LostDetail
import com.example.ninosapp.views.PetDetail


class SosFragment :  Fragment(),SosAdapter.OnItemListener {
    private lateinit var binding: FragmentSosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSosBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val datos = ArrayList<Pet>()
        for (i in 1 until 20){
            val petTmp = Pet(i,"Mascota $i",i,"macho","raza $i","si","Dueño $i","talla $i")
            datos.add(petTmp)
        }
        val adapter = SosAdapter(requireContext(),datos,this)
        with(binding){
            //recyclerview requiere un layout
            rvSOS.layoutManager = LinearLayoutManager(requireContext())
            rvSOS.adapter=adapter
        }

    }

    override fun onItemClick(pet: Pet) {
        val intent = Intent(requireContext(), LostDetail::class.java)
        val parametros = Bundle()
        parametros.putSerializable("pet",pet)
        intent.putExtras(parametros)
        startActivity(intent)
    }


}