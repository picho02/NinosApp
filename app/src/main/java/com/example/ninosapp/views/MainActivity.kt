package com.example.ninosapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.ninosapp.fragments.HomeFragment
import com.example.ninosapp.R
import com.example.ninosapp.fragments.RefugioFragment
import com.example.ninosapp.fragments.SosFragment
import com.example.ninosapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var homeFragment = HomeFragment()
        var sosFragment = SosFragment()
        var refugioFragment = RefugioFragment()
        setCurrentFragment(sosFragment)
        var bottomNavView : BottomNavigationView = binding.bottomNavigationView
        bottomNavView.setOnItemSelectedListener {
            when (it.itemId){
                R.id.nav_home -> setCurrentFragment(homeFragment)
                R.id.nav_adopta -> setCurrentFragment(refugioFragment)
                R.id.nav_oso -> setCurrentFragment(sosFragment)
            }
            true
        }

    }


    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.containerView,fragment)
            commit()
        }
    }
}