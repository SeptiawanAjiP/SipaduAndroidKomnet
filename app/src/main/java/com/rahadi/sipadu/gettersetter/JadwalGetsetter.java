package com.rahadi.sipadu.gettersetter;

/**
 * Created by Rahadi on 10/04/2016.
 */
public class JadwalGetsetter {
    public String getSesi() {
        return sesi;
    }

    public void setSesi(String sesi) {
        this.sesi = sesi;
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

    public String getRuang() {
        return ruang;
    }

    public void setRuang(String ruang) {
        this.ruang = ruang;
    }

    private String sesi;
    private String matkul;
    private String dosen;
    private String ruang;
}
