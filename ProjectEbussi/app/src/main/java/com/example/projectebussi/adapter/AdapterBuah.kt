package com.example.projectebussi.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.projectebussi.DetailTransaksiActivity
import com.example.projectebussi.R
import com.example.projectebussi.helper.Helper
import com.example.projectebussi.model.Buah
import com.example.projectebussi.model.Transaksi
import com.example.projectebussi.util.Config
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class AdapterBuah(var data: ArrayList<Buah>) : RecyclerView.Adapter<AdapterBuah.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val tvStok = view.findViewById<TextView>(R.id.tv_stok)
        val berat = view.findViewById<TextView>(R.id.berat)
        val imgProduk = view.findViewById<ImageView>(R.id.img_produk)
        val layout = view.findViewById<CardView>(R.id.layout)
        val kosong = view.findViewById<TextView>(R.id.tv_kosong)

    }
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        context = parent.context
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_buah, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val a = data[position]
        holder.tvNama.text = a.nama_produk
        holder.berat.text = a.beratisi_produk.toString() + " Kg"
        holder.tvHarga.text = Helper().gantiRupiah(a.harga_produk.toString())
        holder.tvStok.text =  "Stok : " + a.stok.toString()

        val cekstok = a.stok

        if (cekstok <= 0){
            holder.kosong.visibility = View.VISIBLE
        } else {
            holder.kosong.visibility = View.GONE
        }

        val image = Config.produkUrl +a.foto_produk
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.wortel)
            .error(R.drawable.wortel)
            .into(holder.imgProduk)

//        holder.layout.setOnClickListener {
//            listener.onClicked(a)
//        }

    }
//    interface Listeners {
//        fun onClicked(data: Buah)
//    }

}