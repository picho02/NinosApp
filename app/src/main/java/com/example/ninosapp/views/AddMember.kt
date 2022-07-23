package com.example.ninosapp.views

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.ninosapp.R
import com.example.ninosapp.databinding.ActivityAddMemberBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_add_member.*
import java.io.ByteArrayOutputStream
import java.util.*

class AddMember : AppCompatActivity() {
    private val GALERIA_REQUEST_CODE: Int = 22
    private val PERMISO_GALERIA: Int = 98
    private val CAMARA_REQUEST_CODE: Int = 23
    private val PERMISO_CAMARA: Int = 99
    private lateinit var binding: ActivityAddMemberBinding
    private var spinnerGenderPosition: String = ""
    private var spinnerEsterilPosition: String = ""
    private var spinnerTallaPosition: String = ""
    val db = Firebase.firestore
    val auth = Firebase.auth
    val storage = Firebase.storage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnCamera.setOnClickListener {
            solicitarPermisos()
        }
        binding.btnGaleria.setOnClickListener {
            solicitarPermisosGaleria()
        }
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
            etAge.inputType = InputType.TYPE_NULL
            etAge.setOnClickListener {
                val dialogFecha =
                    DatePickerFragment { year, month, day -> mostrarResultado(year, month, day) }
                dialogFecha.show(supportFragmentManager, "datePicker")

            }
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

    private fun solicitarPermisos() {
        when {
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED -> {
                tomarFoto()
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) -> {
                mostrarMensajes("Permiso rechazado autorizar en ajustes")
            }
            else -> {
                requestPermissions(arrayOf(android.Manifest.permission.CAMERA), PERMISO_CAMARA)
            }
        }
    }

    private fun solicitarPermisosGaleria() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
                    == PackageManager.PERMISSION_GRANTED -> {
                abrirGaleria()
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                mostrarMensajes("Permiso rechazado autorizar en ajustes")
            }
            else -> {
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISO_GALERIA
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISO_CAMARA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    tomarFoto()
                }
            }
            PERMISO_GALERIA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    abrirGaleria()
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }

    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALERIA_REQUEST_CODE)
    }

    private fun tomarFoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMARA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CAMARA_REQUEST_CODE -> {
                if (resultCode != Activity.RESULT_OK) {
                    mostrarMensajes("No se tomo foto")
                } else {
                    val bitMap: Bitmap = data?.extras?.get("data") as Bitmap
                    binding.iVAddPet.setImageBitmap(bitMap)
                }
            }
            GALERIA_REQUEST_CODE -> {
                if (resultCode != Activity.RESULT_OK) {
                    mostrarMensajes("No se selecciono imagen")
                } else {
                    binding.iVAddPet.setImageURI(data?.data)
                }
            }
        }
    }

    fun mostrarMensajes(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    private fun mostrarResultado(year: Int, month: Int, day: Int) {
        binding.etAge.setText("$year/$month/$day")


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

                        var esterilizado = spinnerEsterilPosition == "Si"
                        var sexo = spinnerGenderPosition == "Macho"
                        val idDuenio = auth.uid.toString()
                        val nombreMascota = etPetName.text.toString()
                        val idMascota = "$idDuenio$nombreMascota"
                        val storageReference = FirebaseStorage.getInstance().getReference()
                        val ref = storageReference.child("$idMascota.png")

                        // Get the data from an ImageView as bytes
                        binding.iVAddPet.isDrawingCacheEnabled = true
                        binding.iVAddPet.buildDrawingCache()
                        val bitmap = (binding.iVAddPet.drawable as BitmapDrawable).bitmap
                        val baos = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                        val data = baos.toByteArray()

                        var uploadTask = ref.putBytes(data)
                        uploadTask.addOnFailureListener {
                            // Handle unsuccessful uploads
                        }.addOnSuccessListener { taskSnapshot ->
                            val urlTask = uploadTask.continueWithTask { task ->
                                if (!task.isSuccessful) {
                                    task.exception?.let {
                                        throw it
                                    }
                                }
                                ref.downloadUrl
                            }.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val downloadUri = task.result
                                    db.collection("users").document(idDuenio).collection("mascotas")
                                        .document(idMascota)
                                        .set(
                                            mapOf(
                                                "idDuenio" to idDuenio,
                                                "idMascota" to idMascota,
                                                "nombre" to nombreMascota,
                                                "nacimiento" to etAge.text.toString(),
                                                "raza" to etBrench.text.toString(),
                                                "sexo" to sexo,
                                                "talla" to spinnerTallaPosition,
                                                "extraviado" to false,
                                                "esterilizado" to esterilizado,
                                                "foto" to downloadUri
                                            )
                                        )
                                        .addOnSuccessListener {
                                            startActivity(Intent(this, MainActivity::class.java))
                                            finish()
                                        }
                                } else {
                                    // Handle failures
                                    // ...
                                }
                            }

                        }



                        /*
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
                        }*/
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