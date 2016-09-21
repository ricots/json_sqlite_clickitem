package com.appified.jsonparsingexample;

/**
 * Created by ACER on 9/17/2016.
 */
public class Jurusan {
    private String idkampus, jurusan, namaKampus;

    public Jurusan(){
    }

    public Jurusan(String idkampus, String jurusan){
        this.idkampus = idkampus;
        this.jurusan = jurusan;
    }

    public String getNamaKampus() {
        return namaKampus;
    }

    public void setNamaKampus(String namaKampus) {
        this.namaKampus = namaKampus;
    }

    public String getIdkampus() {
        return idkampus;
    }

    public void setIdkampus(String idkampus) {
        this.idkampus = idkampus;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }
}
