package com.example.pqstore.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("uid")
    var uid: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("email")
    var email: String = "",
    @SerializedName("avatar")
    var avatar: String = ""
)