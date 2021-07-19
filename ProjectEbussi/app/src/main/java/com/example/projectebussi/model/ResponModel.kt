package com.example.projectebussi.model

class ResponModel {

    var success = 0
    lateinit var message:String
    var user = User()
    var produks:ArrayList<Produk> = ArrayList()

    var transaksis: ArrayList<Transaksi> = ArrayList()
    var transaksi = Transaksi()
}