package com.example.projectebussi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.register_layout.*

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val register = findViewById<TextView>(R.id.sudahdaftar)

        register.setOnClickListener {
            val intent = Intent (this, login::class.java)
            startActivity(intent)
        }

    }
}