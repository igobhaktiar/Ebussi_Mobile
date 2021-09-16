package com.example.projectebussi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.example.projectebussi.app.ApiConfig
import com.example.projectebussi.model.ResponModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        val splashTime: Long = 3000 // lama splashscreen berjalan

//        Handler().postDelayed({
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent) // Pindah ke Home Activity setelah 3 detik
//            finish()
//        }, splashTime)
        ApiConfig.instanceRetrofit.getProduk().enqueue(object : Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>){
                val res = response.body()!!
                if(res.success == 1){
                    startActivity(Intent(this@Splash, MainActivity::class.java))
                    finish()
                } else{
                    recreate()
                    Toast.makeText(this@Splash, "tidak adak koneksi internet", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                Toast.makeText(this@Splash, "tidak adak koneksi internet", Toast.LENGTH_SHORT).show()
                recreate()
            }
        })
    }
}