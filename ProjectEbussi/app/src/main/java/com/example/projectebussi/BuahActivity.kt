package com.example.projectebussi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.example.projectebussi.adapter.AdapterBuah
import com.example.projectebussi.adapter.AdapterHistory
import com.example.projectebussi.app.ApiConfig
import com.example.projectebussi.helper.Helper
import com.example.projectebussi.model.Buah
import com.example.projectebussi.model.ResponModel
import com.example.projectebussi.model.Transaksi
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_buah.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuahActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buah)
        Helper().setToolbar(this, toolbar, "Kategori Buah")

        getListBuah()
//        refresh()
    }

//    private fun refresh() {
//        refreshBuah.setOnRefreshListener {
//            getBuah()
//            refreshBuah.isRefreshing = false
//        }
//    }

    fun displayBuah(buahs: ArrayList<Buah>){

//        grid produk
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.orientation = GridLayoutManager.VERTICAL

        rv_buah.adapter = AdapterBuah(buahs)
        rv_buah.layoutManager = layoutManager

    }
    fun getListBuah(){
        ApiConfig.instanceRetrofit.getBuah().enqueue(object : Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>){
                val res = response.body()!!
                if(res.success == 1){
                    displayBuah(res.buahs)
                }
            }
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
            }
        })
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}