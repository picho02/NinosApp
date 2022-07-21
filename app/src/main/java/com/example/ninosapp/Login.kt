package com.example.ninosapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ninosapp.databinding.ActivityLoginBinding
import com.example.ninosapp.databinding.ActivityMainBinding
import com.example.ninosapp.views.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        btnLogin.setOnClickListener {
            if (validaCampo()){
                auth.signInWithEmailAndPassword(binding.etLoginEmail.text.toString(),binding.etLoginPwd.text.toString()).addOnSuccessListener {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, getString(R.string.Error), Toast.LENGTH_LONG).show()
                }
            }
        }
        btnCancelLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        btnRegistrarse.setOnClickListener {
            startActivity(Intent(this, RegistrarseActivity::class.java))
            finish()
        }
    }

    private fun validaCampo(): Boolean {
        if (binding.etLoginEmail.text.toString() == "") {
            binding.etLoginEmail.error = getString(R.string.valor_requerido)
            binding.etLoginEmail.requestFocus()
        }
        if (binding.etLoginPwd.text.toString() == "") {
            binding.etLoginPwd.error = getString(R.string.valor_requerido)
            binding.etLoginPwd.requestFocus()
        }else if (binding.etLoginPwd.text!!.length  < 6) {
            binding.etLoginPwd.error = "Ingrese una contraseña mayor de 6 digitos"
            binding.etLoginPwd.requestFocus()
        }


        return !(binding.etLoginEmail.text.toString() == "" || binding.etLoginPwd.text.toString() == "" || binding.etLoginPwd.text!!.length  < 6)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}