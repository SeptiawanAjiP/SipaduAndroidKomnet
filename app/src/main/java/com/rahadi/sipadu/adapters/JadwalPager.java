package com.rahadi.sipadu.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rahadi.sipadu.Fragments.JadwalFragment;

import java.util.Calendar;

/**
 * Created by Rahadi on 11/05/2016.
 */
public class JadwalPager extends FragmentStatePagerAdapter {

    private Calendar cal;

    public JadwalPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return JadwalFragment.newInstance(position+1);
    }

    @Override
    public int getCount() {
        return 10000;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.get(Calendar.DAY_OF_MONTH)+position-4999);
        return getFormattedDay(cal);
    }

    private String getFormattedDay(Calendar calendar) {
        String day, month, tanggal;

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

        tanggal = day + ", " + calendar.get(Calendar.DAY_OF_MONTH) + " " + month + " " + calendar.get(Calendar.YEAR);
        return tanggal;
    }
}
