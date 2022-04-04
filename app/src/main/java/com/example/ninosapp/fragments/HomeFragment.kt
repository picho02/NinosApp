package com.example.ninosapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ninosapp.R
import com.example.ninosapp.adapter.HomeAdapter
import com.example.ninosapp.databinding.FragmentHomeBinding
import com.example.ninosapp.db.DBPets
import com.example.ninosapp.model.Pet
import com.example.ninosapp.views.AddMember
import com.example.ninosapp.views.MainActivity
import com.example.ninosapp.views.PetDetail


class HomeFragment : Fragment(),HomeAdapter.OnItemListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var listPets: ArrayList<Pet>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dbPets = DBPets(requireContext())
        listPets = dbPets.getPets()
        if (listPets.size == 0){
            binding.tvAddNewPet.visibility = View.VISIBLE
            binding.btnAddPetHome.visibility = View.VISIBLE
            binding.ivEmptyHome.visibility = View.VISIBLE
        }else{
            binding.tvAddNewPet.visibility = View.INVISIBLE
            binding.btnAddPetHome.visibility = View.INVISIBLE
            binding.ivEmptyHome.visibility = View.INVISIBLE
        }
        binding.btnAddPetHome.setOnClickListener {
            val intent = Intent(requireContext(), AddMember::class.java)
            startActivity(intent)
        }
        binding.ibAddNewPetHome.setOnClickListener{
            val intent = Intent(requireContext(), AddMember::class.java)
            startActivity(intent)
        }


        val adapter = HomeAdapter(requireContext(),listPets,this)
        with(binding){
            //recyclerview requiere un layout
            rvHome.layoutManager = LinearLayoutManager(requireContext())
            rvHome.adapter=adapter
        }

    }

    override fun onItemClick(pet: Pet) {
        val intent = Intent(requireContext(), PetDetail::class.java)
        intent.putExtra("ID",pet.id)
        startActivity(intent)
    }


}