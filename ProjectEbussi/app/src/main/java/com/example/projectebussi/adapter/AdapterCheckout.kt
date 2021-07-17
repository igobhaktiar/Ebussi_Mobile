package com.example.projectebussi.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectebussi.R
import com.example.projectebussi.helper.Helper
import com.example.projectebussi.model.Produk
import com.example.projectebussi.room.MyDatabase
import com.example.projectebussi.util.Config
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class AdapterCheckout(var data:ArrayList<Produk>):RecyclerView.Adapter<AdapterCheckout.Holder>() {

    class Holder(view: View):RecyclerView.ViewHolder(view){
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val tvJumlah = view.findViewById<TextView>(R.id.tv_jumlah)
        val imgProduk = view.findViewById<ImageView>(R.id.img_produk)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_checkout, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val produk = data[position]
        val harga = produk.harga_produk

        holder.tvNama.text = produk.nama_produk
        holder.tvHarga.text = Helper().gantiRupiah(harga * produk.jumlah)

        var jumlah  = data[position].jumlah
        holder.tvJumlah.text = jumlah.toString()

        val image = Config.produkUrl + data[position].foto_produk
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.wortel)
            .error(R.drawable.wortel)
            .into(holder.imgProduk)
    }


    override fun getItemCount(): Int {
        return data.size
    }

}