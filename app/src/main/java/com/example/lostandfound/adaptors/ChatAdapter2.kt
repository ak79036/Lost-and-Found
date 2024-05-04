package com.example.lostandfound.adaptors

import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.lostandfound.Activities.Full_image_Activity
import com.example.lostandfound.R
import com.example.lostandfound.models.Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import de.hdodenhof.circleimageview.CircleImageView
import io.github.muddz.styleabletoast.StyleableToast
import kotlin.properties.Delegates

class ChatAdapter2(mcontext:Context,mchatlist:List<Chat>):RecyclerView.Adapter<ChatAdapter2.ViewHolder?>(){

 private val mcontext:Context
 private val mchatlist:List<Chat>



    var firebaseUser:FirebaseUser=FirebaseAuth.getInstance().currentUser!!
 init {
     this.mcontext = mcontext
     this.mchatlist = mchatlist

 }

    inner class ViewHolder(viewitem:View):RecyclerView.ViewHolder(viewitem)
 {
     var chat_profile_image: CircleImageView?=null
     var chat_username:TextView?=null
     var chat_message:TextView?=null
     var chat_time:TextView?=null
     var chat_username_1:TextView?=null
     var chat_image_left:ImageView?=null
     var chat_image_right:ImageView?=null
     var chat_time_1:TextView?=null


  init {
      chat_profile_image=viewitem.findViewById(R.id.chat_profile_image)
      chat_username=viewitem.findViewById(R.id.chat_username)
      chat_message=viewitem.findViewById(R.id.chat_message)
      chat_time=viewitem.findViewById(R.id.chat_time)
      chat_username_1=viewitem.findViewById(R.id.chat_username_1)
      chat_image_left=viewitem.findViewById(R.id.chat_image_left)
      chat_image_right=viewitem.findViewById(R.id.chat_image_right)
      chat_time_1=viewitem.findViewById(R.id.chat_time_1)
  }

 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
          val view:View=LayoutInflater.from(mcontext).inflate(R.layout.message_item_right,parent,false)
          return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(mchatlist[position].sender_id!=firebaseUser.uid)
        {holder.chat_profile_image!!.visibility=View.GONE
         holder.chat_username!!.visibility=View.GONE
            holder.chat_message!!.visibility=View.GONE
            holder.chat_time!!.visibility=View.GONE
            holder.chat_username_1!!.visibility=View.GONE
            holder.chat_image_right!!.visibility=View.GONE
            holder.chat_time_1!!.visibility=View.GONE
        }
        if(mchatlist[position].sender_id==firebaseUser.uid) {
            val chat: Chat = mchatlist[position]
            // message of images here
            if(chat.message=="image sent9430828829" && chat.url!="") {
                // image message right side of
                holder.chat_message!!.visibility=View.GONE
                holder.chat_time!!.visibility=View.GONE
                holder.chat_image_right!!.visibility=View.VISIBLE
                holder.chat_time_1!!.visibility=View.VISIBLE
                Glide.with(mcontext).load(chat.url).placeholder(R.drawable.baseline_sensor_occupied_24).into(holder.chat_image_right!!)
                holder.chat_time_1!!.text=chat.timestamp
                holder.chat_image_right!!.setOnClickListener {
                    val decisions1= arrayOf<CharSequence>(
                        "View Full Image",
                        "Delete Message",
                        "Cancel"
                    )
                    var dialog1:AlertDialog.Builder=AlertDialog.Builder(holder.itemView.context)
                    dialog1.setIcon(R.drawable.lost_items)
                    dialog1.setTitle("What do you want?")
                    dialog1.setItems(decisions1,DialogInterface.OnClickListener{
                            dialog1,which->
                        if(which==0)
                        {
                            val intent=Intent(mcontext,Full_image_Activity::class.java)
                            intent.putExtra("url_lost",chat.url)
                            mcontext.startActivity(intent)
                        }
                        else if(which==1)
                        {
                          delete_message(position,holder)
                        }
                    })
                    dialog1.show()

                }
                }

            else{
                holder.chat_message!!.visibility=View.VISIBLE
                holder.chat_time!!.visibility=View.VISIBLE
                holder.chat_username_1!!.visibility=View.GONE
                holder.chat_image_right!!.visibility=View.GONE
                holder.chat_time_1!!.visibility=View.GONE

                holder.chat_message!!.text = chat.message
                holder.chat_time!!.text = chat.timestamp
                holder.chat_message!!.setOnClickListener {
                    val decisions1= arrayOf<CharSequence>(
                        "Delete Message",
                        "Cancel"
                    )
                    var dialog1:AlertDialog.Builder=AlertDialog.Builder(holder.itemView.context)
                    dialog1.setIcon(R.drawable.lost_items)
                    dialog1.setTitle("What do you want?")
                    dialog1.setItems(decisions1,DialogInterface.OnClickListener{
                            dialog1,which->

                        if(which==0)
                        {
                         delete_message(position, holder)
                        }
                    })
                    dialog1.show()
                }
            }











        }
    }



    override fun getItemCount(): Int {
        return mchatlist.size



    }



    private fun delete_message(position:Int, holder: ChatAdapter2.ViewHolder)
    {
        if(mchatlist[position].sender_id==firebaseUser.uid) {
            val mdataref1 = FirebaseDatabase.getInstance().getReference("Lost_Chats")
                .child(mchatlist[position].messageid).removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    StyleableToast.makeText(
                        holder.itemView.context,
                        "Message Deleted Successfully",
                        R.style.exampletoast
                    ).show()
                } else {
                    StyleableToast.makeText(
                        holder.itemView.context,
                        "Message not Deleted",
                        R.style.exampletoast1
                    ).show()
                }

            }
        }



    }


}

