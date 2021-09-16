package com.example.projectebussi.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectebussi.DetailProdukActivity
import com.example.projectebussi.R
import com.example.projectebussi.helper.Helper
import com.example.projectebussi.model.Produk
import com.example.projectebussi.room.MyDatabase
import com.example.projectebussi.util.Config
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.util.*

class AdapterlistProduk(var activity: Activity, var data:ArrayList<Produk>, ):RecyclerView.Adapter<AdapterlistProduk.Holder>() {



    class Holder(view: View):RecyclerView.ViewHolder(view){
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val tvStok = view.findViewById<TextView>(R.id.tv_stok)
        val berat = view.findViewById<TextView>(R.id.berat)
        val imgProduk = view.findViewById<ImageView>(R.id.img_produk)
        val layout = view.findViewById<CardView>(R.id.layout)
        val kosong = view.findViewById<TextView>(R.id.tv_kosong)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_listproduk, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvNama.text = data[position].nama_produk
        holder.berat.text = data[position].beratisi_produk.toString() + " Kg"
        holder.tvHarga.text = Helper().gantiRupiah(data[position].harga_produk.toString())
        holder.tvStok.text =  "Stok : " + data[position].stok.toString()

        val cekstok = data[position].stok

        if (cekstok <= 0){
            holder.kosong.visibility = View.VISIBLE
        } else {
            holder.kosong.visibility = View.GONE
        }

        val image = Config.produkUrl +data[position].foto_produk
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.wortel)
            .error(R.drawable.wortel)
            .into(holder.imgProduk)

        holder.layout.setOnClickListener {
            val activiti = Intent(activity, DetailProdukActivity::class.java)
            val str = Gson().toJson(data[position], Produk::class.java)
            activiti.putExtra( "extra", str)
            activity.startActivity(activiti)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}