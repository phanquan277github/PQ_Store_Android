package com.example.pqstore.model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName

data class ProductModel(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name :String,
    @SerializedName("price")
    var price :Double,
    @SerializedName("thumbnail_path")
    var imagePath :String,
    @SerializedName("favorite")
    var favorite :Boolean = false,
    @SerializedName("images")
    var images: ArrayList<ImageModel> = ArrayList(),
    @SerializedName("sizes")
    var sizes: ArrayList<SizeModel> = ArrayList(),
    var rating: Double = 0.0,
    var quantity: Int = 0,
    var description: String = ""
)

data class ImageModel(
    @SerializedName("id")
    var id: Int,
    @SerializedName("image_path")
    var imagePath: String
)

data class SizeModel(
    @SerializedName("id")
    var id: Int,
    @SerializedName("size")
    var size: String,
    @SerializedName("quantity")
    var quantity: String
)
