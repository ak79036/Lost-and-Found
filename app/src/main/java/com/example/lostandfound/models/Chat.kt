package com.example.lostandfound.models

import android.os.Parcel
import android.os.Parcelable

data class Chat(
    var sender_id:String="",
    val message:String="",
    val url:String="",
    val messageid:String="",
    val username:String="",
    val timestamp:String="",
    val profile_url:String="",
    val profile_email:String="",
    val profile_no:String=""



):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(sender_id)
        parcel.writeString(message)
        parcel.writeString(url)
        parcel.writeString(messageid)
        parcel.writeString(username)
        parcel.writeString(timestamp)
        parcel.writeString(profile_url)
        parcel.writeString(profile_email)
        parcel.writeString(profile_no)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Chat> {
        override fun createFromParcel(parcel: Parcel): Chat {
            return Chat(parcel)
        }

        override fun newArray(size: Int): Array<Chat?> {
            return arrayOfNulls(size)
        }
    }
}

