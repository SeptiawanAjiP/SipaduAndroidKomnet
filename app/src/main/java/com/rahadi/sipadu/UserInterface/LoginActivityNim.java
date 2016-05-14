package com.rahadi.sipadu.UserInterface;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.rahadi.sipadu.Parser.ParseDataDariServer;
import com.rahadi.sipadu.R;
import com.rahadi.sipadu.Object.Mahasiswa;
import com.rahadi.sipadu.Object.StaticFinal;
import com.rahadi.sipadu.WebService.WebServiceProperties;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
/*
    Activity pada halaman pertama, saat meminta input nim
    Digunakan untuk mengakses WebService dengan parameter nim, hasil dari web service berupa Properties
        lalu di buat toString; Setelah itu string di parse ke HashMap untuk mendapatkan key dan value
 */

public class LoginActivityNim extends AppCompatActivity {

    Mahasiswa mhs = new Mahasiswa();

    EditText nim_input;
    Button next_1;
    String nim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_nim);

//        TOMBOL NEXT
        next_1 = (Button)findViewById(R.id.next_1);
        nim_input = (EditText)findViewById(R.id.nim_input);

        next_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LoginActivityNim","clicked");

                nim = nim_input.getText().toString();
                mhs.setNim(nim);
                if (cekKoneksi()) {
                    if (nim.isEmpty()) {
                        Toast.makeText(LoginActivityNim.this, R.string.nim_kosong, Toast.LENGTH_SHORT).show();
                    } else {
                        new getNamadanPathFoto().execute();
                    }
                } else {
                    Toast.makeText(LoginActivityNim.this, R.string.gagal_konek, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    //method untuk cek koneksi
    public boolean cekKoneksi() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    //class untuk memparse data yang dilemparkan server
    public class getNamadanPathFoto extends AsyncTask<String,String,String>{
        ProgressDialog pDialog;
         String respon; //yang dikembalikan webservice
        HashMap<String,String> hm = new HashMap<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("LoginActivityNim","onPreExecute");
            pDialog = new ProgressDialog(LoginActivityNim.this);
            pDialog.setMessage("Loading");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... string) {
            try{

                //mengakses webservice
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);//gunakan VER10, jika menggunakan VER12 error

                SoapObject request = new SoapObject(WebServiceProperties.getNameSpace(),WebServiceProperties.getGetNama());


                //set parameter webservice
                request.addProperty(StaticFinal.getNIM(),mhs.getNim());
                Log.d("request",request.toString());
                envelope.bodyOut = request;

                //mengakses url
                HttpTransportSE transport = new HttpTransportSE(WebServiceProperties.getURL());

                //jika menggunakan https pake ini
                //HttpsTransportSE transport = new HttpsTransportSE(AlamatUrl.getAlamatUrl(),masukan port,masukan file, masukan timeout);


                try {
                    //transport.call(WebServiceProperties.getNameSpace() + WebServiceProperties.getGetNama(),envelope);
                    transport.call(WebServiceProperties.getNameSpace()+WebServiceProperties.getGetNama(),envelope);

                }catch (IOException e){
                    e.printStackTrace();
                }catch (XmlPullParserException e){
                    e.printStackTrace();
                }

                if(envelope.bodyIn != null){
                    SoapPrimitive resultSOAP = (SoapPrimitive) ((SoapObject) envelope.bodyIn).getProperty(0);
                    respon = resultSOAP.toString();
                    Log.d("respon",respon);
                }


            }catch (Exception e){
                e.printStackTrace();
                respon = e.getMessage();
            }
            return respon;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            hm = ParseDataDariServer.convertKeMap(respon, ", ", "=");

            //set nama,kelas dan path foto
            mhs.setNama(hm.get(StaticFinal.getNAMA()));
            mhs.setKelas(hm.get(StaticFinal.getKELAS()));
            mhs.setPath_foto(hm.get(StaticFinal.getPathFoto()));

            if(hm.isEmpty()){
                Toast.makeText(LoginActivityNim.this, R.string.nim_tidak_ada, Toast.LENGTH_SHORT).show();
            }else{
                Intent i = new Intent(getApplicationContext(),LoginActivityPassword.class);
                i.putExtra(StaticFinal.getNIM(),mhs.getNim());
                i.putExtra(StaticFinal.getNAMA(),mhs.getNama());
                i.putExtra(StaticFinal.getKELAS(),mhs.getKelas());
                i.putExtra(StaticFinal.getPathFoto(),mhs.getPath_foto());
                startActivity(i);
            }

            //}
            //dialog loading off
            pDialog.dismiss();
        }
    }
}
