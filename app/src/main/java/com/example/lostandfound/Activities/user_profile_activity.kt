package com.example.lostandfound.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.lostandfound.R

class user_profile_activity : AppCompatActivity() {
     private lateinit var imageview:ImageView
     private lateinit var name:TextView
     private lateinit var email:TextView
     private lateinit var mob_no:TextView
     private var imageurl:String=""
    private var nameurl:String=""
    private var emailurl:String=""
    private var moburl:String=""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
         setupActionBar()
     imageview=findViewById(R.id.user_image)
     name=findViewById(R.id.user_profile_name)
     email=findViewById(R.id.user_profile_email)
     mob_no=findViewById(R.id.user_profile_no)
        imageurl=intent.getStringExtra("url_lost_profile_url")!!
        nameurl=intent.getStringExtra("url_lost_profile_name")!!
        emailurl=intent.getStringExtra("url_lost_profile_email")!!
        moburl=intent.getStringExtra("url_lost_profile_mob")!!


        Glide.with(applicationContext).load(imageurl).centerCrop().placeholder(R.drawable.my_profile_image1).into(imageview)
        name.text=nameurl
        email.text=emailurl
        mob_no.text=moburl





    }
    fun setupActionBar() {
        val toolbar123 = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_user_profile)
        setSupportActionBar(toolbar123)
        val actionbar = supportActionBar
        actionbar?.title="User Profile"
        toolbar123.setTitleTextColor(resources.getColor(R.color.black))
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24)
        }
        toolbar123.setNavigationOnClickListener {
            onBackPressed()

        }
    }
}