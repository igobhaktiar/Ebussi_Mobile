package com.example.projectebussi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val splashTime: Long = 3000 // lama splashscreen berjalan

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) // Pindah ke Home Activity setelah 3 detik
            finish()
        }, splashTime)
    }
}