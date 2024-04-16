package com.example.pqstore.model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

data class ProductModel (
    var name: String = "",
    var price: Double = 0.0,
    var images: ArrayList<String> = ArrayList(),
    var sizes: ArrayList<String> = ArrayList(),
    var colors: ArrayList<String> = ArrayList(),
    var rating: Double = 0.0,
    var quantity: Int = 0,
    var description: String = "",
    var favorite: Boolean = false
): Parcelable {
    constructor(parcel: Parcel): this (
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.createStringArrayList() as ArrayList<String>,
        parcel.createStringArrayList() as ArrayList<String>,
        parcel.createStringArrayList() as ArrayList<String>,
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readInt() != 0
    )

    override fun describeContents(): Int {
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeDouble(price)
        dest.writeStringList(images)
        dest.writeStringList(sizes)
        dest.writeStringList(colors)
        dest.writeDouble(rating)
        dest.writeInt(quantity)
        dest.writeString(description)
        dest.writeInt(if (favorite) 1 else 0)
    }

    companion object CREATOR : Parcelable.Creator<ProductModel> {
        override fun createFromParcel(parcel: Parcel): ProductModel {
            return ProductModel(parcel)
        }

        override fun newArray(size: Int): Array<ProductModel?> {
            return arrayOfNulls(size)
        }
    }
}