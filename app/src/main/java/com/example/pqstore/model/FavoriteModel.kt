package com.example.pqstore.model

import com.google.gson.annotations.SerializedName

data class FavoriteModel(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name :String,
    @SerializedName("price")
    var price :Double,
    @SerializedName("thumbnail_path")
    var imagePath :String,
    @SerializedName("favorite")
    var favorite :Boolean,
)
