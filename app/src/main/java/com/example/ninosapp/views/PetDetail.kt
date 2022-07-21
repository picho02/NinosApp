package com.example.ninosapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.ninosapp.R
import com.example.ninosapp.databinding.ActivityPetDetailBinding
import com.example.ninosapp.db.DBPets
import com.example.ninosapp.model.Pet

class PetDetail : AppCompatActivity() {
    private lateinit var binding: ActivityPetDetailBinding
    private lateinit var dbPets: DBPets
    var pet: Pet? = null
    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null){
            val bundle = intent.extras
            if (bundle != null){
                id = bundle.getInt("ID",0)
            }else{
                id = savedInstanceState?.getSerializable("ID") as Int
            }
            dbPets = DBPets(this)
            /*pet = dbPets.getPet(id)
            if (pet != null){
                with(binding){
                    tvPetDetailName.setText(pet?.name)
                    tvPetDetailBirthday.setText(pet?.age.toString())
                    tvPetDetailBrench.setText(pet?.brench)
                    tvPetDetailEsteril.setText(pet?.esteril)
                    tvPetDetailGender.setText(pet?.gender)
                }
            }*/
        }
    }
    fun click(view: View) {
        when(view.id){
            R.id.ibBackAdoptDetail->{
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}