package com.example.pqstore.model

import com.google.gson.annotations.SerializedName
data class UserAddressModel(
    @SerializedName("id")
    var id: Int,
    @SerializedName("user_id")
    var userId: Int,
    @SerializedName("full_name")
    var name: String,
    @SerializedName("address")
    var address: String,
    @SerializedName("street_address")
    var street: String,
    @SerializedName("phone_number")
    var phone: String
)
