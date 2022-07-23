package com.example.ninosapp.fragments

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ninosapp.Login
import com.example.ninosapp.ReporteExtravio
import com.example.ninosapp.adapter.HomeAdapter
import com.example.ninosapp.databinding.FragmentHomeBinding
import com.example.ninosapp.helper.MyButton
import com.example.ninosapp.helper.MySwipeHelper
import com.example.ninosapp.listener.MybuttonClickListener
import com.example.ninosapp.model.Pet
import com.example.ninosapp.views.AddMember
import com.example.ninosapp.views.MainActivity
import com.example.ninosapp.views.PetDetail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), HomeAdapter.OnItemListener {
    private lateinit var binding: FragmentHomeBinding
    val db = Firebase.firestore
    val datos = ArrayList<Pet>()
    private lateinit var auth: FirebaseAuth
    //private lateinit var listPets: ArrayList<Pet>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        auth = Firebase.auth
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val dbPets = DBPets(requireContext())
        //listPets = dbPets.getPets()

        binding.rvHome.layoutManager = LinearLayoutManager(requireContext())
        datos.clear()

        val swipe = object : MySwipeHelper(requireContext(), rvHome, 200) {
            override fun instantiateMyButton(
                viewHolder: RecyclerView.ViewHolder,
                buffer: MutableList<MyButton>
            ) {
                buffer.add(
                    MyButton(requireContext(), "Reportar extravio", 30,
                        0, Color.RED, object : MybuttonClickListener {
                            override fun onCLick(pos: Int) {
                                val intent = Intent(requireContext(), ReporteExtravio::class.java)
                                val parametros = Bundle()
                                parametros.putSerializable("pet", datos[pos])
                                intent.putExtras(parametros)
                                startActivity(intent)
                            }

                        })
                )
            }

        }

        binding.ibAddNewPetHome.setOnClickListener {
            if (auth.currentUser != null) {
                val intent = Intent(requireContext(), AddMember::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(requireContext(), Login::class.java)
                startActivity(intent)
            }
        }
        binding.btnAddPetHome.setOnClickListener {
            if (auth.currentUser != null) {
                val intent = Intent(requireContext(), AddMember::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(requireContext(), Login::class.java)
                startActivity(intent)
            }
        }
        db.collection("users").document(auth.uid.toString()).collection("mascotas")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "listen:error", e)
                    return@addSnapshotListener
                }
                for (dc in snapshots!!.documentChanges) {
                    when (dc.type) {
                        DocumentChange.Type.ADDED -> {
                            var tmp = Pet(
                                dc.document.data.get("idDuenio") as String,
                                dc.document.data.get("idMascota") as String,
                                dc.document.data.get("nombre") as String,
                                dc.document.data.get("nacimiento") as String,
                                dc.document.data.get("sexo") as Boolean,
                                dc.document.data.get("raza") as String,
                                dc.document.data.get("esterilizado") as Boolean,
                                dc.document.data.get("talla") as String,
                                dc.document.data.get("foto") as String,
                                dc.document.data.get("extraviado") as Boolean
                            )
                            Log.d(ContentValues.TAG, "New city: ${dc.document.data}")
                            print(dc.document.data)
                            datos.add(tmp)
                            if (datos.size == 0) {
                                binding.tvAddNewPet.visibility = View.VISIBLE
                                binding.btnAddPetHome.visibility = View.VISIBLE
                                binding.ivEmptyHome.visibility = View.VISIBLE
                            } else {
                                binding.tvAddNewPet.visibility = View.INVISIBLE
                                binding.btnAddPetHome.visibility = View.INVISIBLE
                                binding.ivEmptyHome.visibility = View.INVISIBLE
                            }
                        }
                        DocumentChange.Type.MODIFIED -> {
                            var tmp = Pet(
                                dc.document.data.get("idDuenio") as String,
                                dc.document.data.get("idMascota") as String,
                                dc.document.data.get("nombre") as String,
                                dc.document.data.get("nacimiento") as String,
                                dc.document.data.get("sexo") as Boolean,
                                dc.document.data.get("raza") as String,
                                dc.document.data.get("esterilizado") as Boolean,
                                dc.document.data.get("talla") as String,
                                dc.document.data.get("foto") as String,
                                dc.document.data.get("extraviado") as Boolean
                            )
                            print(dc.document.data)
                            for (i in datos.indices) {
                                if (datos[i].idMascota == tmp.idMascota) {
                                    datos.set(i, tmp)
                                }
                            }
                            if (datos.size == 0) {
                                binding.tvAddNewPet.visibility = View.VISIBLE
                                binding.btnAddPetHome.visibility = View.VISIBLE
                                binding.ivEmptyHome.visibility = View.VISIBLE
                            } else {
                                binding.tvAddNewPet.visibility = View.INVISIBLE
                                binding.btnAddPetHome.visibility = View.INVISIBLE
                                binding.ivEmptyHome.visibility = View.INVISIBLE
                            }
                        }
                        DocumentChange.Type.REMOVED -> Log.d(
                            ContentValues.TAG,
                            "Removed city: ${dc.document.data}"
                        )
                    }

                }
                binding.rvHome.setHasFixedSize(true)
                val adapter = HomeAdapter(requireContext(), datos, this)
                with(binding) {
                    //recyclerview requiere un layout
                    rvHome.layoutManager = LinearLayoutManager(requireContext())
                    rvHome.adapter = adapter
                }
            }
    }

    override fun onStart() {
        super.onStart()
        if (datos.size == 0 || auth.currentUser == null) {
            binding.tvAddNewPet.visibility = View.VISIBLE
            binding.btnAddPetHome.visibility = View.VISIBLE
            binding.ivEmptyHome.visibility = View.VISIBLE
        } else {
            binding.tvAddNewPet.visibility = View.INVISIBLE
            binding.btnAddPetHome.visibility = View.INVISIBLE
            binding.ivEmptyHome.visibility = View.INVISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        if (datos.size == 0 || auth.currentUser == null) {
            binding.tvAddNewPet.visibility = View.VISIBLE
            binding.btnAddPetHome.visibility = View.VISIBLE
            binding.ivEmptyHome.visibility = View.VISIBLE
        } else {
            binding.tvAddNewPet.visibility = View.INVISIBLE
            binding.btnAddPetHome.visibility = View.INVISIBLE
            binding.ivEmptyHome.visibility = View.INVISIBLE
        }
    }

    override fun onItemClick(pet: Pet) {
        val intent = Intent(requireContext(), PetDetail::class.java)
        val parametros = Bundle()
        parametros.putSerializable("pet", pet)
        intent.putExtras(parametros)
        startActivity(intent)
    }


}