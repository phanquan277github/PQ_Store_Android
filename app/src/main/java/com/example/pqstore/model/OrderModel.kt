package com.example.pqstore.model

import com.google.gson.annotations.SerializedName

data class OrderModel(
    @SerializedName("id")
    val orderId: Int,
    @SerializedName("full_name")
    val name: String,
    @SerializedName("phone_number")
    val phone: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("payment_methods")
    val paymentMethod: String,
    @SerializedName("order_status")
    val orderStatus: String,
    @SerializedName("note")
    val note: String,
    @SerializedName("order_date")
    val orderDate: String,
    @SerializedName("total")
    val total: Double,
    @SerializedName("order_items")
    val orderItems: ArrayList<CartModel>
)
