package com.example.pqstore.model

import com.google.gson.annotations.SerializedName

data class AddressModel(
    @SerializedName("code")
    var code: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("name_with_type")
    var nameWithType: String,
    @SerializedName("path_with_type")
    var pathWithType: String,
    @SerializedName("parent_code")
    var parentCode: String
)

