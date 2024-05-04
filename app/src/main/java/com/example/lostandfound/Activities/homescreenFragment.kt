package com.example.lostandfound.Activities

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.lostandfound.R
import com.example.lostandfound.models.User
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.w3c.dom.Text
import java.io.IOException


                                        //when something is clicked on navigation item
class homescreenFragment : Fragment(),NavigationView.OnNavigationItemSelectedListener {
private lateinit var mdatabasereference:DatabaseReference
private lateinit var mauth:FirebaseAuth





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {




        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_homescreen, container, false)
        // update ui,get view references

        val typeface:Typeface= Typeface.createFromAsset(activity?.assets,"carbon bl.otf")
        val welcometext:TextView=view.findViewById(R.id.welcome_home_screen)
        welcometext.typeface=typeface
        val typeface1:Typeface= Typeface.createFromAsset(activity?.assets,"main_profile.ttf")

        val username:TextView=view.findViewById(R.id.welcome_username)
        username.typeface=typeface1
      val   toolbar_homefragment1=view.findViewById<Toolbar>(R.id.toolbar_homefragment)
        val drawerlayout=view.findViewById<DrawerLayout>(R.id.drawer_layout)
        toolbar_homefragment1.title = "Lost & Found"
        toolbar_homefragment1.setTitleTextColor(resources.getColor(R.color.black_1))



       val your_lost_button =view.findViewById<Button>(R.id.your_lost1).setOnClickListener {
           val intent1=Intent(activity,Your_lost::class.java)
           startActivity(intent1)

       }
        val your_found_button =view.findViewById<Button>(R.id.your_found1).setOnClickListener {
            val intent=Intent(activity,your_found::class.java)
            startActivity(intent)
        }
            val headerview: NavigationView = view.findViewById(R.id.view_navigation)
            val headerview1 = headerview.getHeaderView(0)
            val navusername = headerview1.findViewById<TextView>(R.id.header_username)
            val navimage = headerview1.findViewById<ImageView>(R.id.user_image)
            mauth = FirebaseAuth.getInstance()
            val `current-userid` = mauth.uid.toString()

            mdatabasereference =
                FirebaseDatabase.getInstance().getReference("Users").child(`current-userid`)

            mdatabasereference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(activity==null)
                    {
                        return
                    }
                    val user1 = snapshot.getValue(User::class.java)
                    val name = user1?.name
                    username.text=user1?.name
                    navusername?.text = name
                    val image = user1?.image

                    Glide
                        .with(this@homescreenFragment)
                        .load(image)
                        .centerCrop()
                        .placeholder(R.drawable.ic_user_image)
                        .into(navimage)


                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Sorry,Can't be able to load", Toast.LENGTH_SHORT)
                        .show()

                }

            })





            val toggle = ActionBarDrawerToggle(
                this.activity,
                drawerlayout,
                toolbar_homefragment1,
                R.string.OpenDrawer,
                R.string.CloseDrawer
            )
            drawerlayout.addDrawerListener(toggle)
            toggle.syncState()






            toolbar_homefragment1.setNavigationIcon(R.drawable.menuiicontoolbar)

            //do whatever is designed here
            headerview.setNavigationItemSelectedListener(this)


        return view

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId)
        {
            R.id.nav_my_profile->{
                val intent1=Intent(activity,profile_activity::class.java)
                startActivity(intent1)
            }
            R.id.nav_Sign_out->{FirebaseAuth.getInstance().signOut()
            val intent2=Intent(activity,IntroActivity12::class.java)
                //back button does not take you this activity clear all the top activity on the backstack
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

            startActivity(intent2)
              activity?.finish()


            }




        }
        val drawerlayout1=view?.findViewById<DrawerLayout>(R.id.drawer_layout)
        drawerlayout1?.closeDrawer(GravityCompat.START)
        return true

    }





    }









