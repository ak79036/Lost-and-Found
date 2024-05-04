package com.example.lostandfound.Activities

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.example.lostandfound.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
         val typeFace:Typeface= Typeface.createFromAsset(assets,"carbon bl.otf")
         val tvsplash:TextView=findViewById(R.id.tv_splash)
        tvsplash.typeface=typeFace
        Handler().postDelayed(
            {
                startActivity(Intent(this, IntroActivity12::class.java))
                finish()
            },
            1000,
        )

    }
}