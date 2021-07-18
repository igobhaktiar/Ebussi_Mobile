package com.example.projectebussi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectebussi.adapter.AdapterHistory
import com.example.projectebussi.app.ApiConfig
import com.example.projectebussi.helper.Helper
import com.example.projectebussi.helper.SharedPref
import com.example.projectebussi.model.ResponModel
import com.example.projectebussi.model.Transaksi
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_riwayat.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class Riwayat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat)
        Helper().setToolbar(this, toolbar, "Riwayat Belanja")

        getHistory()
    }

    private fun getHistory() {
            val id = SharedPref(this).getUser()!!.id
            ApiConfig.instanceRetrofit.getRiwayat(id).enqueue(object : Callback<ResponModel> {
                override fun onFailure(call: Call<ResponModel>, t: Throwable) {

                }

                override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                    val res = response.body()!!
                    if (res.success == 1) {
                        displayRiwayat(res.transaksis)
                    }
                }
            })
    }
    fun displayRiwayat(transaksis: ArrayList<Transaksi>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rv_riwayat.adapter = AdapterHistory(transaksis, object : AdapterHistory.Listeners {
            override fun onClicked(data: Transaksi) {
                val json = Gson().toJson(data, Transaksi::class.java)
                val intent = Intent(this@Riwayat, DetailTransaksiActivity::class.java)
                intent.putExtra("transaksi", json)
                startActivity(intent)
            }
        })
        rv_riwayat.layoutManager = layoutManager
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}