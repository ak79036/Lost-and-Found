package com.example.lostandfound.adaptors

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lostandfound.Activities.Full_image_Activity
import com.example.lostandfound.Activities.LostScreenFragment
import com.example.lostandfound.Activities.user_profile_activity
import com.example.lostandfound.R
import com.example.lostandfound.models.Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import de.hdodenhof.circleimageview.CircleImageView
import io.github.muddz.styleabletoast.StyleableToast


class ChatAdapter(
    mcontext: Context,
    mchatlist:List<Chat>
):RecyclerView.Adapter<ChatAdapter.ViewHolder?>()
{
    private val mcontext:Context
    private val mchatlist:List<Chat>

    var firebaseuser: FirebaseUser=FirebaseAuth.getInstance().currentUser!!
    init {
        this.mcontext= mcontext
        this.mchatlist=mchatlist

    }
    // we generate view from here
    override fun onCreateViewHolder(parent: ViewGroup, Viewtype: Int): ViewHolder {

         return if(Viewtype==1)
         {
             val view:View=LayoutInflater.from(mcontext).inflate(R.layout.message_item_right,parent,false)
             ViewHolder(view)
         }
        else{
             val view:View=LayoutInflater.from(mcontext).inflate(R.layout.message_item_left,parent,false)
             ViewHolder(view)
         }
    }

    override fun getItemCount(): Int {
           return mchatlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         val chat:Chat=mchatlist[position]
        Glide.with(mcontext).load(chat.profile_url).placeholder(R.drawable.my_profile_image1).centerCrop().into(
            holder.chat_profile_image!!
        )

        holder.chat_profile_image!!.setOnClickListener {
            val decisions= arrayOf<CharSequence>(
                "View Profile",
                "Cancel"
            )
            var dialog:AlertDialog.Builder=AlertDialog.Builder(holder.itemView.context)
            dialog.setIcon(R.drawable.lost_items)
            dialog.setTitle("What do you want?")
            dialog.setItems(decisions,DialogInterface.OnClickListener{
                    dialog,which->
                if(which==0)
                {
                    val intent=Intent(mcontext,user_profile_activity::class.java)
                    intent.putExtra("url_lost_profile_url",chat.profile_url)
                    intent.putExtra("url_lost_profile_name",chat.username)
                    intent.putExtra("url_lost_profile_email",chat.profile_email)
                    intent.putExtra("url_lost_profile_mob",chat.profile_no)
                    mcontext.startActivity(intent)

                }

            })
            dialog.show()
        }

        // message of images here
        if(chat.message=="image sent9430828829" && chat.url!="")
        {
            // image message right side of
            if(chat.sender_id == firebaseuser.uid)
            {
                holder.chat_message!!.visibility=View.GONE
                holder.chat_time!!.visibility=View.GONE
                holder.chat_image_right!!.visibility=View.VISIBLE
                holder.chat_time_1!!.visibility=View.VISIBLE
                Glide.with(mcontext).load(chat.url).placeholder(R.drawable.baseline_sensor_occupied_24).into(holder.chat_image_right!!)
                holder.chat_time_1!!.text=chat.timestamp
                holder.chat_image_right!!.setOnClickListener {
                  val decisions= arrayOf<CharSequence>(
                      "View Full Image",
                      "Delete Message",
                      "Cancel"
                  )
                    var dialog:AlertDialog.Builder=AlertDialog.Builder(holder.itemView.context)
                    dialog.setIcon(R.drawable.lost_items)
                    dialog.setTitle("What do you want?")
                    dialog.setItems(decisions,DialogInterface.OnClickListener{
                        dialog,which->
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
                    dialog.show()

                }
            }
            //image message left side of
            else if(chat.sender_id != firebaseuser.uid){
                holder.chat_username!!.visibility=View.GONE
                holder.chat_message!!.visibility=View.GONE
                holder.chat_time!!.visibility=View.GONE
                holder.chat_username_1!!.visibility=View.VISIBLE
                holder.chat_image_left!!.visibility=View.VISIBLE
                holder.chat_time_1!!.visibility=View.VISIBLE
                holder.chat_username_1!!.text=chat.username
                Glide.with(mcontext).load(chat.url).placeholder(R.drawable.baseline_sensor_occupied_24).into(holder.chat_image_left!!)
                holder.chat_time_1!!.text=chat.timestamp
                holder.chat_image_left!!.setOnClickListener {
                    val decisions= arrayOf<CharSequence>(
                        "View Full Image",
                        "Cancel"
                    )
                    var dialog:AlertDialog.Builder=AlertDialog.Builder(holder.itemView.context)
                    dialog.setIcon(R.drawable.lost_items)
                    dialog.setTitle("What do you want?")
                    dialog.setItems(decisions,DialogInterface.OnClickListener{
                            dialog,which->
                        if(which==0)
                        {
                            val intent=Intent(mcontext,Full_image_Activity::class.java)
                            intent.putExtra("url_lost",chat.url)
                            mcontext.startActivity(intent)

                        }

                    })
                    dialog.show()
                }
            }
        }
        // messages of text here
        //
        else
        {


            if(firebaseuser.uid==chat.sender_id)
            {
                holder.chat_message!!.visibility=View.VISIBLE
                holder.chat_time!!.visibility=View.VISIBLE
                holder.chat_username_1!!.visibility=View.GONE
                holder.chat_image_right!!.visibility=View.GONE
                holder.chat_time_1!!.visibility=View.GONE

                holder.chat_message!!.text = chat.message
                holder.chat_time!!.text = chat.timestamp
                holder.chat_message!!.setOnClickListener {
                    val decisions= arrayOf<CharSequence>(
                        "Delete Message",
                        "Cancel"
                    )
                    var dialog:AlertDialog.Builder=AlertDialog.Builder(holder.itemView.context)
                    dialog.setIcon(R.drawable.lost_items)
                    dialog.setTitle("What do you want?")
                    dialog.setItems(decisions,DialogInterface.OnClickListener{
                            dialog,which->

                         if(which==0)
                        {
                            delete_message(position,holder)
                        }
                    })
                    dialog.show()
                }
            }
            else{
                holder.chat_username!!.visibility=View.VISIBLE
                holder.chat_message!!.visibility=View.VISIBLE
                holder.chat_time!!.visibility=View.VISIBLE
                holder.chat_username_1!!.visibility=View.GONE
                holder.chat_image_left!!.visibility=View.GONE
                holder.chat_time_1!!.visibility=View.GONE
                holder.chat_username!!.text=chat.username
                holder.chat_message!!.text = chat.message
                holder.chat_time!!.text = chat.timestamp
            }
        }
    }



    // we get view items from here
   inner class ViewHolder(viewitem:View):RecyclerView.ViewHolder(viewitem)
   {
       var chat_profile_image:CircleImageView?=null
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
   // represents the position of view i.e whether it is position item left or right
    override fun getItemViewType(position: Int): Int {

        return if(mchatlist[position].sender_id == firebaseuser.uid){
            1
        }
        else{
            0
        }

    }
    private fun delete_message(position:Int, holder: ChatAdapter.ViewHolder)
    {
        val mdataref1=FirebaseDatabase.getInstance().getReference("Lost_Chats").child(mchatlist[position].messageid).removeValue().addOnCompleteListener {
            task->
            if(task.isSuccessful)
            {
               StyleableToast.makeText(holder.itemView.context,"Message Deleted Successfully",R.style.exampletoast).show()
            }
            else{
                StyleableToast.makeText(holder.itemView.context,"Message not Deleted",R.style.example_toast1).show()
            }

        }



    }



}

