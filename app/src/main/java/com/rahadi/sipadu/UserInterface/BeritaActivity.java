package com.rahadi.sipadu.UserInterface;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rahadi.sipadu.Object.StaticFinal;
import com.rahadi.sipadu.R;
import com.rahadi.sipadu.adapters.ArrayContainer;
import com.rahadi.sipadu.adapters.Berita;
import com.rahadi.sipadu.gettersetter.BeritaGetsetter;

import java.io.Serializable;
import java.util.ArrayList;


public class BeritaActivity extends AppCompatActivity implements Serializable {

    private ListView lvItem;
    public ArrayList<BeritaGetsetter> listItem;
    ArrayList<ArrayList> array;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);

        lvItem = (ListView)findViewById(R.id.list_berita);
        listItem = new ArrayList<>();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_berita);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_berita);
        actionBar.setDisplayHomeAsUpEnabled(true);
        array = new ArrayList<ArrayList>();

        Intent intent = this.getIntent();//mengambil array yang di lempar home activity,karena array maka menggunakan serializable
        array = (ArrayList<ArrayList>) intent.getSerializableExtra(StaticFinal.getBERITA());

        BeritaGetsetter berita = null;
        for (int i = 0; i < array.size(); i++){
            Log.d("array size for ",Integer.toString(array.size()));
            berita = new BeritaGetsetter();


            berita.setInisial(array.get(i).get(1).toString().substring(0, 1).toUpperCase());
            berita.setNama(array.get(i).get(1).toString());
            berita.setTanggal(array.get(i).get(4).toString());
            berita.setPengirim(array.get(i).get(2).toString());
            berita.setIsi(array.get(i).get(0).toString());

            listItem.add(berita);
            Log.d("List Item Berita",listItem.toString());
        }

        Berita adapter = new Berita(BeritaActivity.this, listItem);
        lvItem.setAdapter(adapter);

        lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BeritaGetsetter mbl = listItem.get(position);

                Intent intent = new Intent(BeritaActivity.this, BeritaDetailActivity.class);
                intent.putExtra(BeritaDetailActivity.KEY_ITEM, mbl);
                startActivityForResult(intent, 0);
            }
        });

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        if (id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<ArrayList> getArray() {
        return array;
    }
    //untuk mengeset nilai arai dari class HomeActivity
    public void setArray(ArrayList<ArrayList> array) {
        this.array = array;
        Log.d("Size array di berita",Integer.toString(array.size()));
        Log.d("Array di Berita :",array.toString());
    }

    public class getListBerita extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}

