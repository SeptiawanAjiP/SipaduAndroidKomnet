package com.rahadi.sipadu.WebService;

/**
 * Created by Septiawan Aji on 5/14/2016.
 */

/*
    Digunakan untuk menyimpan alamat url dari api sipadu
    Pada aplikasi sipadu ini masih menggunakan api php yaitu untuk konten berita/pengumuman
 */
public class JSONApi {
    private static final String AlamatURLBerita = "http://stis.ac.id/sipadu/android/berita.php?page=1&jumlah=50&nim=";

    private static final String AlamatURLJadwal = "http://stis.ac.id/sipadu/android/jadwal.php?nim=";
       private static final String TGL = "&tgl=";

    public static String getAlamatURLBerita() {
        return AlamatURLBerita;
    }

    public static String getAlamatURLJadwal() {
        return AlamatURLJadwal;
    }

    public static String getTGL() {
        return TGL;
    }
}
