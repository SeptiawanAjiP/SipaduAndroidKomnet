package com.rahadi.sipadu.gettersetter;

import java.io.Serializable;

/**
 * Created by isfann on 4/11/2016.
 */
public class AbsensiGetsetter implements Serializable {

    private String kode_matkul;
    private String mataKuliah;
    private String dosen;
    private String persentase;
    private String hurufDepan;


    public String getMataKuliah() {
        return mataKuliah;
    }

    public void setMataKuliah(String mataKuliah) {
        this.mataKuliah = mataKuliah;
    }

    public String getDosen() {
        return dosen;
    }

    public void setDosen(String dosen) {
        this.dosen = dosen;
    }

    public String getPersenase() {
        return persentase;
    }

    public void setPersentase(String persentase) {
        this.persentase = persentase;
    }

    public String getHurufDepan() {
        return String.valueOf(this.mataKuliah.charAt(0));
    }

    public void setHurufDepan() {
        this.hurufDepan = String.valueOf(this.mataKuliah.charAt(0));
    }

    public String getKode_matkul() {
        return kode_matkul;
    }

    public void setKode_matkul(String kode_matkul) {
        this.kode_matkul = kode_matkul;
    }
}
