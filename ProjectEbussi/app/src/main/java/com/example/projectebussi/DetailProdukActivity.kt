package com.example.projectebussi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.projectebussi.helper.Helper
import com.example.projectebussi.helper.SharedPref
import com.example.projectebussi.model.Produk
import com.example.projectebussi.room.MyDatabase
import com.example.projectebussi.util.Config
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_produk.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class DetailProdukActivity : AppCompatActivity() {

    lateinit var myDb: MyDatabase
    lateinit var produk: Produk
    private lateinit var s: SharedPref


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_produk)
        myDb = MyDatabase.getInstance(this)!! // call database
        s = SharedPref(this)

        getInfo()
        mainButton()
        checkKeranjang()
    }

    private fun mainButton(){
        btn_keranjang.setOnClickListener {
            val data = myDb.daoKeranjang().getProduk(produk.id)
            val stok = produk.stok

            if (stok <= 0){
                Toast.makeText(this, "Stok sayuran tidak tersedia", Toast.LENGTH_LONG).show()
            } else if (data == null){
                insert()
            }
            else{
                data.jumlah += 1
                update(data)
            }

        }

        btn_toKeranjang.setOnClickListener {
            if (s.getStatusLogin()) {
                val intent = Intent("event:keranjang")
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
                onBackPressed()
            }else {
                startActivity(Intent(this, login::class.java))
            }
        }
    }

    private fun insert(){
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjang().insert(produk) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respons", "data inserted")
                Toast.makeText(this, "Berhasil ditambahkan ke Keranjang", Toast.LENGTH_LONG).show()
            })
    }

    private fun update(data: Produk){
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjang().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respons", "data updated")
                Toast.makeText(this, "Berhasil update ke Keranjang", Toast.LENGTH_LONG).show()
            })
    }

    private fun checkKeranjang(){
        val datakeranjang = myDb.daoKeranjang().getAll()

        if(datakeranjang.isNotEmpty()){
            div_angka.visibility = View.VISIBLE
            tv_angka.text = datakeranjang.size.toString()
        } else{
            div_angka.visibility = View.GONE
        }
    }

    private fun getInfo(){
        val data = intent.getStringExtra("extra")
        produk = Gson().fromJson<Produk>(data, Produk::class.java)

        //set value
        tv_nama.text = produk.nama_produk
        tv_harga.text = Helper().gantiRupiah(produk.harga_produk.toString())
        tv_deskripsi.text = produk.keterangan
        detailBerat.text = produk.beratisi_produk.toString()
        detailStok.text = produk.stok.toString()

        val img = Config.produkUrl + produk.foto_produk
        Picasso.get()
            .load(img)
            .placeholder(R.drawable.wortel)
            .error(R.drawable.wortel)
            .resize(400, 400)
            .into(image)

        Helper().setToolbar(this, toolbar, produk.nama_produk)

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}