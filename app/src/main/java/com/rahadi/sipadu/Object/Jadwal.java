package com.rahadi.sipadu.Object;

/**
 * Created by Septiawan Aji on 5/13/2016.
 */
public class Jadwal {
    private int kode_Sesi;
    private String matkul;
    private String dosen;
    private String kode_ruang;
    private String nama_singkat;
    private String status;

    public int getKode_Sesi() {
        return kode_Sesi;
    }

    public void setKode_Sesi(int kode_Sesi) {
        this.kode_Sesi = kode_Sesi;
    }

    public String getMatkul() {
        return matkul;
    }

    public void setMatkul(String matkul) {
        this.matkul = matkul;
    }

    public String getDosen() {
        return dosen;
    }

    public void setDosen(String dosen) {
        this.dosen = dosen;
    }

    public String getKode_ruang() {
        return kode_ruang;
    }

    public void setKode_ruang(String kode_ruang) {
        this.kode_ruang = kode_ruang;
    }

    public String getNama_singkat() {
        return nama_singkat;
    }

    public void setNama_singkat(String nama_singkat) {
        this.nama_singkat = nama_singkat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
