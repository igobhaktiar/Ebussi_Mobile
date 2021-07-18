package com.example.projectebussi.model

class Transaksi {
    var jumlah_harga = ""
    var user_id = ""
    var status = 0
    var tanggal_pesanan = ""
    var updated_at = ""
    var created_at = ""
    var id = 0

    val user = User()
    val pesanan_detail = ArrayList<DetailTransaksi>()
}