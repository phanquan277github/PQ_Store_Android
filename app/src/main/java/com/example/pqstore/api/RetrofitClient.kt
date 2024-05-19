package com.example.pqstore.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    fun getRetrofitClient() : ApiInterface {
        // thay đường dẫn API vào baseUrl
        return Retrofit.Builder().baseUrl("http://192.168.1.11:80")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiInterface::class.java)
    }
}