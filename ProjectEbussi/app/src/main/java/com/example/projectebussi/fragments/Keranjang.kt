package com.example.projectebussi.fragments

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectebussi.MainActivity
import com.example.projectebussi.R
import com.example.projectebussi.adapter.AdapterKeranjang
import com.example.projectebussi.app.ApiConfig
import com.example.projectebussi.helper.Helper
import com.example.projectebussi.helper.SharedPref
import com.example.projectebussi.model.Checkout
import com.example.projectebussi.model.Produk
import com.example.projectebussi.model.ResponModel
import com.example.projectebussi.room.MyDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_keranjang.*
import kotlinx.android.synthetic.main.login_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Keranjang : Fragment() {

    lateinit var myDB : MyDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,): View? {

        val view: View = inflater.inflate(R.layout.fragment_keranjang, container, false)
        init(view)
        myDB = MyDatabase.getInstance(requireActivity())!!
        mainButton()


        return view
    }

    lateinit var adapter: AdapterKeranjang
    var listProduk = ArrayList<Produk>()
    private fun displayProduk(){

        listProduk = myDB!!.daoKeranjang().getAll() as ArrayList

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

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
    var totalHarga = 0
    fun hitungTotal(){
        val listProduk = myDB.daoKeranjang().getAll() as ArrayList
        totalHarga = 0
        var isSelectedAll = true
        for (produk in listProduk){
            if (produk.selected) {
                totalHarga += (produk.harga_produk * produk.jumlah)
            } else{
                isSelectedAll = false
            }
        }
        cbAll.isChecked = isSelectedAll
        tvTotal.text = Helper().gantiRupiah(totalHarga)
    }

    private fun mainButton(){
        btnDelete.setOnClickListener {
            val listDelete = ArrayList<Produk>()
            for (p in listProduk) {
                if (p.selected) listDelete.add(p)
            }
            delete(listDelete)

        }

        btnBayar.setOnClickListener {
            var isThereProduk = false

            for (p in listProduk) {
                if (p.selected) isThereProduk = true
            }
            if (isThereProduk) {
                bayar()
            } else {
                Toast.makeText(requireContext(), "Tidak ada produk yg terpilih", Toast.LENGTH_SHORT).show()
        }



        }
        cbAll.setOnClickListener {
            for (i in listProduk.indices){
                val produk = listProduk[i]
                produk.selected = cbAll.isChecked
                listProduk[i] = produk
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun bayar() {
        val user = SharedPref(requireActivity()).getUser()!!

        val listProduk = myDB.daoKeranjang().getAll() as ArrayList
        var totalItem = 0
        var totalHarga = 0
        val produks = ArrayList<Checkout.Item>()
        for (p in listProduk) {
            if (p.selected) {
                totalItem += p.jumlah
                totalHarga += (p.jumlah * p.harga_produk)

                val produk = Checkout.Item()
                produk.id = "" + p.id
                produk.jumlah = "" + p.jumlah
                produk.jumlah_harga = "" + (p.jumlah * p.harga_produk)

                produks.add(produk)
            }
        }
        val checkout = Checkout()
        checkout.user_id = "" + user.id
        checkout.jumlah = "" + totalItem
        checkout.jumlah_harga = "" + totalHarga
        checkout.produks = produks

        ll.visibility = View.VISIBLE
        ApiConfig.instanceRetrofit.checkout(checkout).enqueue(object : Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val respon =response.body()!!

                if (respon.success == 1){
                    val listDelete = ArrayList<Produk>()
                    for (p in listProduk) {
                        if (p.selected) listDelete.add(p)
                    }
                    delete(listDelete)

                    customDialog()

                    Toast.makeText(requireContext(), "Transaksi Berhasil ", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(requireContext(), "Error : "+respon.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                Toast.makeText(requireContext(), "Error : "+t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun customDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.activity_customdialog)

        val btnTutup = dialog.findViewById<Button>(R.id.btnCD)
        btnTutup.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
        dialog.show()
    }

    private fun delete(data: ArrayList<Produk>) {
        CompositeDisposable().add(Observable.fromCallable { myDB.daoKeranjang().delete(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                listProduk.clear()
                listProduk.addAll(myDB.daoKeranjang().getAll() as ArrayList)
                adapter.notifyDataSetChanged()
            })
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