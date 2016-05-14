package com.rahadi.sipadu.UserInterface;

import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rahadi.sipadu.R;
import com.rahadi.sipadu.adapters.JadwalPager;

public class JadwalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_jadwal);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_activity_jadwal);
        }

        JadwalPager adapter = new JadwalPager(getSupportFragmentManager());
        ViewPager pager = (ViewPager)findViewById(R.id.pager_jadwal);
        pager.setAdapter(adapter);
        pager.setCurrentItem(4999);

        PagerTabStrip strip = (PagerTabStrip)findViewById(R.id.strip_jadwal);
        strip.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
        strip.setTabIndicatorColor(getResources().getColor(android.R.color.white));
        strip.setTextColor(getResources().getColor(android.R.color.white));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
