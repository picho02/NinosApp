package com.example.ninosapp.views

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.ninosapp.Login
import com.example.ninosapp.fragments.HomeFragment
import com.example.ninosapp.R
import com.example.ninosapp.databinding.ActivityMainBinding
import com.example.ninosapp.fragments.NoInternet
import com.example.ninosapp.fragments.RefugioFragment
import com.example.ninosapp.fragments.SosFragment
import com.example.ninosapp.model.NetworkConnection
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var homeFragment = HomeFragment()
        var sosFragment = SosFragment()
        var refugioFragment = RefugioFragment()
        var noInternetFragment = NoInternet()
        var currentFragment = sosFragment


        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected ->
            if (isConnected){
                binding.bottomNavigationView.isVisible = true
                binding.logBtn.isVisible = true
                setCurrentFragment(currentFragment)


                var bottomNavView : BottomNavigationView = binding.bottomNavigationView
                bottomNavView.setOnItemSelectedListener {
                    when (it.itemId){
                        R.id.nav_home -> setCurrentFragment(homeFragment)
                        R.id.nav_adopta -> setCurrentFragment(refugioFragment)

                        R.id.nav_oso ->setCurrentFragment(sosFragment)
                    }
                    true
                }
            }else{
                //no hay
                Toast.makeText(this,"No hay internet",Toast.LENGTH_LONG).show()
                binding.bottomNavigationView.isVisible = false
                binding.logBtn.isVisible = false
                setCurrentFragment(noInternetFragment)
            }
        })

        binding.logBtn.setOnClickListener {
            val currentUser = auth.currentUser
            if (currentUser != null){
                AlertDialog.Builder(this).setTitle("Cerrar sesión").setMessage("¿Quiere cerrar la sesión?")
                    .setPositiveButton("Salir",DialogInterface.OnClickListener { dialogInterface, i ->
                        auth.signOut()
                        binding.logBtn.setImageResource(R.drawable.ic_login)
                    }).setNegativeButton("No",DialogInterface.OnClickListener { dialogInterface, i ->

                    }).show()
            }else{
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)

    }


    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.containerView,fragment)
            commit()
        }
    }
    fun updateUI(currentUser: FirebaseUser?){
        if (currentUser != null){
            binding.logBtn.setImageResource(R.drawable.ic_logout)
        }else{
            binding.logBtn.setImageResource(R.drawable.ic_login)
        }
    }

}