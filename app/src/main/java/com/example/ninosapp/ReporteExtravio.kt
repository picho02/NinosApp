package com.example.ninosapp

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.ninosapp.databinding.ActivityReporteExtravioBinding
import com.example.ninosapp.model.Pet
import com.example.ninosapp.views.AddMember
import com.example.ninosapp.views.MainActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class ReporteExtravio : AppCompatActivity() {
    private lateinit var binding: ActivityReporteExtravioBinding
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        val pet = bundle?.getSerializable("pet") as Pet

        binding = ActivityReporteExtravioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.etFechaExtravio.inputType = InputType.TYPE_NULL
        binding.etFechaExtravio.setOnClickListener {
            val dialogFecha =
                AddMember.DatePickerFragment { year, month, day ->
                    binding.etFechaExtravio.setText("$year/$month/$day")
                }
            dialogFecha.show(supportFragmentManager, "datePicker")
        }
        binding.btnReportar.setOnClickListener {
            if (binding.etLugarExtravio.text.isNotEmpty() && binding.etDetalles.text.isNotEmpty() && binding.etFechaExtravio.text.isNotEmpty() ){
                db.collection("perdidos").document(pet.idMascota)
                    .set(
                        mapOf(
                            "idDuenio" to pet.idDuenio,
                            "idMascota" to pet.idMascota,
                            "nombre" to pet.nombre,
                            "nacimiento" to pet.nacimiento,
                            "raza" to pet.raza,
                            "sexo" to pet.sexo,
                            "talla" to pet.talla,
                            "extraviado" to true,
                            "esterilizado" to pet.esterilizado,
                            "foto" to pet.foto,
                            "fechaExtravio" to binding.etFechaExtravio.text.toString(),
                            "detalles" to binding.etDetalles.text.toString(),
                            "lugarExtravio" to binding.etLugarExtravio.text.toString()
                        )
                    )
                    .addOnSuccessListener {
                        val db2 = Firebase.firestore
                        db2.collection("users").document(pet.idDuenio).collection("mascotas")
                            .document(pet.idMascota)
                            .set(
                                mapOf(
                                    "idDuenio" to pet.idDuenio,
                                    "idMascota" to pet.idMascota,
                                    "nombre" to pet.nombre,
                                    "nacimiento" to pet.nacimiento,
                                    "raza" to pet.raza,
                                    "sexo" to pet.sexo,
                                    "talla" to pet.talla,
                                    "extraviado" to true,
                                    "esterilizado" to pet.esterilizado,
                                    "foto" to pet.foto
                                )
                            )
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }

            }else{
                Toast.makeText(this,"Rellene todos los campos",Toast.LENGTH_LONG).show()
            }
        }
    }
    class DatePickerFragment(val listener: (year: Int, month: Int, day: Int) -> Unit) :
        DialogFragment(), DatePickerDialog.OnDateSetListener {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c = Calendar.getInstance()
            var year = c.get(Calendar.YEAR)
            var month = c.get(Calendar.MONTH)
            var day = c.get(Calendar.DAY_OF_MONTH)
            return DatePickerDialog(requireActivity(), this, year, month, day)
        }

        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
            listener(year, month, day)
        }

    }
}