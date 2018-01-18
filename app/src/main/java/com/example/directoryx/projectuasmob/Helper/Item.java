package com.example.directoryx.projectuasmob.Helper;

/**
 * Created by directoryx on 18/12/17.
 */

public class Item{

    private String nama,harga,unit,id;
    private int jumlahbeli;





    public Item(String id,String nama, String harga, String unit,int jumlahbeli){
        this.nama = nama;
        this.harga = harga;
        this.unit = unit;
        this.id = id;
        this.jumlahbeli = jumlahbeli;

    }

    public String getNama() {
        return nama;
    }

    public int getJumlahbeli() {
        return jumlahbeli;
    }

    public void setJumlahbeli(int jumlahbeli) {
        this.jumlahbeli = jumlahbeli;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


}
