package com.rahadi.sipadu.UserInterface;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rahadi.sipadu.ImageLoader.ImageLoader;
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
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Septiawan Aji on 5/10/2016.
 */
public class LoginActivityPassword extends AppCompatActivity {
    EditText password_input;
    TextView nama_user;
    TextView kelas_user;
    CircleImageView circleImageView;
    Button next_2,back;
    String password;
    ImageLoader imageLoader;

    Mahasiswa mhs = new Mahasiswa();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_password);

        Intent i = getIntent();
        mhs.setNim(i.getStringExtra(StaticFinal.getNIM()));
        mhs.setNama(i.getStringExtra(StaticFinal.getNAMA()));
        mhs.setKelas(i.getStringExtra(StaticFinal.getKELAS()));
        mhs.setPath_foto( i.getStringExtra(StaticFinal.getPathFoto()));

        //inisialisasi

        imageLoader = new ImageLoader(getApplicationContext());
        circleImageView = (CircleImageView)findViewById(R.id.profile_picture);
        nama_user = (TextView)findViewById(R.id.nama_user);
        kelas_user = (TextView)findViewById(R.id.kelas_user);
        next_2 = (Button)findViewById(R.id.next_2);
        back = (Button)findViewById(R.id.prev_1);
        TextInputLayout password_support = (TextInputLayout)findViewById(R.id.password_support);
        password_support.setHintAnimationEnabled(true);
        password_input = (EditText)findViewById(R.id.password_input);
        password_input.requestFocus();

        //set nama, kelas dan foto pada layout
        nama_user.setText(mhs.getNama());
        kelas_user.setText(mhs.getKelas());
        imageLoader.DisplayImage(mhs.getPath_foto(), circleImageView);

        next_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = password_input.getText().toString();
                mhs.setPassword(password);
                if (cekKoneksi()) {
                    if (password.isEmpty()) {
                        Toast.makeText(LoginActivityPassword.this, R.string.password_kosong, Toast.LENGTH_SHORT).show();
                    } else {
                        new LoginHomeActivity().execute();
                    }
                } else {
                    Toast.makeText(LoginActivityPassword.this, R.string.gagal_konek, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //tombol back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //class untuk memparse data yang dilemparkan server
    public class LoginHomeActivity extends AsyncTask<String,String,String> {
        ProgressDialog pDialog;
        private String respon;//hasil dari webservice
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("LoginActivityNim", "onPreExecute");
            pDialog = new ProgressDialog(LoginActivityPassword.this);
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

                SoapObject request = new SoapObject(WebServiceProperties.getNameSpace(),WebServiceProperties.getLoginAkun());


                //set parameter webservice
                request.addProperty(StaticFinal.getNIM(),mhs.getNim());
                request.addProperty(StaticFinal.getPASS(),mhs.getPassword());
                Log.d("request",request.toString());
                envelope.bodyOut = request;

                //mengakses url
                HttpTransportSE transport = new HttpTransportSE(WebServiceProperties.getURL());

                //jika menggunakan https pake ini
                //HttpsTransportSE transport = new HttpsTransportSE(AlamatUrl.getAlamatUrl(),masukan port,masukan file, masukan timeout);


                try {
                    //transport.call(WebServiceProperties.getNameSpace() + WebServiceProperties.getGetNama(),envelope);
                    transport.call(WebServiceProperties.getNameSpace()+WebServiceProperties.getLoginAkun(),envelope);

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
            Calendar calendar = Calendar.getInstance();
            String parameterTanggal;
            parameterTanggal = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH))+"-"+Integer.toString(calendar.get(Calendar.MONTH)+1)+
                    "-"+Integer.toString(calendar.get(Calendar.YEAR)).replace("20", "");
            Log.d("Format tanggal ",parameterTanggal);
            if(respon.equals("1")){
                Intent i = new Intent(getApplicationContext(),HomeActivity.class);

                //mengirim string ke HomeActivity.class
                i.putExtra(StaticFinal.getNIM(),mhs.getNim());
                i.putExtra(StaticFinal.getNAMA(),mhs.getNama());
                i.putExtra(StaticFinal.getKELAS(),mhs.getKelas());
                i.putExtra(StaticFinal.getPathFoto(), mhs.getPath_foto());
                i.putExtra(StaticFinal.getTANGGAL(),parameterTanggal);
                startActivity(i);

            }else{//bool bernilai false
                Toast.makeText(LoginActivityPassword.this, R.string.password_salah, Toast.LENGTH_SHORT).show();
            }
            //dialog loading off
            pDialog.dismiss();
        }
    }

    //method untuk cek koneksi
    public boolean cekKoneksi() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
