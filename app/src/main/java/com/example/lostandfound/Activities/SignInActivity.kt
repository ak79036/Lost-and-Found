package com.example.lostandfound.Activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar

import com.example.lostandfound.R
import com.example.lostandfound.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.snapshots

open class SignInActivity :baseactivity() {
   private lateinit var auth:FirebaseAuth
   private lateinit var mdatabaseref:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        setupactionbar1()
        auth= FirebaseAuth.getInstance()
        findViewById<Button>(R.id.btn_signin1).setOnClickListener {
            signinregistereduser()
        }
        val btn_signin = findViewById<View>(R.id.intentsignin)
        btn_signin.setOnClickListener{
            startActivity(Intent(this,SignUpActivity::class.java))

        }

     mdatabaseref=FirebaseDatabase.getInstance().getReference("Users")
    }
    fun setupactionbar1()
    {
        val toolbar1234=findViewById<Toolbar>(R.id.search_bar1)
        setSupportActionBar(toolbar1234)
        val actionbar123=supportActionBar
        if(actionbar123!=null)
        {
            actionbar123.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24)
            actionbar123.setDisplayHomeAsUpEnabled(true)



        }

         toolbar1234.setNavigationOnClickListener {
             onBackPressed()
         }
    }


    private fun signinregistereduser() {
        val email1:String = findViewById<TextView>(R.id.et_email1).text.toString().trim{it<= ' ' }
        val password1:String =findViewById<TextView>(R.id.et_password1).text.toString().trim{it<=' '}

        if (revalidate1(email1,password1)) {
            showprogressdialog("Signing In...")
            auth.signInWithEmailAndPassword(email1,password1).addOnCompleteListener(this) { task->
                hideprogressdialog()
                if(task.isSuccessful){

                    val layout123=layoutInflater.inflate(R.layout.custom_toast_layout,findViewById(R.id.view_layout_of_toast))
                    val   toast4:Toast=Toast(this)
                    toast4.view=layout123
                    val txtmsg12:TextView=layout123.findViewById(R.id.textview_toast)
                    txtmsg12.setText( "You have Signed In successfully")
                    toast4.duration.toLong()
                    toast4.show()

                    finish()
                    startActivity(Intent(this,MainActivity::class.java))

                    Log.d("Sign IN","Sign with Email successfully")





                }
                else{
                    //if sign in fails display the message to the users

                    val layout1=layoutInflater.inflate(R.layout.error_toast_layout,findViewById(R.id.view_layout_of_toast1))
                    val toast1:Toast= Toast(this)
                    toast1.view=layout1
                    val txtmsg1:TextView=layout1.findViewById(R.id.textview_toast1)
                    txtmsg1.setText("Authentication failed,Please enter correct credentials.")
                    toast1.duration.toShort()
                    toast1.show()
                    Log.w("Sign In","sign with email failed",task.exception)

                }


            }

        }
    }

    private fun revalidate1(
        email1: String,
        password1: String
    )
    : Boolean {
        return when{
            TextUtils.isEmpty(email1)->{
                showerrorsnackbar("Please enter your Email Address")
                false
            }
            TextUtils.isEmpty(password1)->{
                showerrorsnackbar("Please enter your Password")
                false
            }
            else->{
                true
            }

        }
    }











}