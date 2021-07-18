package com.example.projectebussi.app

import com.example.projectebussi.model.Checkout
import com.example.projectebussi.model.ResponModel
import retrofit2.Call
import retrofit2.http.*

interface  ApiService {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("nohp") nohp: String,
        @Field("alamat") alamat: String,
        @Field("password") password: String,
        @Field("confirm_password") confirm_password: String
    ): retrofit2.Call<ResponModel>

    @FormUrlEncoded
    @POST("login")
    fun loginapi(
        @Field("email") email: String,
        @Field("password") password: String,
    ): retrofit2.Call<ResponModel>

    @POST("checkout")
    fun checkout(
        @Body data: Checkout
    ): Call<ResponModel>

    @GET("checkout/user/{id}")
    fun getRiwayat(
        @Path("id") id: Int
    ): Call<ResponModel>

    @GET("produk")
    fun getProduk():retrofit2.Call<ResponModel>


}
