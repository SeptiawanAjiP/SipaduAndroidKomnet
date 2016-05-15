package com.rahadi.sipadu.WebService;

/**
 * Created by Septiawan Aji on 5/14/2016.
 */

/*
    Digunakan untuk menyimpan alamat url dari api sipadu
    Pada aplikasi sipadu ini masih menggunakan api php yaitu untuk konten berita/pengumuman
 */
public class JSONApi {
    private static final String AlamatUrlPhp = "http://stis.ac.id/sipadu/android/berita.php?page=1&jumlah=50&nim=";

    public static String getAlamatUrlPhp() {
        return AlamatUrlPhp;
    }
}
