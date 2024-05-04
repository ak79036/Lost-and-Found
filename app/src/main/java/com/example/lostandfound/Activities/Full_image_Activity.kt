package com.example.lostandfound.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.lostandfound.R

class Full_image_Activity : AppCompatActivity() {
    private  lateinit var ImageView:ImageView
    private var imageurl:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_image)
      setupActionBar()


        ImageView=findViewById(R.id.full_image)
        imageurl= intent.getStringExtra("url_lost")!!


        Glide.with(applicationContext).load(imageurl).placeholder(R.drawable.baseline_sensor_occupied_24).into(ImageView)

    }
    fun setupActionBar() {
        val toolbar123 = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_full_image)
        setSupportActionBar(toolbar123)
        val actionbar = supportActionBar
        actionbar?.title="Full Image"
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