package com.example.projectebussi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projectebussi.app.ApiConfig
import com.example.projectebussi.helper.SharedPref
import com.example.projectebussi.model.ResponModel
import kotlinx.android.synthetic.main.login_layout.*
import kotlinx.android.synthetic.main.register_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class login : AppCompatActivity() {

    lateinit var s:SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        s = SharedPref(this)

        val daftar = findViewById<TextView>(R.id.daftar)

        daftar.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            Login()
        }
    }

    fun Login(){
        if (editTextEmail.text.isEmpty()){
            editTextEmail.error = "Kolom email tidak boleh kosong !"
            editTextEmail.requestFocus()
            return
        } else if (editTextPassword.text.isEmpty()){
            editTextPassword.error = "Kolom password tidak boleh kosong !"
            editTextPassword.requestFocus()
            return
        }

        pb.visibility = View.VISIBLE
        ApiConfig.instanceRetrofit.loginapi(editTextEmail.text.toString(), editTextPassword.text.toString()).enqueue(object : Callback<ResponModel> {

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                pb.visibility = View.GONE
                val respon =response.body()!!

                if (respon.success == 1){
                    s.setStatusLogin(true)
                    s.setString(s.nama, respon.user.name)
                    s.setString(s.email, respon.user.email)
                    s.setString(s.nohp, respon.user.nohp)
                    val intent = Intent(this@login, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@login, "Selamat datang "+respon.user.name, Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this@login, "Error : "+respon.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                pb.visibility = View.GONE
                Toast.makeText(this@login, "Error : "+t.message, Toast.LENGTH_SHORT).show()
            }

        })

    }
}