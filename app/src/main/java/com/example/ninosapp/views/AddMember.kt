package com.example.ninosapp.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ninosapp.R
import com.example.ninosapp.databinding.ActivityAddMemberBinding
import com.example.ninosapp.db.DBPets

class AddMember : AppCompatActivity() {
    private lateinit var binding: ActivityAddMemberBinding
    private var spinnerGenderPosition: String = ""
    private var spinnerEsterilPosition: String = ""
    private var spinnerTallaPosition: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val listaOpcionesgender = resources.getStringArray(R.array.gender_array)
        val listaOpcionesEsteril = resources.getStringArray(R.array.esteril_array)
        val listaOpcionesTalla = resources.getStringArray(R.array.talla_array)
        val generos = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item, listaOpcionesgender
        )
        val esteril = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item, listaOpcionesEsteril
        )
        val talla = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item, listaOpcionesTalla
        )


        with(binding) {
            spGender.adapter = generos
            spGender.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    posicion: Int,
                    p3: Long
                ) {
                    if (posicion != 0)
                        spinnerGenderPosition = listaOpcionesgender[posicion]

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
            spEsteril.adapter = esteril
            spEsteril.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    posicion: Int,
                    p3: Long
                ) {
                    if (posicion != 0)
                        spinnerEsterilPosition = listaOpcionesEsteril[posicion]

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
            spTalla.adapter = talla
            spTalla.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    posicion: Int,
                    p3: Long
                ) {
                    if (posicion != 0)
                        spinnerTallaPosition = listaOpcionesTalla[posicion]

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }

        }
    }

    private fun validaCampo(): Boolean {
        if (binding.etPetName.text.toString() == "") {
            binding.etPetName.error = getString(R.string.valor_requerido)
            binding.etPetName.requestFocus()
        }
        if (binding.etBrench.text.toString() == "") {
            binding.etBrench.error = getString(R.string.valor_requerido)
            binding.etBrench.requestFocus()
        }
        if (binding.etAge.text.toString() == "") {
            binding.etAge.error = getString(R.string.valor_requerido)
            binding.etAge.requestFocus()
        }

        return !(binding.etPetName.text.toString() == "" || binding.etBrench.text.toString() == "" || binding.etAge.text.toString() == "")
    }

    fun click(view: View) {
        when (view.id) {
            R.id.btnAdopt -> {
                if (validaCampo()) {
                    if (spinnerGenderPosition == "") {
                        Toast.makeText(this, getString(R.string.error_spgender), Toast.LENGTH_LONG)
                            .show()
                    } else if (spinnerEsterilPosition == "") {
                        Toast.makeText(this, getString(R.string.error_spesteril), Toast.LENGTH_LONG)
                            .show()
                    } else if (spinnerTallaPosition == "") {
                        Toast.makeText(this, getString(R.string.error_sptalla), Toast.LENGTH_LONG)
                            .show()
                    } else {
                        val dbPets = DBPets(this)
                        with(binding) {
                            val id = dbPets.insertPet(
                                etPetName.text.toString(),
                                etAge.text.toString().toInt(),
                                spinnerGenderPosition,
                                etBrench.text.toString(),
                                spinnerEsterilPosition,
                                "Usuario",
                                spinnerTallaPosition
                            )
                            if (id > 0) {
                                Toast.makeText(
                                    this@AddMember,
                                    getString(R.string.guardado),
                                    Toast.LENGTH_LONG
                                ).show()

                            } else {
                                Toast.makeText(
                                    this@AddMember,
                                    getString(R.string.Error),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            intent = Intent(this@AddMember, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
            R.id.btnCancelAddPet -> {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
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