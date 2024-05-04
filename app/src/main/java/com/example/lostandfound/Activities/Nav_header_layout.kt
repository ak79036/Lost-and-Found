package com.example.lostandfound.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.lostandfound.R
import com.example.lostandfound.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Nav_header_layout : AppCompatActivity() {
    private var mauth=FirebaseAuth.getInstance()
    private lateinit var mdatabasereference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nav_header_layout)
        val headername:TextView=findViewById(R.id.header_username)





    }
}