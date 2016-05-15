package com.rahadi.sipadu.UserInterface;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.Toast;

import com.rahadi.sipadu.R;
import com.rahadi.sipadu.adapters.ArrayContainer;
import com.rahadi.sipadu.adapters.JadwalPager;
import com.rahadi.sipadu.Fragments.DatePickerFragment;
import com.rahadi.sipadu.Fragments.JadwalFragment;

import java.util.Calendar;

public class JadwalActivity extends AppCompatActivity {

    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_jadwal);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_activity_jadwal);
        }

        JadwalPager adapter = new JadwalPager(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.pager_jadwal);
        pager.setAdapter(adapter);
        pager.setCurrentItem(4999);

        PagerTabStrip strip = (PagerTabStrip) findViewById(R.id.strip_jadwal);
        strip.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
        strip.setTabIndicatorColor(getResources().getColor(android.R.color.white));
        strip.setTextColor(getResources().getColor(android.R.color.white));
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_jadwal, menu);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MenuItem item = menu.findItem(R.id.reset);
                if(position != 4999) {
                    item.setVisible(true);
                } else {
                    item.setVisible(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.calendar) {
           DatePickerFragment newFragment = DatePickerFragment.getInstance();
            newFragment.show(getSupportFragmentManager(), "datePicker");
            return true;
        }else if (id == R.id.reset) {
            pager.setCurrentItem(4999);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setDate(int year, int month, int date){
        Calendar d1 = Calendar.getInstance();
        Calendar d2 = Calendar.getInstance();
        d2.set(year, month, date);
        d2.set(Calendar.MILLISECOND, d1.get(Calendar.MILLISECOND));

        long diff = d2.getTimeInMillis()- d1.getTimeInMillis();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        int delta = (int)diffDays;

        pager.setCurrentItem(4999+delta, true);
    }
}