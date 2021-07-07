package com.example.projectebussi

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projectebussi.app.ApiConfig
import com.example.projectebussi.model.ResponModel
import kotlinx.android.synthetic.main.register_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val sudahdaftar = findViewById<TextView>(R.id.sudahdaftar)

        sudahdaftar.setOnClickListener {
            val intent = Intent (this, login::class.java)
            startActivity(intent)
        }

        buttonRegister.setOnClickListener {
            register()
        }
    }

    fun register(){
        if (edt_name.text.isEmpty()){
            edt_name.error = "Kolom tidak boleh kosong !"
            edt_name.requestFocus()
            return
        } else if (edt_username.text.isEmpty()){
            edt_username.error = "Kolom username tidak boleh kosong !"
            edt_username.requestFocus()
            return
        } else if (edt_Email.text.isEmpty()){
            edt_Email.error = "Kolom email tidak boleh kosong !"
            edt_Email.requestFocus()
            return
        } else if (edt_password.text.isEmpty()){
            edt_password.error = "Kolom password tidak boleh kosong !"
            edt_password.requestFocus()
            return
        } else if (confirmPassword.text.isEmpty()){
            confirmPassword.error = "Kolom confirm password tidak boleh kosong !"
            confirmPassword.requestFocus()
            return
        }

        ApiConfig.instanceRetrofit.register(edt_name.text.toString(), edt_username.text.toString(), edt_Email.text.toString(), edt_password.text.toString(), confirmPassword.text.toString()).enqueue(object : Callback<ResponModel>{

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {

                val respon =response.body()!!

                if (respon.success == 1){
                    Toast.makeText(this@Register, "Success : "+respon.message, Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this@Register, "Error : "+respon.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                Toast.makeText(this@Register, "Error : "+t.message, Toast.LENGTH_SHORT).show()
            }

        })

    }
}