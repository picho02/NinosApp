package com.example.ninosapp.views

import android.content.ContentValues
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.ninosapp.AgregarDesparacitada
import com.example.ninosapp.AgregarVacuna
import com.example.ninosapp.R
import com.example.ninosapp.adapter.ListaDesparacitadasAdapter
import com.example.ninosapp.databinding.ActivityPetDetailBinding
import com.example.ninosapp.model.Desparacitada
import com.example.ninosapp.model.Pet
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class PetDetail : AppCompatActivity() {
    private lateinit var binding: ActivityPetDetailBinding

    //private lateinit var dbPets: DBPets
    val db = Firebase.firestore
    val datosDesparacitada = ArrayList<Desparacitada>()
    val db2 = Firebase.firestore
    val datosVacuna = ArrayList<Desparacitada>()

    //var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        val pet = bundle?.getSerializable("pet") as Pet
        datosDesparacitada.clear()
        datosVacuna.clear()
        binding = ActivityPetDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ibtnAddDesparacitaciones.setOnClickListener {
            val intent = Intent(this, AgregarDesparacitada::class.java)
            val parametros = Bundle()
            parametros.putSerializable("pet", pet)
            intent.putExtras(parametros)
            startActivity(intent)
        }
        binding.ibtnAddVacunas.setOnClickListener {
            val intent = Intent(this, AgregarVacuna::class.java)
            val parametros = Bundle()
            parametros.putSerializable("pet", pet)
            intent.putExtras(parametros)
            startActivity(intent)
        }
        /*if (savedInstanceState == null){
            val bundle = intent.extras
            if (bundle != null){
                id = bundle.getInt("ID",0)
            }else{
                id = savedInstanceState?.getSerializable("ID") as Int
            }*/
        //dbPets = DBPets(this)
        //pet = dbPets.getPet(id)
        if (pet != null) {
            with(binding) {
                tvPetDetailName.setText(pet?.nombre)
                tvPetDetailBirthday.setText(pet?.nacimiento)
                tvPetDetailBrench.setText(pet?.raza)
                if (pet!!.esterilizado) {
                    tvPetDetailEsteril.setText("Si")
                } else {
                    tvPetDetailEsteril.setText("No")
                }
                if (pet!!.sexo) {
                    tvPetDetailGender.setText("Macho")
                } else {
                    tvPetDetailGender.setText("Hembra")
                }
                val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(pet!!.foto)
                val localfile = File.createTempFile("tmp", "jpg")
                storageRef.getFile(localfile).addOnSuccessListener {
                    val bitMap = BitmapFactory.decodeFile(localfile.absolutePath)
                    fotoMascota.setImageBitmap(bitMap)
                }
                db.collection("users").document(pet.idDuenio).collection("mascotas")
                    .document(pet.idMascota)
                    .collection("desparacitaciones")
                    .addSnapshotListener { snapshots, e ->
                        if (e != null) {
                            Log.w(ContentValues.TAG, "listen:error", e)
                            return@addSnapshotListener
                        }
                        for (dc in snapshots!!.documentChanges) {
                            when (dc.type) {
                                DocumentChange.Type.ADDED -> {
                                    var tmp = Desparacitada(
                                        dc.document.data.get("idDesparacitacion") as String,
                                        dc.document.data.get("tipo") as String,
                                        dc.document.data.get("fechaAplicacion") as String,
                                        dc.document.data.get("fechaRefuerzo") as String
                                    )
                                    Log.d(ContentValues.TAG, "New city: ${dc.document.data}")
                                    print(dc.document.data)
                                    datosDesparacitada.add(tmp)
                                }
                                DocumentChange.Type.MODIFIED -> {
                                    var tmp = Desparacitada(
                                        dc.document.data.get("idDesparacitacion") as String,
                                        dc.document.data.get("tipo") as String,
                                        dc.document.data.get("fechaAplicacion") as String,
                                        dc.document.data.get("fechaRefuerzo") as String
                                    )
                                    print(dc.document.data)
                                    for (i in datosDesparacitada.indices) {
                                        if (datosDesparacitada[i].idDesparacitacion == tmp.idDesparacitacion) {
                                            datosDesparacitada.set(i, tmp)
                                        }
                                    }
                                }
                                DocumentChange.Type.REMOVED -> Log.d(
                                    ContentValues.TAG,
                                    "Removed city: ${dc.document.data}"
                                )
                            }

                        }
                        val adapter = ListaDesparacitadasAdapter(this@PetDetail,datosDesparacitada)
                        listaDesparacitada.adapter = adapter

                    }
                db2.collection("users").document(pet.idDuenio).collection("mascotas")
                    .document(pet.idMascota)
                    .collection("vacunas")
                    .addSnapshotListener { snapshots, e ->
                        if (e != null) {
                            Log.w(ContentValues.TAG, "listen:error", e)
                            return@addSnapshotListener
                        }
                        for (dc in snapshots!!.documentChanges) {
                            when (dc.type) {
                                DocumentChange.Type.ADDED -> {
                                    var tmp = Desparacitada(
                                        dc.document.data.get("idVacuna") as String,
                                        dc.document.data.get("tipo") as String,
                                        dc.document.data.get("fechaAplicacion") as String,
                                        dc.document.data.get("fechaRefuerzo") as String
                                    )
                                    Log.d(ContentValues.TAG, "New city: ${dc.document.data}")
                                    print(dc.document.data)
                                    datosVacuna.add(tmp)
                                }
                                DocumentChange.Type.MODIFIED -> {
                                    var tmp = Desparacitada(
                                        dc.document.data.get("idVacuna") as String,
                                        dc.document.data.get("tipo") as String,
                                        dc.document.data.get("fechaAplicacion") as String,
                                        dc.document.data.get("fechaRefuerzo") as String
                                    )
                                    print(dc.document.data)
                                    for (i in datosVacuna.indices) {
                                        if (datosVacuna[i].idDesparacitacion == tmp.idDesparacitacion) {
                                            datosVacuna.set(i, tmp)
                                        }
                                    }
                                }
                                DocumentChange.Type.REMOVED -> Log.d(
                                    ContentValues.TAG,
                                    "Removed city: ${dc.document.data}"
                                )
                            }

                        }
                        val adapter = ListaDesparacitadasAdapter(this@PetDetail,datosVacuna)
                        listaVacunas.adapter = adapter

                    }

            }

        }

    }

    fun click(view: View) {
        when (view.id) {
            R.id.ibBackAdoptDetail -> {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}