package com.example.lostandfound.Activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import com.example.lostandfound.R

import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.*
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


open class baseactivity : AppCompatActivity() {


    private lateinit var  mprogressdialog: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }



    fun showprogressdialog(Text:String){
        mprogressdialog=Dialog(this)
        mprogressdialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mprogressdialog.setContentView(R.layout.dialog_progress)
        mprogressdialog.setCancelable(false)
        mprogressdialog.findViewById<TextView>(R.id.tv_progress_text).text=Text

        mprogressdialog.show()
        Handler().postDelayed({},2000)


    }
    fun hideprogressdialog()
    {
        mprogressdialog.dismiss()
    }


    fun showerrorsnackbar(message:String){
        // gives the root element of a view without actually knowing its id
    val snackbar =Snackbar.make(findViewById(android.R.id.content),message,Snackbar.LENGTH_LONG)
        val snackbarview=snackbar.view
    snackbarview.setBackgroundColor(getColor(this,R.color.snackbar_error_color))
    snackbar.show()

    }



}