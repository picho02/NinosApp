package com.example.ninosapp

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.ninosapp.databinding.ActivityAgregarVacunaBinding
import com.example.ninosapp.model.Pet
import com.example.ninosapp.views.AddMember
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class AgregarVacuna : AppCompatActivity() {
    private lateinit var binding: ActivityAgregarVacunaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarVacunaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        val pet = bundle?.getSerializable("pet") as Pet
        val listaOpcionesDesparacitadas= resources.getStringArray(R.array.tipo_desparacitada)
        val tiposDesparacitadas = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item, listaOpcionesDesparacitadas
        )
        binding.vacunaAplicacion.inputType = InputType.TYPE_NULL
        binding.vacunaAplicacion.setOnClickListener{
            val dialogFecha =
                AddMember.DatePickerFragment { year, month, day ->
                    binding.vacunaAplicacion.setText("$day $month $year")
                }
            dialogFecha.show(supportFragmentManager, "datePicker")
        }
        binding.vacunaRefuerzo.inputType = InputType.TYPE_NULL
        binding.vacunaRefuerzo.setOnClickListener{
            val dialogFecha =
                AddMember.DatePickerFragment { year, month, day ->
                    binding.vacunaRefuerzo.setText("$day $month $year")
                }
            dialogFecha.show(supportFragmentManager, "datePicker")
        }
        binding.btnCancelAddDesparacitada.setOnClickListener{
            finish()
        }
        binding.btnAceptarAddDesparecitada.setOnClickListener {
            if (validaDatos()){
                val db = Firebase.firestore
                val fechaAplicacion = binding.vacunaAplicacion.text.toString()
                val idVacuna = "$fechaAplicacion${binding.etVacuna.text}"
                db.collection("users").document(pet.idDuenio).collection("mascotas")
                    .document(pet.idMascota).collection("vacunas").document(idVacuna)
                    .set(
                        mapOf(
                            "idVacuna" to idVacuna,
                            "tipo" to binding.etVacuna.text.toString(),
                            "fechaAplicacion" to binding.vacunaAplicacion.text.toString(),
                            "fechaRefuerzo" to binding.vacunaRefuerzo.text.toString()
                        )
                    )
                    .addOnSuccessListener {
                        finish()
                    }
            }else{
                Toast.makeText(this,"Rellene todos los datos", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)

    }

    private fun validaDatos(): Boolean {
        return binding.vacunaRefuerzo.text.isNotEmpty() && binding.vacunaAplicacion.text.isNotEmpty()
                && binding.etVacuna.text.isNotEmpty()
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