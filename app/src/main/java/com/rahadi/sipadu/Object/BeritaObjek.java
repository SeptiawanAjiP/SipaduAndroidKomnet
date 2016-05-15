package com.rahadi.sipadu.Object;

import java.util.Date;

/**
 * Created by Septiawan Aji on 5/13/2016.
 */
public class BeritaObjek {
    private String kode_berita;
    private String judul;
    private String unit_kerja;
    private String hari;
    private String tanggal;
    private String konten;

    public String getKode_berita() {
        return kode_berita;
    }

    public void setKode_berita(String kode_berita) {
        this.kode_berita = kode_berita;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getUnit_kerja() {
        return unit_kerja;
    }

    public void setUnit_kerja(String unit_kerja) {
        this.unit_kerja = unit_kerja;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKonten() {
        return konten;
    }

    public void setKonten(String konten) {
        this.konten = konten;
    }
}
