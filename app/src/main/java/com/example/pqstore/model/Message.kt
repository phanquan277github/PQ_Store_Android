package com.example.pqstore.model

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("success")
    var success:Int,
    @SerializedName("message")
    var message:String
)
