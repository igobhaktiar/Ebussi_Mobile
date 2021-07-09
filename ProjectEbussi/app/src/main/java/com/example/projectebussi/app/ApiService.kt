package com.example.projectebussi.app

import com.example.projectebussi.model.ResponModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

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
}