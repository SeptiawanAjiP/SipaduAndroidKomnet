package com.rahadi.sipadu.UserInterface;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.rahadi.sipadu.ImageLoader.ImageLoader;
import com.rahadi.sipadu.Object.Mahasiswa;
import com.rahadi.sipadu.Object.BeritaObjek;
import com.rahadi.sipadu.Object.StaticFinal;
import com.rahadi.sipadu.R;
import com.rahadi.sipadu.WebService.JSONApi;
import com.rahadi.sipadu.WebService.JSONParser;
import com.rahadi.sipadu.adapters.ArrayContainer;
import com.rahadi.sipadu.adapters.Berita;
import com.rahadi.sipadu.adapters.Jadwal;
import com.rahadi.sipadu.gettersetter.BeritaGetsetter;
import com.rahadi.sipadu.gettersetter.JadwalGetsetter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
/*
    Menangani layout home,berisi beberapa aktivitas ke server spt : getBerita,getJadwal

    *catatan--aji :
    1. (next) Gugnakan share preference untuk menyimpan nim,nama,kelas
    2. (next) seharusnya dipisah tiap aktivitas request ke servernya
    3. (next) berita masih request ke API php stis.ac.id/sipadu/android/...
    4. (next) untuk list berita seharusnya dibuat pagination,untuk sementara
        berita yang direquest sebanyak 50 (StaticFinal.getIsiJumlah)
 */

public class HomeActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {
    Mahasiswa mhs = new Mahasiswa();
    ImageLoader imageLoader;
    TextView userNameHome,kelasHome;
    ArrayList<ArrayList> arr2;
    private ListView jadwal_overview, berita_overview;
    private ArrayList<JadwalGetsetter> jadwal_overview_array;
    private ArrayList<BeritaGetsetter> berita_overview_array;
    private ObservableScrollView mScrollView;
    private int parallaxHeight;
    private DisplayMetrics displayMetrics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent intent = getIntent();
        mhs.setNim(intent.getStringExtra(StaticFinal.getNIM()));
        mhs.setNama(intent.getStringExtra(StaticFinal.getNAMA()));
        mhs.setKelas(intent.getStringExtra(StaticFinal.getKELAS()));
        mhs.setPath_foto(intent.getStringExtra(StaticFinal.getPathFoto()));

