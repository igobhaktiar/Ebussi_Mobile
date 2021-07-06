package com.example.projectebussi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.projectebussi.fragments.UserApi
import kotlinx.android.synthetic.main.login_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initAction()

        val daftar = findViewById<TextView>(R.id.daftar)

        daftar.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

    }

    fun initAction(){
        loginButton.setOnClickListener{
            login()
        }
    }

    fun login(){
        val request = UserRequest()
        request.email = editTextEmail.text.toString().trim()
        request.password = editTextPassword.text.toString().trim()

        val retro = Retro().getRetroClientInstance().create(UserApi::class.java)
        retro.login(request).enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val user = response.body()
                user!!.data?.token?.let { Log.e("token", it) }
                user!!.data?.email?.let { Log.e("token", it) }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                t.message?.let { Log.e("Error", it) }
            }

        })
    }
}