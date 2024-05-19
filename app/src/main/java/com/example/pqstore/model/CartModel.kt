package com.example.pqstore.model

import com.google.gson.annotations.SerializedName

data class CartModel(
    @SerializedName("id")
    var id: Int,
    @SerializedName("product_id")
    var productId: Int,
    @SerializedName("name")
    var name :String,
    @SerializedName("price")
    var price :Double,
    @SerializedName("thumbnail_path")
    var imagePath :String,
    @SerializedName("quantity")
    var quantity :Int,
    @SerializedName("size")
    var size :String
)
