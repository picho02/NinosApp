package com.example.ninosapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.ninosapp.R
import com.example.ninosapp.databinding.ActivityAdoptableDetailBinding
import com.example.ninosapp.model.Pet

class AdoptableDetail : AppCompatActivity() {
    lateinit var binding: ActivityAdoptableDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdoptableDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        val pet = bundle?.getSerializable("pet") as Pet
        with(binding){
            if (pet != null){
                tvPetAdoptableName.text = pet.name
                tvPetAdoptableGender.text = pet.gender
                tvPetAdoptableTalla.text = pet.talla
                tvPetAdoptableBrench.text = pet.brench
                tvPetAdoptableEsteril.text = pet.esteril
                tvPetAdoptableOwner.text = pet.owner
                tvPetAdoptableAge.text = pet.age.toString()
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