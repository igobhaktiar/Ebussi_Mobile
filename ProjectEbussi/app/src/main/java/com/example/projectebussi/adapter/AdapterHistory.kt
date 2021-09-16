package com.example.projectebussi.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.projectebussi.DetailTransaksiActivity
import com.example.projectebussi.R
import com.example.projectebussi.helper.Helper
import com.example.projectebussi.model.Transaksi
import com.google.gson.Gson
import kotlin.collections.ArrayList

class AdapterHistory( var data: ArrayList<Transaksi>, var listener: Listeners) : RecyclerView.Adapter<AdapterHistory.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val tvTangal = view.findViewById<TextView>(R.id.tv_tgl)
        val tvJumlah = view.findViewById<TextView>(R.id.tv_jumlah)
        val tvStatus = view.findViewById<TextView>(R.id.tv_status)
        val btnDetail = view.findViewById<TextView>(R.id.btn_detail)
        val layout = view.findViewById<CardView>(R.id.layout)

    }
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        context = parent.context
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_riwayat, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val a = data[position]

        holder.tvNama.text = "AKI00" + a.id.toString()
        holder.tvHarga.text = Helper().gantiRupiah(a.jumlah_harga)
        holder.tvJumlah.text = a.user.alamat
        val color = context.getColor(R.color.menungu)
        val menungggu = context.getColor(R.color.black)
        val berhasil = context.getColor(R.color.green)
        val Status = holder.tvStatus
        if (a.status == 1) {
            Status.text = String().plus("Menunggu Konfirmasi")
            Status.setTextColor(menungggu)
        } else if (a.status == 2){
            Status.text = String().plus("Proses Pengiriman")
            Status.setTextColor(color)
        } else if (a.status == 3){
            Status.text = String().plus("Terkirim")
            Status.setTextColor(berhasil)
        }

        holder.tvTangal.text = a.tanggal_pesanan

        holder.btnDetail.setOnClickListener {
            listener.onClicked(a)
        }

    }
    interface Listeners {
        fun onClicked(data: Transaksi)
    }

}