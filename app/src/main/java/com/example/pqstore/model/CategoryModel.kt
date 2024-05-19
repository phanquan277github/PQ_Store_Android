package com.example.pqstore.model

import com.google.gson.annotations.SerializedName

data class CategoryModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("image_path")
    val imagePath: String = ""
)
