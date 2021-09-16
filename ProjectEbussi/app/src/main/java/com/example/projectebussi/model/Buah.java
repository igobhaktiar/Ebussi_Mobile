package com.example.projectebussi.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

public class Buah implements Serializable {

    public int id;
    public String nama_produk;
    public int kategori;
    public int stok;
    public int beratisi_produk;
    public int harga_produk;
    public String foto_produk;
    public String keterangan;
    public String created_at;
    public String updated_at;

    public int jumlah = 1;
    public boolean selected;
}
