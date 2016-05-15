package com.rahadi.sipadu.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.rahadi.sipadu.UserInterface.JadwalActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private static int[] selectedDate;
    private static final DatePickerFragment INSTANCE = new DatePickerFragment();

    private DatePickerFragment() {
    }

    public static DatePickerFragment getInstance() {
        return INSTANCE;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, selectedDate[0], selectedDate[1], selectedDate[2]);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        JadwalActivity callingJadwal = (JadwalActivity)getActivity();
        callingJadwal.setDate(year, month, day);

        selectedDate[0] = year;
        selectedDate[1] = month;
        selectedDate[2] = day;
    }

    public void resetPicker() {
        selectedDate = new int[3];

        Calendar c = Calendar.getInstance();
        int year1 = c.get(Calendar.YEAR);
        int month1 = c.get(Calendar.MONTH);
        int day1 = c.get(Calendar.DAY_OF_MONTH);

        selectedDate[0] = year1;
        selectedDate[1] = month1;
        selectedDate[2] = day1;
    }
}