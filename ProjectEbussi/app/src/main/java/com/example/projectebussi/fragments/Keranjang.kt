package com.example.projectebussi.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectebussi.R
import com.example.projectebussi.adapter.AdapterKeranjang
import com.example.projectebussi.helper.Helper
import com.example.projectebussi.room.MyDatabase

class Keranjang : Fragment() {

    lateinit var myDB : MyDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,): View? {

        val view: View = inflater.inflate(R.layout.fragment_keranjang, container, false)
        init(view)
        myDB = MyDatabase.getInstance(requireActivity())!!
        mainButton()


        return view
    }

    private fun displayProduk(){

        val listProduk = myDB!!.daoKeranjang().getAll() as ArrayList

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        lateinit var adapter: AdapterKeranjang

        adapter = AdapterKeranjang(requireActivity(),listProduk, object : AdapterKeranjang.Listeners {
            override fun onUpdate() {
                hitungTotal()
            }

            override fun onDelete(position: Int) {
                listProduk.removeAt(position)
                adapter.notifyDataSetChanged()
                hitungTotal()
            }

        })

        rvKeranjang.adapter = adapter
        rvKeranjang.layoutManager = layoutManager
    }

    fun hitungTotal(){
        val listProduk = myDB!!.daoKeranjang().getAll() as ArrayList

        var totalHarga = 0
        for (produk in listProduk){
            totalHarga += (produk.harga_produk * produk.jumlah)
        }
        tvTotal.text = Helper().gantiRupiah(totalHarga)
    }

    private fun mainButton(){
        btnDelete.setOnClickListener {

        }

        btnBayar.setOnClickListener {

        }
    }


    lateinit var btnDelete: ImageView
    lateinit var rvKeranjang: RecyclerView
    lateinit var tvTotal : TextView
    lateinit var btnBayar : Button
    lateinit var cbAll : CheckBox

    private fun init(view: View) {
        btnDelete = view.findViewById(R.id.btn_delete)
        rvKeranjang = view.findViewById(R.id.rv_keranjang)
        tvTotal= view.findViewById(R.id.tv_total)
        btnBayar = view.findViewById(R.id.btn_bayar)
        cbAll = view.findViewById(R.id.cb_all)
    }

    override fun onResume() {
        displayProduk()
        hitungTotal()
        super.onResume()
    }

}