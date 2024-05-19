package com.example.pqstore.model

import com.google.gson.annotations.SerializedName

data class SliderModel(
    @SerializedName("id")
    var id: Int,
    @SerializedName("image_path")
    var imagePath: String
)
