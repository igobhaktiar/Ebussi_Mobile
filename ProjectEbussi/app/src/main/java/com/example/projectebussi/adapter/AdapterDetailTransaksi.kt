package com.example.projectebussi.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectebussi.R
import com.example.projectebussi.helper.Helper
import com.example.projectebussi.model.DetailTransaksi
import com.example.projectebussi.util.Config
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class AdapterDetailTransaksi(var data: ArrayList<DetailTransaksi>) : RecyclerView.Adapter<AdapterDetailTransaksi.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val imgProduk = view.findViewById<ImageView>(R.id.img_produk)
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvBerat= view.findViewById<TextView>(R.id.tv_berat)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val tvTotalHarga = view.findViewById<TextView>(R.id.tv_totalHarga)
        val tvJumlah = view.findViewById<TextView>(R.id.tv_jumlah)
        val layout = view.findViewById<CardView>(R.id.layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_detail_transaksi, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val a = data[position]

        val name = a.produk.nama_produk
        val p = a.produk
        holder.tvNama.text = name
        holder.tvBerat.text = p.beratisi_produk.toString() + " Kg"
        holder.tvHarga.text = Helper().gantiRupiah(p.harga_produk)
        holder.tvTotalHarga.text = Helper().gantiRupiah(a.jumlah_harga)
        holder.tvJumlah.text = a.jumlah.toString() + " Items"

        holder.layout.setOnClickListener {
//            listener.onClicked(a)
        }

        val image = Config.produkUrl + p.foto_produk
        Picasso.get()
                .load(image)
                .placeholder(R.drawable.wortel)
                .error(R.drawable.wortel)
                .into(holder.imgProduk)
    }

    interface Listeners {
        fun onClicked(data: DetailTransaksi)
    }

}