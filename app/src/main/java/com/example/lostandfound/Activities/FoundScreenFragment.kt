package com.example.lostandfound.Activities

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.webkit.MimeTypeMap
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lostandfound.R
import com.example.lostandfound.adaptors.ChatAdapter
import com.example.lostandfound.adaptors.ChatAdapter1
import com.example.lostandfound.models.Chat
import com.example.lostandfound.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*

class FoundScreenFragment : Fragment() {
    private var          name:String=""
    private var image:String?=""
    private var email:String=""
    private var mob_no:String=""
    private  var muri:String=""
    private var mselectedimageuri: Uri?=null
    private  val  galleryrequestcode:Int=2
    private lateinit var  mprogressdialog: Dialog
    private lateinit var mauth: FirebaseAuth
    private lateinit var mdatabaseref: DatabaseReference
    private var chatAdapter: ChatAdapter1?=null
    private var mchatlist: List<Chat>?=null
    private lateinit var recyclerviewchats: RecyclerView
    private lateinit var mdatabasereferce2: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mdatabaseref=FirebaseDatabase.getInstance().getReference("Found_Chats")
       val  view= inflater.inflate(R.layout.fragment_found_screen, container, false)
        val messageid=view.findViewById<TextView>(R.id.lost_screen_chat)
        mauth=FirebaseAuth.getInstance()
        mdatabasereferce2= FirebaseDatabase.getInstance().getReference("Found_Chats")
        val currentuserid=mauth.currentUser!!.uid
        recyclerviewchats=view.findViewById(R.id.message_recycler_found)
        recyclerviewchats.setHasFixedSize(true)
        var linearlayoutmanager= LinearLayoutManager(context)
        linearlayoutmanager.stackFromEnd=true
        recyclerviewchats.layoutManager=linearlayoutmanager



        val mdatareference1= FirebaseDatabase.getInstance().getReference("Users")
        mdatareference1.child(currentuserid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user1=snapshot.getValue(User::class.java)
                name= user1?.name.toString()
                image=user1?.image
                email=user1?.email.toString()
                mob_no=user1?.mobile.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        mchatlist=kotlin.collections.ArrayList()
        mdatabasereferce2.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                (mchatlist as kotlin.collections.ArrayList<Chat>).clear()
                for(snapshot2 in snapshot.children)
                {
                    val chat1=snapshot2.getValue(Chat::class.java)
                    (mchatlist as kotlin.collections.ArrayList<Chat>).add(chat1!!)
                    chatAdapter=ChatAdapter1(view.context,mchatlist as kotlin.collections.ArrayList<Chat>)
                    recyclerviewchats.adapter=chatAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })






        view.findViewById<ImageButton>(R.id.chat_button_1).setOnClickListener{



            val message=messageid.text.toString()
            if(message=="")
            {
//                val snackbar = Snackbar.make(requireActivity().findViewById(android.R.id.content),"Please enter the message", Snackbar.LENGTH_SHORT)
//                val snackbarview=snackbar.view
//                snackbarview.setBackgroundColor(
//                    ContextCompat.getColor(
//                        this.requireContext(),
//                        R.color.snackbar_error_color
//                    )
//                )
//                snackbar.show()
                val layout1 =layoutInflater.inflate(R.layout.error_toast_layout,requireActivity().findViewById(R.id.view_layout_of_toast1))
                val toast1: Toast = Toast(context)
                toast1.view=layout1
                val txtmsg: TextView =layout1.findViewById(R.id.textview_toast1)
                txtmsg.setText("please enter the message")
                toast1.duration.toShort()
                toast1.show()
            }
            else{


                sendingmessage(currentuserid,message,name,image!!,email,mob_no)
            }
            messageid.text=""
        }
        view.findViewById<CircleImageView>(R.id.lost_screen_gallery).setOnClickListener {
            val intent1= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent1,galleryrequestcode)
        }














        return view
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== AppCompatActivity.RESULT_OK && requestCode==galleryrequestcode  && data!!.data!=null)
        {
            mselectedimageuri=data.data

            showprogressdialog("Uploading Image...")
            if(mselectedimageuri!=null)
            {
                val sref: StorageReference = FirebaseStorage.getInstance().
                getReference("Found_Chat_Images").
                child("User_image"+System.currentTimeMillis()+"."+getfileextension(mselectedimageuri))
                sref.putFile(mselectedimageuri!!).addOnSuccessListener{
                        tasksnapshot->
                    tasksnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        //this is actual link we want
                            uri->
                        muri= uri.toString()
                        val reference=FirebaseDatabase.getInstance().reference
                        val messagekey=reference.push().key!!
                        val senderid:String=mauth.uid!!
                        val message="image sent9430828829"
                        val url=muri
                        val currentmillisecond=System.currentTimeMillis()
                        val sdm: SimpleDateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
                        val   sdm1:SimpleDateFormat= SimpleDateFormat("hh:mm a",Locale.getDefault())
                        val time:String=sdm1.format(currentmillisecond)
                        val  date:String=sdm.format(currentmillisecond)
                        val timestamp:String= "$date $time"
                        val chats:Chat=Chat(sender_id = senderid, messageid =messagekey, message = message,url=url,timestamp = timestamp, username = name, profile_url = image!!, profile_email = email, profile_no = mob_no)
                        mdatabaseref.child(messagekey).setValue(chats).addOnSuccessListener {

                        }


                    }
                    hideprogressdialog()

                }.addOnFailureListener{

                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                    hideprogressdialog()

                }

            }





        }
    }
    private fun sendingmessage(senderuid:String?,message:String,username:String,profileimage:String,profileemail:String,profilemob:String) {
        val reference=FirebaseDatabase.getInstance().reference
        val messagekey=reference.push().key
        val currentmillisecond=System.currentTimeMillis()
        val sdm:SimpleDateFormat= SimpleDateFormat("dd MMM", Locale.getDefault())
        val   sdm1:SimpleDateFormat= SimpleDateFormat("hh:mm a",Locale.getDefault())
        val time:String=sdm1.format(currentmillisecond)
        val  date:String=sdm.format(currentmillisecond)
        val timestamp:String= "$date $time"
        val chats:Chat=Chat(sender_id = senderuid!!,message=message, messageid = messagekey!!, timestamp =timestamp, username = username, profile_url = profileimage, profile_email = profileemail, profile_no = profilemob)
        mdatabaseref.child(messagekey).setValue(chats).addOnCompleteListener {

        }.addOnFailureListener {
            Toast.makeText(this.context,"Failed in setting the info into realtime database",Toast.LENGTH_SHORT).show()
        }



    }

    private fun showprogressdialog(Text:String){
        mprogressdialog= Dialog(this.requireContext())
        mprogressdialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mprogressdialog.setContentView(R.layout.dialog_progress)

        mprogressdialog.findViewById<TextView>(R.id.tv_progress_text).text=Text

        mprogressdialog.show()
        Handler().postDelayed({},2000)


    }
    private  fun hideprogressdialog()
    {
        mprogressdialog.dismiss()
    }
    private fun getfileextension(uri:Uri?):String?
    {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(activity?.contentResolver?.getType(uri!!))

    }




}