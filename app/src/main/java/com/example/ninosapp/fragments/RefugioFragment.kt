package com.example.ninosapp.fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ninosapp.R
import com.example.ninosapp.adapter.AdoptAdapter
import com.example.ninosapp.model.MascotaAdoptable
import com.example.ninosapp.model.Pet
import com.example.ninosapp.views.AdoptableDetail
import com.example.ninosapp.views.MainActivity
import com.example.ninosapp.views.PetDetail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_refugio.*

class RefugioFragment : Fragment(), AdoptAdapter.OnItemListener {
    val db = Firebase.firestore
    val datos = ArrayList<MascotaAdoptable>()
    //var storageRef = Storage.reference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        db.collection("adoptables").addSnapshotListener { snapshots, e ->
            if (e != null) {
                Log.w(TAG, "listen:error", e)
                return@addSnapshotListener
            }
            datos.clear()

            for (dc in snapshots!!.documentChanges) {
                when (dc.type) {
                    DocumentChange.Type.ADDED -> {
                        var tmp = MascotaAdoptable(dc.document.data.get("idDuenio") as String,
                            dc.document.data.get("idMascota") as String,
                            dc.document.data.get("nombre") as String,
                            dc.document.data.get("nacimiento") as String,
                            dc.document.data.get("sexo") as Boolean,
                            dc.document.data.get("raza") as String,
                            dc.document.data.get("esterilizado") as Boolean,
                            dc.document.data.get("talla") as String,
                            dc.document.data.get("foto") as String,
                            dc.document.data.get("detalles") as String,
                            dc.document.data.get("sociable") as String,
                            dc.document.data.get("necesitaEjercicio") as String,
                            dc.document.data.get("energia") as String
                        )
                        Log.d(TAG, "New city: ${dc.document.data}")
                        print(dc.document.data)
                        datos.add(tmp)}
                    DocumentChange.Type.MODIFIED -> Log.d(TAG, "Modified city: ${dc.document.data}")
                    DocumentChange.Type.REMOVED -> Log.d(TAG, "Removed city: ${dc.document.data}")
                }
                val adapter = AdoptAdapter(datos,this)
                val gridLayout = GridLayoutManager(requireContext(),2)
                rvAdopt.layoutManager =gridLayout
                rvAdopt.adapter = adapter
            }
        }
        return inflater.inflate(R.layout.fragment_refugio, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*for (i in 1 until 20){
            val petTmp = Pet(i,"Mascota $i",i,"macho","raza $i","si","Dueño $i","talla $i")
            datos.add(petTmp)
        }*/



    }

    override fun onItemClick(pet: MascotaAdoptable) {
        val intent = Intent(requireContext(), AdoptableDetail::class.java)
        val parametros = Bundle()
        parametros.putSerializable("pet",pet)
        intent.putExtras(parametros)
        startActivity(intent)
    }

}