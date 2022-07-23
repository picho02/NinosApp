package com.example.ninosapp

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
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
import com.example.ninosapp.databinding.ActivityAgregarDesparacitadaBinding
import com.example.ninosapp.model.Pet
import com.example.ninosapp.views.AddMember
import com.example.ninosapp.views.MainActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_member.*
import java.util.*
import kotlin.collections.ArrayList

class AgregarDesparacitada : AppCompatActivity() {
    private lateinit var binding: ActivityAgregarDesparacitadaBinding
    private var spinnerTypePosition: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarDesparacitadaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        val pet = bundle?.getSerializable("pet") as Pet
        val listaOpcionesDesparacitadas= resources.getStringArray(R.array.tipo_desparacitada)
        val tiposDesparacitadas = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item, listaOpcionesDesparacitadas
        )
        binding.spinnerTipoDesparacitada.adapter = tiposDesparacitadas
        binding.spinnerTipoDesparacitada.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                p0: AdapterView<*>?,
                p1: View?,
                posicion: Int,
                p3: Long
            ) {
                if (posicion != 0)
                    spinnerTypePosition = listaOpcionesDesparacitadas[posicion]

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        binding.etDesparacitadaAplicacion.inputType = InputType.TYPE_NULL
        binding.etDesparacitadaAplicacion.setOnClickListener{
            val dialogFecha =
                AddMember.DatePickerFragment { year, month, day ->
                    binding.etDesparacitadaAplicacion.setText("$day $month $year")
                }
            dialogFecha.show(supportFragmentManager, "datePicker")
        }
        binding.etDesparacitadaRefuerzo.inputType = InputType.TYPE_NULL
        binding.etDesparacitadaRefuerzo.setOnClickListener{
            val dialogFecha =
                AddMember.DatePickerFragment { year, month, day ->
                    binding.etDesparacitadaRefuerzo.setText("$day $month $year")
                }
            dialogFecha.show(supportFragmentManager, "datePicker")
        }
        binding.btnCancelAddDesparacitada.setOnClickListener{
            finish()
        }
        binding.btnAceptarAddDesparecitada.setOnClickListener {
            if (validaDatos()){
                val db = Firebase.firestore
                val fechaAplicacion = binding.etDesparacitadaAplicacion.text.toString()
                val idDesparacitada = "$fechaAplicacion${spinnerTypePosition}"
                db.collection("users").document(pet.idDuenio).collection("mascotas")
                    .document(pet.idMascota).collection("desparacitaciones").document(idDesparacitada)
                    .set(
                        mapOf(
                            "idDesparacitacion" to idDesparacitada,
                            "tipo" to spinnerTypePosition,
                            "fechaAplicacion" to binding.etDesparacitadaAplicacion.text.toString(),
                            "fechaRefuerzo" to binding.etDesparacitadaRefuerzo.text.toString()
                        )
                    )
                    .addOnSuccessListener {
                        finish()
                    }
            }else{
                Toast.makeText(this,"Rellene todos los datos",Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)

    }

    private fun validaDatos(): Boolean {
        return binding.etDesparacitadaRefuerzo.text.isNotEmpty() && binding.etDesparacitadaAplicacion.text.isNotEmpty()
                && spinnerTypePosition.isNotEmpty() && spinnerTypePosition != "Seleccione el tipo de desparacitación"
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