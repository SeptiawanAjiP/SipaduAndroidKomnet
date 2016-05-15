package com.rahadi.sipadu.Parser;

import android.util.Log;

import com.rahadi.sipadu.gettersetter.AbsensiGetsetter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Septiawan Aji on 5/11/2016.
 */
public class ParseDataDariServer {

    /*
    - DIgunakan pada saat user memasukan nim, mengakses webservice getNama(String nim)
    Semua data yang dikembalikan oleh server dalam bentuk STring
    sehingga perlu di parse menjadi Hasmap ataupun Array
    data dari server
            {kelas=3KS1, nama=Septiawan Aji Pradana, path_foto_profile=http://192.168.1.100/sipadu/foto.png}

    -harus diparse dulu menjadi Map
     */
    public static HashMap<String,String> convertKeMap(String asli,String entriesSeparator,String keyValueSeparator){
        String ubah = asli.replace("{", "").replace("}","");//menghilangkan tanda { dan } pada string dari properties
        HashMap<String,String> hm = new HashMap<>();
        String[] entries = ubah.split(entriesSeparator);
        for(String entry : entries){
            if(!entry.isEmpty()&&entry.contains(keyValueSeparator)){
                String[] keyValue = entry.split(keyValueSeparator);
                hm.put(keyValue[0],keyValue[1]);
            }
        }
        return hm;
    }


    /*
        untuk memparse pada menu absensi (AbsensiActivity.java),
        data yang dikembalikan server bertipe string spt ini

            [{kode_matkul=23, skor=90, nama_dosen=Ir. Agus Purwoto, nama_matkul=Aljabar Linier}, {kode_matkul=26, skor=90, nama_dosen=Farhid Rido,M.T., nama_matkul=Keamanan Jaringan}]

            -dari string diatas harus dihilangkan dulu character2 tertentu
            -untuk menampilkan dalam listview perlu di parse menjadi ArrayList<AbesnsiGetSetter> dengan method dibawah ini

            hasil keluarannya ialah array objek

     */
    public static ArrayList<AbsensiGetsetter> olahGetAbsensi(String awal){
        ArrayList listAbsensi = new ArrayList();
        AbsensiGetsetter absensi;

        String ubah = awal.replace("[","").replace("}, ","-").replace("}","").replace("{","").replace("]","").replace("kode_matkul=", "").replace("skor=", "").replace("nama_dosen=", "").replace("nama_matkul=","");
        Log.d("Ubah : ",ubah);
        String[] rows = ubah.split("\\-");

        String[][] matrix = new String[rows.length][];

        int r =0;
        for(String row : rows){
            matrix[r++] = row.split(", ");
        }
        Log.d("Rows",Integer.toString(rows.length));

        for(int i=0;i<rows.length;i++){
            absensi = new AbsensiGetsetter();
            //absensi.setKode_matkul(matrix[i][0]);
            absensi.setPersentase(matrix[i][1]);
            absensi.setDosen(matrix[i][2]);
            absensi.setMataKuliah(matrix[i][3]);
            absensi.setHurufDepan();
            listAbsensi.add(absensi);
            Log.d("List Absensi Parser", listAbsensi.toString());
        }
        int i = listAbsensi.size();
        Log.d("List Absensi:",Integer.toString(i));
        return listAbsensi;

    }
}
