package com.example.ninosapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ninosapp.databinding.ActivityRegistrarseBinding
import com.example.ninosapp.views.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registrarse.*

class RegistrarseActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistrarseBinding
    private lateinit var auth: FirebaseAuth
    var nombre = ""
    var apellidoPaterno = ""
    var apellidoMaterno = ""
    var email = ""
    var pwd = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        btnAceptarRegistro.setOnClickListener {
            if (validaCampo()) {
                if (pwd.length >= 6){
                    auth.createUserWithEmailAndPassword(email,pwd).addOnSuccessListener {
                        val db = Firebase.firestore

                        db.collection("users").document(it.user!!.uid).set(
                            mapOf(
                                "nombre" to nombre,
                                "apellidoPaterno" to apellidoPaterno,
                                "apellidoMaterno" to apellidoMaterno,
                                "id" to it.user!!.uid
                            )
                        ).addOnSuccessListener {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }
                } else{
                    Toast.makeText(this,"La contraseña debe tener mas de 6 caracteres", Toast.LENGTH_LONG).show()
                }

            }else{
                Toast.makeText(this,"Rellena todos los campos", Toast.LENGTH_LONG).show()
            }
        }
        btnCancelRegistro.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }

    }

    private fun validaCampo(): Boolean {
        nombre = binding.etnombre.text.toString()
        apellidoPaterno = binding.etApellidoPaterno.text.toString()
        apellidoMaterno = binding.etapellidoMaterno.text.toString()
        email = binding.etRegistroEmail.text.toString()
        pwd = binding.etRegistroPwd.text.toString()
        return nombre.isNotEmpty() && apellidoMaterno.isNotEmpty() && apellidoPaterno.isNotEmpty() && email.isNotEmpty() && pwd.isNotEmpty()

    }
}