package com.example.projectebussi

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat.getColor
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectebussi.adapter.AdapterDetailTransaksi
import com.example.projectebussi.adapter.AdapterHistory
import com.example.projectebussi.model.DetailTransaksi
import com.example.projectebussi.model.Transaksi
import com.google.android.material.color.MaterialColors.getColor
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail_transaksi.*
import kotlinx.android.synthetic.main.activity_riwayat.*
import kotlinx.android.synthetic.main.fragment_keranjang.*
import java.util.ArrayList
import com.example.projectebussi.helper.Helper
import kotlinx.android.synthetic.main.toolbar_checkout.*

class DetailTransaksiActivity : AppCompatActivity() {

    var transaksi = Transaksi()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_transaksi)
        Helper().setToolbar(this, toolbar, "Detail Transaksi")

        val json = intent.getStringExtra("transaksi")
        transaksi = Gson().fromJson(json, Transaksi::class.java)

        setData(transaksi)
        displayProduk(transaksi.pesanan_detail)
    }

    fun displayProduk(transaksis: ArrayList<DetailTransaksi>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_produk.adapter = AdapterDetailTransaksi(transaksis)
        rv_produk.layoutManager = layoutManager
    }


    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    private fun setData(t: Transaksi) {
        tv_penerima.text = t.user.name + " - " + t.user.nohp
        tv_alamat.text = t.user.alamat
        tv_totalBelanja.text = Helper().gantiRupiah(t.jumlah_harga)
        val color = this.getColor(R.color.menungu)
        val menungggu = this.getColor(R.color.black)
        val berhasil = this.getColor(R.color.green)
        if (t.status == 1) {
            tv_status.text = String().plus("Menunggu Konfirmasi")
            tv_status.setTextColor(menungggu)
        } else if (t.status == 2){
            tv_status.text = String().plus("Pesanan Diproses")
            tv_status.setTextColor(color)
        } else if (t.status == 3){
            tv_status.text = String().plus("Pesanan Dikirim")
            tv_status.setTextColor(berhasil)
        }

        tv_tgl.text = t.tanggal_pesanan
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}