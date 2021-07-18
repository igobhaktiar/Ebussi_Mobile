package com.example.projectebussi.helper

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import com.example.projectebussi.model.User
import com.google.gson.Gson

class SharedPref(activity: Activity) {

    val satlogin = "login"
    val nama = "nama"
    val nohp = "nohp"
    val email = "email"
    val alamat = "alamat"

    val user = "user"

    val mypref = "MAIN_PRF"
    val sp: SharedPreferences

    init {
        sp = activity.getSharedPreferences(mypref, Context.MODE_PRIVATE)
    }

    fun setStatusLogin(status: Boolean) {
        sp.edit().putBoolean(satlogin, status).apply()
    }

    fun getStatusLogin(): Boolean {
        return sp.getBoolean(satlogin, false)
    }

    fun setUser(value: User){
        val data:String = Gson().toJson(value, User::class.java)
        sp.edit().putString(user, data).apply()
    }

    fun getUser(): User?  {
        val data:String = sp.getString(user, null) ?: return null
        return Gson().fromJson<User>(data, User::class.java)
    }

    fun setString(key: String, value: String){
        sp.edit().putString(key, value).apply()
    }

    fun getString(key: String): String{
        return sp.getString(key,"")!!
    }
}