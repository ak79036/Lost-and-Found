package com.example.lostandfound.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lostandfound.R
import com.example.lostandfound.adaptors.ChatAdapter
import com.example.lostandfound.adaptors.ChatAdapter1
import com.example.lostandfound.adaptors.ChatAdapter2
import com.example.lostandfound.models.Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Your_lost : AppCompatActivity() {
    private var chatAdapter: ChatAdapter2?=null

    private lateinit var recyclerviewchats: RecyclerView
    private lateinit var mdatabasereferce2: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_your_lost)
          setupActionBar()
        mdatabasereferce2=FirebaseDatabase.getInstance().getReference("Lost_Chats")
        recyclerviewchats=findViewById(R.id.my_lost_recycler)
        recyclerviewchats.setHasFixedSize(true)
        var linearlayoutmanager= LinearLayoutManager(applicationContext)
        linearlayoutmanager.stackFromEnd=true
        recyclerviewchats.layoutManager=linearlayoutmanager
        var mchatlist=ArrayList<Chat>()

        mdatabasereferce2.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                (mchatlist as kotlin.collections.ArrayList<Chat>).clear()
                for(snapshot2 in snapshot.children)
                {

                    val chat1=snapshot2.getValue(Chat::class.java)
                    if(chat1!!.sender_id==FirebaseAuth.getInstance().currentUser!!.uid)
                    {(mchatlist as kotlin.collections.ArrayList<Chat>).add(chat1!!)
                    chatAdapter= ChatAdapter2(this@Your_lost,mchatlist as ArrayList<Chat>)
                    recyclerviewchats.adapter=chatAdapter}
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })







    }
    fun setupActionBar() {
        val toolbar123 = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_full_image)
        setSupportActionBar(toolbar123)
        val actionbar = supportActionBar
        actionbar?.title="My Lost"
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