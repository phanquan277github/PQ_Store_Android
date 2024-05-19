package com.example.pqstore.api

import com.example.pqstore.model.AddressModel
import com.example.pqstore.model.CartModel
import com.example.pqstore.model.CategoryModel
import com.example.pqstore.model.ImageModel
import com.example.pqstore.model.Message
import com.example.pqstore.model.OrderModel
import com.example.pqstore.model.ProductModel
import com.example.pqstore.model.SliderModel
import com.example.pqstore.model.UserAddressModel
import com.example.pqstore.model.UserModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {
    @FormUrlEncoded
    @POST("/api/controllers/user.php")
    fun authentication(
        @Field("action") action: String,
        @Field("uid") uid: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("avatar") avatar: String
    ): Call<Message>

    @FormUrlEncoded
    @POST("/api/controllers/user.php")
    fun createAddress(
        @Field("action") action: String,
        @Field("uid") uid: String,
        @Field("name") name: String,
        @Field("address") address: String,
        @Field("street") street: String,
        @Field("phone") phone: String,
        @Field("addressId") id: Int
    ): Call<Message>
    @FormUrlEncoded
    @POST("/api/controllers/user.php")
    fun deleteAddress(
        @Field("action") action: String,
        @Field("addressId") id: Int,
    ): Call<Message>

    @FormUrlEncoded
    @POST("/api/controllers/user.php")
    fun addToFavorite(@Field("action") action: String, @Field("uid") uid: String, @Field("product_id") productId: Int): Call<Message>


    @GET("/api/controllers/user.php?action=cart")
    fun getCarts(@Query("uid") userUid: String): Call<List<CartModel>>
    @FormUrlEncoded
    @POST("/api/controllers/user.php")
    fun addToCart(
        @Field("action") action: String,
      @Field("uid") uid: String,
      @Field("product_id") productId: Int,
      @Field("size") size: String
    ): Call<Message>
    @FormUrlEncoded
    @POST("/api/controllers/user.php")
    fun cartItemQuantity(
        @Field("action") action: String,
        @Field("cartItemId") cartItemId: Int,
        @Field("quantity") quantity: Int,
    ): Call<Message>
    @FormUrlEncoded
    @POST("/api/controllers/user.php")
    fun deleteCart(
        @Field("action") action: String,
        @Field("uid") uid: String,
    ): Call<Message>

    @GET("/api/controllers/home.php?action=slideshows")
    fun getSliders(): Call<List<ImageModel>>

    @GET("/api/controllers/home.php?action=suggestCategories")
    fun getCategories(): Call<List<CategoryModel>>

    @GET("/api/controllers/product.php?action=search")
    fun search(@Query("uid") userUid: String, @Query("searchKey") searchKey: String): Call<List<ProductModel>>

    @GET("/api/controllers/product.php?action=spotlight")
    fun getPopulars(@Query("uid") userUid: String): Call<List<ProductModel>>

    @GET("/api/controllers/product.php?action=catalogProducts")
    fun getCatalogProducts(@Query("uid") userUid: String, @Query("cateId") cateId: Int): Call<List<ProductModel>>

    @GET("/api/controllers/user.php?action=favorite")
    fun getFavorites(@Query("uid") userUid: String): Call<List<ProductModel>>


    @GET("/api/controllers/product.php?action=detail")
    fun getProductDetail(@Query("id") id: Int): Call<ProductModel>


    @GET("/api/controllers/user.php?action=address")
    fun userAddress(@Query("uid") userUid: String): Call<List<UserAddressModel>>
    @GET("/api/controllers/address.php?action=tinh_tp")
    fun getProvinces(): Call<List<AddressModel>>
    @GET("/api/controllers/address.php?action=quan_huyen")
    fun getDistricts(@Query("parentId") parentId: String): Call<List<AddressModel>>
    @GET("/api/controllers/address.php?action=xa_phuong")
    fun getCommuneWards(@Query("parentId") parentId: String): Call<List<AddressModel>>

    @FormUrlEncoded
    @POST("/api/controllers/user.php")
    fun createOrder(
        @Field("action") action: String,
        @Field("uid") uid: String,
        @Field("addressId") addressId: Int,
        @Field("paymentMethods") paymentMethods: String,
        @Field("note") note: String
    ): Call<Message>

    @GET("/api/controllers/user.php?action=order")
    fun getOrders(@Query("uid") userUid: String, @Query("order_status") orderStatus: String): Call<List<OrderModel>>
}