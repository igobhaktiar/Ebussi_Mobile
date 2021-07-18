package com.example.projectebussi.model

class Checkout {
    lateinit var user_id: String
    lateinit var jumlah: String
    lateinit var jumlah_harga: String

    var produks = ArrayList<Item>()

    class Item{
        lateinit var id: String
        lateinit var jumlah: String
        lateinit var jumlah_harga: String
    }

    var produk = Produk()
}