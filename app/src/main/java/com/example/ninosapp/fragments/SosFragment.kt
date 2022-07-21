package com.example.ninosapp.fragments

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ninosapp.R
import com.example.ninosapp.adapter.AdoptAdapter
import com.example.ninosapp.adapter.HomeAdapter
import com.example.ninosapp.adapter.SosAdapter
import com.example.ninosapp.databinding.FragmentHomeBinding
import com.example.ninosapp.databinding.FragmentSosBinding
import com.example.ninosapp.model.MascotaAdoptable
import com.example.ninosapp.model.MascotaPerdida
import com.example.ninosapp.model.Pet
import com.example.ninosapp.views.LostDetail
import com.example.ninosapp.views.PetDetail
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_refugio.*
import kotlinx.android.synthetic.main.fragment_sos.*


class SosFragment :  Fragment(),SosAdapter.OnItemListener {
    private lateinit var binding: FragmentSosBinding
    val db = Firebase.firestore
    val datos = ArrayList<MascotaPerdida>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSosBinding.inflate(inflater,container,false)
        db.collection("perdidos").addSnapshotListener { snapshots, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "listen:error", e)
                return@addSnapshotListener
            }
            datos.clear()

            for (dc in snapshots!!.documentChanges) {
                when (dc.type) {
                    DocumentChange.Type.ADDED -> {
                        var tmp = MascotaPerdida(dc.document.data.get("idDuenio") as String,
                            dc.document.data.get("idMascota") as String,
                            dc.document.data.get("nombre") as String,
                            dc.document.data.get("nacimiento") as String,
                            dc.document.data.get("sexo") as Boolean,
                            dc.document.data.get("raza") as String,
                            dc.document.data.get("esterilizado") as Boolean,
                            dc.document.data.get("talla") as String,
                            dc.document.data.get("foto") as String,
                            dc.document.data.get("detalles") as String,
                            dc.document.data.get("lugarExtravio") as String,
                            dc.document.data.get("fechaExtravio") as String,
                            dc.document.data.get("extraviado") as Boolean
                        )
                        Log.d(ContentValues.TAG, "New city: ${dc.document.data}")
                        print(dc.document.data)
                        datos.add(tmp)}
                    DocumentChange.Type.MODIFIED -> Log.d(ContentValues.TAG, "Modified city: ${dc.document.data}")
                    DocumentChange.Type.REMOVED -> Log.d(ContentValues.TAG, "Removed city: ${dc.document.data}")
                }
                val adapter = SosAdapter(requireContext(),datos,this)
                rvSOS.layoutManager = LinearLayoutManager(requireContext())
                rvSOS.adapter=adapter
            }
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onItemClick(pet: MascotaPerdida) {
        val intent = Intent(requireContext(), LostDetail::class.java)
        val parametros = Bundle()
        parametros.putSerializable("pet",pet)
        intent.putExtras(parametros)
        startActivity(intent)
    }


}