        if(toolbar.getOverflowIcon() != null) {
            toolbar.getOverflowIcon().setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
        }

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.colorPrimary)));
        mScrollView = (ObservableScrollView)findViewById(R.id.scroller);
        mScrollView.setScrollViewCallbacks(this);

        parallaxHeight = getResources().getDimensionPixelSize(R.dimen.parallax_item);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        Button[] buttons = new Button[4];
        buttons[0] = (Button)findViewById(R.id.button_absen);
        buttons[1] = (Button)findViewById(R.id.button_nilai);
        buttons[2] = (Button)findViewById(R.id.button_dashboard);
        buttons[3] = (Button)findViewById(R.id.button_sic);

        for (Button button : buttons) {
            setButtonBackgroundColor(button);
        }

        TextView tanggaljadwal = (TextView)findViewById(R.id.tanggal_jadwal);
        tanggaljadwal.setText(getToday("JADWAL_OVERVIEW"));
        jadwal_overview = (ListView)findViewById(R.id.list_jadwal_overview);
        jadwal_overview_array = new ArrayList<>();

        JadwalGetsetter jadwal = null;

        Calendar calendar = Calendar.getInstance();
        int today  = calendar.get(Calendar.DAY_OF_WEEK);

        if(today > 1 && today < 7) {
            today -= 2;
            for (int j = 0; j < ArrayContainer.konten_jadwal[today].length; j++) {
                jadwal = new JadwalGetsetter();
                jadwal.setSesi(ArrayContainer.konten_jadwal[today][j][0]);
                jadwal.setMatkul(ArrayContainer.konten_jadwal[today][j][1]);
                jadwal.setDosen(ArrayContainer.konten_jadwal[today][j][2]);
                jadwal.setRuang(ArrayContainer.konten_jadwal[today][j][3]);

                jadwal_overview_array.add(jadwal);
            }
        }

        Jadwal adapterJadwal = new Jadwal(HomeActivity.this, jadwal_overview_array);
        jadwal_overview.setAdapter(adapterJadwal);

        View emptyView;
        if (today == 1 || today == 7) {
            emptyView = findViewById(R.id.jadwal_empty_weekend);
        } else {
            emptyView = findViewById(R.id.jadwal_empty_weekday);
        }
        jadwal_overview.setEmptyView(emptyView);

        new getListBerita().execute();

        if(!jadwal_overview_array.isEmpty()) {
            setListViewHeight(jadwal_overview);
        }

        userNameHome = (TextView)findViewById(R.id.username_home);
        kelasHome = (TextView)findViewById(R.id.kelas_home);

        CircleImageView circleImageView = (CircleImageView)findViewById(R.id.detail_userpic);

        userNameHome.setText(mhs.getNama());
        kelasHome.setText(mhs.getKelas());

        imageLoader = new ImageLoader(getApplicationContext());
        imageLoader.DisplayImage(mhs.getPath_foto(), circleImageView);

        CardView beritaOverview = (CardView)findViewById(R.id.card_view_berita_overview);
        beritaOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, BeritaActivity.class);
                startActivity(i);
            }
        });



        CardView jadwalOverview = (CardView)findViewById(R.id.card_view_jadwal_overview);
        jadwalOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, JadwalActivity.class);
                startActivity(i);
            }
        });

        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, AbsensiActivity.class);
                i.putExtra(StaticFinal.getNIM(), mhs.getNim());

                i.putExtra(StaticFinal.getKELAS(), mhs.getKelas());

                startActivity(i);
            }
        });

        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, NilaiActivity.class);
                startActivity(i);
            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if(id == R.id.action_logout) {
            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout() {
        Intent i = new Intent(HomeActivity.this, LoginActivityNim.class);
        SharedPreferences preferences = getSharedPreferences("INPUT_NIM", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("INPUT_NIM", false);
        editor.apply();
        preferences = getSharedPreferences("LOGIN_FLAG", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putBoolean("LOGIN_FLAG", false);
        editor.apply();
        startActivity(i);
        finish();
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if(listAdapter == null) {
            return;
        }
        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for(int i = 0; i<listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if(i==0) view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public void setButtonBackgroundColor(Button button) {
        button.getBackground().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_OVER);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        float alpha = Math.min(1, (float) scrollY / (parallaxHeight - toolbar.getHeight()));

        float alpha2 = ScrollUtils.getFloat(scrollY / (parallaxHeight - toolbar.getHeight()), 0, 1);
        toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha2, getResources().getColor(R.color.colorPrimary)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(getActionBar() != null) {
                getActionBar().setElevation(alpha2);
            }
        }

        View tinthead = findViewById(R.id.tint_head);
        tinthead.setMinimumHeight(parallaxHeight);
        tinthead.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, getResources().getColor(R.color.colorPrimary)));
        tinthead.setTranslationY(0);

        View parallaxContent = findViewById(R.id.konten_parallax);
        parallaxContent.setTranslationY(scrollY / 2);

        CircleImageView circleImageView = (CircleImageView)findViewById(R.id.detail_userpic);
        circleImageView.setAlpha(Math.max(1 - alpha * 3, 0));
        circleImageView.setTranslationY(-scrollY);

        TextView uname = (TextView) findViewById(R.id.username_home);
        TextView kelas = (TextView) findViewById(R.id.kelas_home);

        View konten = findViewById(R.id.konten_home);

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        konten.setMinimumHeight(displayMetrics.heightPixels-toolbar.getHeight());

        int screenWidth = displayMetrics.widthPixels;
        int unameWidth = uname.getWidth();
        int unameSpace = (screenWidth - unameWidth)/2;

        int kelasWidth = kelas.getWidth();
        int kelasSpace = (screenWidth - kelasWidth) / 2;

        float marginDefault = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);

        float maxUnameY = (float)(parallaxHeight - toolbar.getHeight() - 0.3*marginDefault);
        int adjustedUnameY = (int) ScrollUtils.getFloat(scrollY, 0, maxUnameY);
        float maxKelasY = (float)(parallaxHeight - toolbar.getHeight());
        int adjustedKelasY = (int) ScrollUtils.getFloat(scrollY, 0, maxKelasY);

        uname.setTranslationY(-adjustedUnameY);
        kelas.setTranslationY(-adjustedKelasY);

        uname.setPivotX(0);
        uname.setPivotY(0);
        kelas.setPivotX(0);
        kelas.setPivotY(0);

        int tempUnameX = (int) ScrollUtils.getFloat(scrollY - (parallaxHeight / 2), 0, parallaxHeight);
        int tempKelasX = (int) ScrollUtils.getFloat(scrollY - (parallaxHeight / 2), 0, (kelasSpace));
        int adjustedUnameX = (int) ScrollUtils.getFloat(((unameSpace - marginDefault) / uname.getY()) * tempUnameX, 0, (unameSpace - marginDefault));
        int adjustedKelasX = (int) ScrollUtils.getFloat(((kelasSpace + toolbar.getHeight() + marginDefault) / kelas.getY()) * tempKelasX, 0, (kelasSpace - marginDefault));
        uname.setTranslationX(-adjustedUnameX);
        kelas.setTranslationX(-adjustedKelasX);

        float scaler =((float)parallaxHeight - (adjustedUnameY-(parallaxHeight/2)))/parallaxHeight;
        float scale = ScrollUtils.getFloat(scaler, (float) 0.9, 1);
        uname.setScaleX(scale);
        uname.setScaleY(scale);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);
    }

    public String getToday(String usage) {
        String day, month, tanggal;
        Calendar calendar = Calendar.getInstance();

        tanggal = null;

        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            day = "Senin";
        } else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            day = "Selasa";
        } else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            day = "Rabu";
        } else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            day = "Kamis";
        } else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            day = "Jumat";
        } else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            day = "Sabtu";
        } else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            day = "Minggu";
        } else {
            day = "";
        }

        if(calendar.get(Calendar.MONTH) == Calendar.JANUARY) {
            month = "Januari";
        } else if(calendar.get(Calendar.MONTH) == Calendar.FEBRUARY) {
            month = "Februari";
        } else if(calendar.get(Calendar.MONTH) == Calendar.MARCH) {
            month = "Maret";
        } else if(calendar.get(Calendar.MONTH) == Calendar.APRIL) {
            month = "April";
        } else if(calendar.get(Calendar.MONTH) == Calendar.MAY) {
            month = "Mei";
        } else if(calendar.get(Calendar.MONTH) == Calendar.JUNE) {
            month = "Juni";
        } else if(calendar.get(Calendar.MONTH) == Calendar.JULY) {
            month = "Juli";
        } else if(calendar.get(Calendar.MONTH) == Calendar.AUGUST) {
            month = "Agustus";
        } else if(calendar.get(Calendar.MONTH) == Calendar.SEPTEMBER) {
            month = "September";
        } else if(calendar.get(Calendar.MONTH) == Calendar.OCTOBER) {
            month = "Oktober";
        } else if(calendar.get(Calendar.MONTH) == Calendar.NOVEMBER) {
            month = "November";
        } else if(calendar.get(Calendar.MONTH) == Calendar.DECEMBER) {
            month = "Desember";
        } else {
            month = "";
        }
        if(usage.equals("JADWAL_OVERVIEW")) {
            tanggal = day + ", " + calendar.get(Calendar.DAY_OF_MONTH) + " " + month + " " + calendar.get(Calendar.YEAR);
        }
        return tanggal;
    }
    /*
    Digunakan untuk memparse list berita dari api php
        http://stis.ac.id/sipadu/android/berita.php?page=1&jumlah=50&nim=13.7868
        alamat url ini dituliskan di class JSONApi

        *catatan harusnya dipisah jadi adapter, jangan dijadikan 1 dlm 1 activity
     */
    public class getListBerita extends AsyncTask<String,String,String>{
        ProgressDialog pDialog;
        ArrayList<HashMap<String,String>> arrayList;
        ArrayList<String> arr1;

        BeritaObjek berita = new BeritaObjek();
        JSONObject json;
        JSONArray data;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            super.onPreExecute();
            Log.d("LoginActivityNim", "onPreExecute");
            pDialog = new ProgressDialog(HomeActivity.this);
            pDialog.setMessage("Loading");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            JSONParser jsonParser = new JSONParser();
            arr2= new ArrayList<ArrayList>();
            List<NameValuePair> parameter = new ArrayList<>();
            try {
                json = jsonParser.getJSONFromUrl(JSONApi.getAlamatUrlPhp() + mhs.getNim());
                data = json.getJSONArray(StaticFinal.getBERITA());

                Log.d("data :",data.toString());
                Log.d("Panjang data : ", Integer.toString(data.length()));

                for(int i = 0; i< data.length();i++){
                    arr1= new ArrayList<String>();
                    JSONObject c = data.getJSONObject(i);

                    berita.setKode_berita(c.getString(StaticFinal.getKodeBerita()));
                    berita.setJudul(c.getString(StaticFinal.getJUDUL()));
                    berita.setUnit_kerja(c.getString(StaticFinal.getUnitKerja()));

                    berita.setHari(c.getString(StaticFinal.getHARI()));
                    berita.setTanggal(c.getString(StaticFinal.getTANGGAL()));

                    arr1.add(berita.getKode_berita());
                    arr1.add(berita.getJudul());
                    arr1.add(berita.getUnit_kerja());
                    arr1.add(berita.getHari());
                    arr1.add(berita.getTanggal());
                    Log.d("Array 1", arr1.toString());
                    arr2.add(arr1);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Array 2", arr2.toString());
            Log.d("Hasil :", arr2.get(1).get(0).toString());
            berita_overview = (ListView)findViewById(R.id.list_berita_overview);
            berita_overview_array = new ArrayList<>();

            BeritaGetsetter berita = null;
            int jumlah_berita_overview = 5;

            for(int i = 0; i<jumlah_berita_overview; i++) {
                berita = new BeritaGetsetter();
                berita.setInisial(arr2.get(i).get(1).toString().substring(0,1).toUpperCase());
                berita.setNama(arr2.get(i).get(1).toString());
                berita.setTanggal(arr2.get(i).get(4).toString());
                berita.setPengirim(arr2.get(i).get(2).toString());
                berita.setIsi(arr2.get(i).get(0).toString());

                berita_overview_array.add(berita);
            }

            Berita adapterBerita = new Berita(HomeActivity.this, berita_overview_array);
            berita_overview.setAdapter(adapterBerita);
            if(!berita_overview_array.isEmpty()) {
                setListViewHeight(berita_overview);
            }
            berita_overview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    BeritaGetsetter mbl = berita_overview_array.get(position);

                    Intent intent = new Intent(HomeActivity.this, BeritaDetailActivity.class);
                    intent.putExtra(BeritaDetailActivity.KEY_ITEM, mbl);
                    startActivityForResult(intent, 0);
                }
            });





            pDialog.dismiss();
        }
    }
}
