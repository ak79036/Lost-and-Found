package com.example.lostandfound.Activities
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.lostandfound.databinding.ActivityMainBinding
import com.example.lostandfound.R
import com.example.lostandfound.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.checkerframework.common.returnsreceiver.qual.This

class profile_activity : baseactivity() {
    private  val  galleryrequestcode:Int=1
    private lateinit var mdatabaserefernce:DatabaseReference
    private lateinit var mauth:FirebaseAuth
    private var mselectedimageuri:Uri ?=null
    private var mprofileimageuri:String=""
    private lateinit var muserdetails:User
  private lateinit var  mainprofileimage:ImageView
   private lateinit var  mainprofilename:TextView
  private lateinit var mainprofileemail:TextView
  private lateinit var mainprofileno:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

      val toolbar_myprofile=findViewById<Toolbar>(R.id.Toolbar_profile_Screen)
        toolbar_myprofile.title="My Profile"
        toolbar_myprofile.setTitleTextColor(resources.getColor(R.color.black))

         toolbar_myprofile.setNavigationIcon(R.drawable.baseline_arrow_back_ios_24)
        toolbar_myprofile.setNavigationOnClickListener {
            onBackPressed()
        }
         mainprofileimage=findViewById<ImageView>(R.id.user_image)
         mainprofilename=findViewById<TextView>(R.id.my_profile_name)
         mainprofileemail=findViewById<TextView>(R.id.my_profile_email)
         mainprofileno=findViewById<TextView>(R.id.my_profile_no)
        val mainprofilebutton=findViewById<Button>(R.id.my_profile_button)
        mainprofileimage.setOnClickListener{

            chooseimages()
        }
        mainprofilebutton.setOnClickListener {
            if(mselectedimageuri!=null)
            {
                uploaduserimage()
            }
            else{
                showprogressdialog("Please Wait...")
                updateuserprofiledata()
            }
        }

        mdatabaserefernce=FirebaseDatabase.getInstance().getReference("Users")
        mauth=FirebaseAuth.getInstance()
        val currentuserid=mauth.uid.toString()
        mdatabaserefernce.child(currentuserid).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                val user1=snapshot.getValue(User::class.java)

                mainprofilename.text=user1?.name
                mainprofileemail.text=user1?.email


                    mainprofileno.text=user1?.mobile

                Glide.with(applicationContext)
                    .load(user1?.image)
                    .centerCrop()
                    .placeholder(R.drawable.my_profile_image1)
                    .into(mainprofileimage)
                if (user1 != null) {
                    muserdetails=user1
                }

            }
            override fun onCancelled(error: DatabaseError) {
              Toast.makeText(applicationContext,"Sorry can't be able to fetch data please check your internet connectivity",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun chooseimages()
    {
        val intent1= Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent1,galleryrequestcode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK && requestCode==galleryrequestcode && data!!.data!=null)
        {
                mselectedimageuri=data.data

            Glide.with(this@profile_activity)
                .load(mselectedimageuri)
                .centerCrop()
                .placeholder(R.drawable.my_profile_image1)
                .into(mainprofileimage)

        }
    }
        private fun uploaduserimage(){
        showprogressdialog("Please Wait...")
        if(mselectedimageuri!=null)
        {
            val sref:StorageReference=FirebaseStorage.getInstance().
            getReference("Users_profile_images").
            child("User_image"+System.currentTimeMillis()+"."+getfileextension(mselectedimageuri))
            sref.putFile(mselectedimageuri!!).addOnSuccessListener {
             tasksnapshot->
                tasksnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                    //this is actual link we want where data stored in firebase
                    uri->

                        mprofileimageuri = uri.toString()

                    updateuserprofiledata()


                }
                hideprogressdialog()
            }.addOnFailureListener{

                Toast.makeText(this@profile_activity,it.message,Toast.LENGTH_SHORT).show()
                hideprogressdialog()
            }


        }
    }
    private fun getfileextension(uri:Uri?):String?
    {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri!!))

    }
    private fun updateuserprofiledata()
    {
        var anychangesmade=false
        val userhashmap=HashMap<String,Any>()
        if(mprofileimageuri.isNotEmpty() && mprofileimageuri!=muserdetails.image)
        {
            userhashmap["image"]=mprofileimageuri
            anychangesmade=true
        }
        if(mainprofilename.text.toString()!=muserdetails.name)
        {
            userhashmap["name"]=mainprofilename.text.toString()
            anychangesmade=true
        }
        if(mainprofileemail.text.toString()!=muserdetails.email)
        {
            userhashmap["email"]=mainprofileemail.text.toString()
            anychangesmade=true
        }
        if(mainprofileno.text.toString()!=muserdetails.mobile.toString())
        {
            userhashmap["mobile"]=mainprofileno.text.toString()
            anychangesmade=true
        }
        if(anychangesmade)
        {
            updateuserprofiledata1(userhashmap)
        }
        if(!anychangesmade)
        {
            val layout1 =layoutInflater.inflate(R.layout.error_toast_layout,findViewById(R.id.view_layout_of_toast1))
            val toast1: Toast=Toast(this)
            toast1.view=layout1
            val txtmsg:TextView=layout1.findViewById(R.id.textview_toast1)
            txtmsg.setText("You have not updated any data")
            toast1.duration.toShort()
            toast1.show()
            hideprogressdialog()
        }

    }
    private fun updateuserprofiledata1(userhashmap1:HashMap<String,Any>)
    {
          val currentuserid=mauth.uid.toString()
        mdatabaserefernce.child(currentuserid).updateChildren(userhashmap1).addOnSuccessListener {
            val layout  = layoutInflater.inflate(R.layout.custom_toast_layout,findViewById(R.id.view_layout_of_toast))
            val toast:Toast= Toast(this)
            toast.view=layout
            val  txtmst:TextView=layout.findViewById(R.id.textview_toast)
            txtmst.text ="Your Profile Updated Successfully"
            toast.duration.toLong()
            toast.show()
            hideprogressdialog()
            finish()
        }.addOnFailureListener {
            hideprogressdialog()
            Toast.makeText(this,"Profile update error",Toast.LENGTH_SHORT).show()
        }
    }
}