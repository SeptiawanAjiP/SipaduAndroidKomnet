package com.rahadi.sipadu.UserInterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rahadi.sipadu.R;
import com.rahadi.sipadu.adapters.ArrayContainer;
import com.rahadi.sipadu.adapters.Berita;
import com.rahadi.sipadu.gettersetter.BeritaGetsetter;

import java.util.ArrayList;


public class BeritaActivity extends AppCompatActivity {

    private ListView lvItem;
    public ArrayList<BeritaGetsetter> listItem;

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

        BeritaGetsetter berita = null;

        for (int i = 0; i < ArrayContainer.konten_berita.length; i++){
            berita = new BeritaGetsetter();
            berita.setInisial(ArrayContainer.konten_berita[i][0].substring(0, 1).toUpperCase());
            berita.setNama(ArrayContainer.konten_berita[i][0]);
            berita.setTanggal(ArrayContainer.konten_berita[i][1]);
            berita.setPengirim(ArrayContainer.konten_berita[i][2]);
            berita.setIsi(ArrayContainer.konten_berita[i][3]);

            listItem.add(berita);
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
}

