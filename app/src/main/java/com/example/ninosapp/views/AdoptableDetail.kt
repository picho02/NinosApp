package com.example.ninosapp.views

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.ninosapp.R
import com.example.ninosapp.databinding.ActivityAdoptableDetailBinding
import com.example.ninosapp.model.MascotaAdoptable
import com.example.ninosapp.model.Pet
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.sos_element.*
import java.io.File


class AdoptableDetail : AppCompatActivity() {
    lateinit var binding: ActivityAdoptableDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdoptableDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        val pet = bundle?.getSerializable("pet") as MascotaAdoptable
        with(binding){
            if (pet != null){
                tvPetAdoptableName.text = pet.nombre
                if (pet.sexo){
                    tvPetAdoptableGender.text = "Macho"
                }else {
                    tvPetAdoptableGender.text = "Hembra"
                }
                tvPetAdoptableTalla.text = pet.talla
                tvPetAdoptableBrench.text = pet.raza
                if(pet.esterilizado){
                    tvPetAdoptableEsteril.text = "Si"
                }else{
                    tvPetAdoptableEsteril.text = "No"
                }
                tvPetAdoptableOwner.text = pet.idDuenio
                tvPetAdoptableAge.text = pet.nacimiento
                detalleAdoptable.text = pet.detalles
                energiaTV.text = "Energía: ${pet.energia}%"
                ejercicioTV.text = "Necesita ejercicio: ${pet.necesitaEjercicio}"
                sociableTV.text = "Sociable ${pet.sociable}"
                val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(pet.foto)
                val localfile = File.createTempFile("tmp","jpg")
                storageRef.getFile(localfile).addOnSuccessListener {
                    val bitMap = BitmapFactory.decodeFile(localfile.absolutePath)
                    fotoAdoptable.setImageBitmap(bitMap)
                }
            }
        }

    }
    fun click(view: View) {
        when (view.id) {

            R.id.ibBackAdoptDetail -> {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.btnAdopt -> {
                Toast.makeText(this,"Aqui se pondra una forma de contacto", Toast.LENGTH_LONG).show()
            }

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}