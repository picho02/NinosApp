package com.example.ninosapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.ninosapp.R
import com.example.ninosapp.databinding.ActivityLostDetailBinding
import com.example.ninosapp.db.DBPets
import com.example.ninosapp.model.Pet
import kotlinx.android.synthetic.main.sos_element.*

class LostDetail : AppCompatActivity() {
    lateinit var binding: ActivityLostDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        val pet = bundle?.getSerializable("pet") as Pet
        with(binding){
            if (pet != null){
                tvLostPetDetailName.text = pet.name
                tvLostPetDetailGender.text = pet.gender
                tvLostPetDetailBirthday.text = "20/12/2021"
                tvLostPetDetailTalla.text = pet.talla
                tvLostPetDetailBrench.text = pet.brench
                tvLostPetDetailEsteril.text = pet.esteril
            }
        }

    }
    fun click(view: View) {
        when (view.id) {

            R.id.ibBackPetDetail -> {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.btnLostPetOwner -> {
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