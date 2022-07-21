package com.example.ninosapp.views

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.ninosapp.R
import com.example.ninosapp.databinding.ActivityLostDetailBinding
import com.example.ninosapp.model.MascotaPerdida
import com.example.ninosapp.model.Pet
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_adoptable_detail.*
import kotlinx.android.synthetic.main.activity_lost_detail.*
import java.io.File

class LostDetail : AppCompatActivity() {
    lateinit var binding: ActivityLostDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        val pet = bundle?.getSerializable("pet") as MascotaPerdida
        with(binding){
            if (pet != null){
                tvLostPetDetailName.text = pet.nombre
                if(pet.sexo){
                    tvLostPetDetailGender.text = "Macho"
                }else{
                    tvLostPetDetailGender.text =  "Hembra"
                }

                tvLostPetDetailBirthday.text = pet.nacimiento
                tvLostPetDetailTalla.text = pet.talla
                tvLostPetDetailBrench.text = pet.raza
                if (pet.esterilizado){
                    tvLostPetDetailEsteril.text = "Si"
                }else{
                    tvLostPetDetailEsteril.text = "No"
                }
                detalle.text= pet.detalles

                val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(pet.foto)
                val localfile = File.createTempFile("tmp","jpg")
                storageRef.getFile(localfile).addOnSuccessListener {
                    val bitMap = BitmapFactory.decodeFile(localfile.absolutePath)
                    foto.setImageBitmap(bitMap)
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
                Toast.makeText(this,"Aqui se pondra una forma de contacto",Toast.LENGTH_LONG).show()
            }

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}