package com.example.lostandfound.Activities

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.lostandfound.R
import com.example.lostandfound.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addfragment(homescreenFragment(), 0)


        binding.bottomnavigationBar.itemIconTintList = null

        binding.bottomnavigationBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home123 -> addfragment(homescreenFragment(), 1)
                R.id.lost123 -> addfragment(LostScreenFragment(), 1)
                R.id.found123 -> addfragment(FoundScreenFragment(), 1)

                else -> {


                }


            }
            true


        }


    }


    private fun addfragment(fragments: Fragment, Flags: Int) {
        // this is a service call
        val fragmentmanager = supportFragmentManager
        val fragmentTransaction = fragmentmanager.beginTransaction()
        if (Flags == 1) {
            fragmentTransaction.replace(R.id.framelayout, fragments)
//
        } else {
            fragmentTransaction.add(R.id.framelayout, fragments)

        }

         fragmentTransaction.commit()
    }

    override fun onBackPressed() {

        if (binding.bottomnavigationBar.selectedItemId == R.id.home123  ) {
            super.onBackPressed()
            this.finishAffinity()


        } else {
            binding.bottomnavigationBar.selectedItemId = R.id.home123
        }

    }


}


