package com.rahadi.sipadu.WebService;

/**
 * Created by Septiawan Aji on 5/11/2016.
 */

/*
    Berisi alamat url webservice dan nama2 dari method didalam nya
 */

public class WebServiceProperties {
    //url dan namespace
    private final static String URL = "http://192.168.1.100:8080/WebService/NewWebService?WSDL";
    private final static String NAME_SPACE = "http://sipadu.com/";


    //method2 yang ada pada webservice
    private final static String GET_NAMA ="getNama";
    private final static String LOGIN_AKUN = "loginAkun";
    private final static String GET_JADWAL_SAYA = "getJadwalSaya";
    private final static String GET_PENGUMUMAN = "getPengumuman";
    private final static String GET_DETAIL_PENGUMUMAN ="getDetaliPengumuman";
    private final static String GET_ABSENSI = "getAbsensi";
    private final static String GET_DETAIL_ABSENSI = "getDetailAbsensi";
    private final static String GET_NILAI = "getNilai";

    public static String getGetNama() {
        return GET_NAMA;
    }

    public static String getLoginAkun() {
        return LOGIN_AKUN;
    }

    public static String getGetJadwalSaya() {
        return GET_JADWAL_SAYA;
    }

    public static String getGetPengumuman() {
        return GET_PENGUMUMAN;
    }

    public static String getGetDetailPengumuman() {
        return GET_DETAIL_PENGUMUMAN;
    }

    public static String getGetAbsensi() {
        return GET_ABSENSI;
    }

    public static String getGetDetailAbsensi() {
        return GET_DETAIL_ABSENSI;
    }

    public static String getGetNilai() {
        return GET_NILAI;
    }

    public static String getURL() {
        return URL;
    }

    public static String getNameSpace() {
        return NAME_SPACE;
    }
}
