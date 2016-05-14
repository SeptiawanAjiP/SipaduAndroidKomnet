package com.rahadi.sipadu.UserInterface;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rahadi.sipadu.Object.Mahasiswa;
import com.rahadi.sipadu.Object.StaticFinal;
import com.rahadi.sipadu.Parser.ParseDataDariServer;
import com.rahadi.sipadu.R;
import com.rahadi.sipadu.WebService.WebServiceProperties;
import com.rahadi.sipadu.adapters.Absensi;
import com.rahadi.sipadu.gettersetter.AbsensiGetsetter;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class AbsensiActivity extends AppCompatActivity {

    //Intent intent = getIntent();

    //String nim = intent.getStringExtra("NIM");
    //String tahunAjaran = intent.getStringExtra("TA");
    //String semester = intent.getStringExtra("SEM");
    //View v = getLayoutInflater().inflate(R.layout.absensi_content_main, null);

    //EditText eNim = (EditText)v.findViewById(R.id.nim);
    //EditText eTahunAjaran = (EditText)v.findViewById(R.id.tahunAjaran);
    //EditText eSemester = (EditText)v.findViewById(R.id.semester);

    //String nim = String.valueOf(eNim.getText());
    //String tahunAjaran = String.valueOf(eTahunAjaran.getText());
    //String semester = String.valueOf(eSemester.getText());

    private ListView lvAbsensi;
    private ArrayList<AbsensiGetsetter> listAbsensi;
    private int currentPercentNumber = 0;
    Mahasiswa mhs = new Mahasiswa();

    public void animateTextView(int initialValue, int finalValue, final TextView textview, int duration) {

        ValueAnimator valueAnimator = ValueAnimator.ofInt((int)initialValue, (int)finalValue);
        valueAnimator.setDuration(duration);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                textview.setText(valueAnimator.getAnimatedValue().toString());
            }
        });
        valueAnimator.start();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        mhs.setNim(intent.getStringExtra(StaticFinal.getNIM()));

        mhs.setKelas(intent.getStringExtra(StaticFinal.getKELAS()));


        lvAbsensi = (ListView)findViewById(R.id.lvAbsensi);
        listAbsensi = new ArrayList<>();

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_activity_absensi);
        }
        if(cekKoneksi()){
            new getAbsensiDariServer().execute();
        }else{
            Toast.makeText(AbsensiActivity.this, R.string.gagal_konek, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttachedToWindow() {
        animateTextView(100, 0, (TextView) findViewById(R.id.textPersentaseBig), 3000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //method untuk cek koneksi
    public boolean cekKoneksi() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    /*
        Class untuk mengakses webservice method getAbsensi(String nim)
     */
    public class getAbsensiDariServer extends AsyncTask<String,String,String>{
        ProgressDialog pDialog;
        String respon; //yang dikembalikan webservice
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("LoginActivityNim", "onPreExecute");
            pDialog = new ProgressDialog(AbsensiActivity.this);
            pDialog.setMessage("Loading");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try{

                //mengakses webservice
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);//gunakan VER10, jika menggunakan VER12 error

                SoapObject request = new SoapObject(WebServiceProperties.getNameSpace(),WebServiceProperties.getGetAbsensi());


                // parameter webservicenya
                request.addProperty(StaticFinal.getNIM(),mhs.getNim());
                request.addProperty(StaticFinal.getKELAS(),mhs.getKelas());
                Log.d("request", request.toString());
                envelope.bodyOut = request;

                //mengakses url
                HttpTransportSE transport = new HttpTransportSE(WebServiceProperties.getURL());

                //jika menggunakan https pake ini
                //HttpsTransportSE transport = new HttpsTransportSE(AlamatUrl.getAlamatUrl(),masukan port,masukan file, masukan timeout);


                try {
                    transport.call(WebServiceProperties.getNameSpace()+WebServiceProperties.getGetAbsensi(),envelope);
                }catch (IOException e){
                    e.printStackTrace();
                }catch (XmlPullParserException e){
                    e.printStackTrace();
                }

                if(envelope.bodyIn != null){
                    SoapPrimitive resultSOAP = (SoapPrimitive) ((SoapObject) envelope.bodyIn).getProperty(0);
                    //hasil data dari server berupa String
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
            if(respon.equals("[]")){
                TextView absensi = (TextView)findViewById(R.id.textMataKuliahBig);
                absensi.setText(R.string.data_tidak_ada);
                Toast.makeText(AbsensiActivity.this, R.string.data_tidak_ada, Toast.LENGTH_SHORT).show();
            }else{
                ArrayList hasil= ParseDataDariServer.olahGetAbsensi(respon);
                Absensi adapter = new Absensi(AbsensiActivity.this, ParseDataDariServer.olahGetAbsensi(respon));
                Log.d("Hey ", ParseDataDariServer.olahGetAbsensi(respon).toString());
                lvAbsensi.setAdapter(adapter);

                lvAbsensi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        AbsensiGetsetter absensi = ParseDataDariServer.olahGetAbsensi(respon).get(position);

                        TextView textPercentageBig = (TextView) findViewById(R.id.textPersentaseBig);
                        TextView textMaatKuiahlBig = (TextView) findViewById(R.id.textMataKuliahBig);
                        TextView textDosenBig = (TextView) findViewById(R.id.textDosenBig);

                        currentPercentNumber = Integer.parseInt(textPercentageBig.getText().toString());

                        textMaatKuiahlBig.setText(absensi.getMataKuliah());
                        textDosenBig.setText(absensi.getDosen());

                        animateTextView(currentPercentNumber, Integer.parseInt(absensi.getPersenase()), (TextView) findViewById(R.id.textPersentaseBig), 1500);

                    }
                });
            }
            pDialog.dismiss();

        }
    }

}