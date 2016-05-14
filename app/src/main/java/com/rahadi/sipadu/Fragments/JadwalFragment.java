package com.rahadi.sipadu.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.rahadi.sipadu.R;
import com.rahadi.sipadu.adapters.ArrayContainer;
import com.rahadi.sipadu.adapters.Jadwal;
import com.rahadi.sipadu.gettersetter.JadwalGetsetter;

import java.util.ArrayList;
import java.util.Calendar;

public class JadwalFragment extends Fragment {

    private static final String ARG_PAGE_NUMBER = "page_number";
    private ListView lvItem;
    public ArrayList<JadwalGetsetter> listItem;

    public JadwalFragment() {

    }

    public static JadwalFragment newInstance(int page) {
        JadwalFragment fragment = new JadwalFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_jadwal, container, false);

        int page = getArguments().getInt(ARG_PAGE_NUMBER, -1);

        lvItem = (ListView)root.findViewById(R.id.list_jadwal);
        listItem = new ArrayList<>();

        JadwalGetsetter jadwal;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)+page-5000);
        int thatday = cal.get(Calendar.DAY_OF_WEEK);

        if(thatday > 1 && thatday < 7) {
            thatday -= 2;
            for (int j = 0; j < ArrayContainer.konten_jadwal[thatday].length; j++) {
                jadwal = new JadwalGetsetter();
                jadwal.setSesi(ArrayContainer.konten_jadwal[thatday][j][0]);
                jadwal.setMatkul(ArrayContainer.konten_jadwal[thatday][j][1]);
                jadwal.setDosen(ArrayContainer.konten_jadwal[thatday][j][2]);
                jadwal.setRuang(ArrayContainer.konten_jadwal[thatday][j][3]);

                listItem.add(jadwal);
            }
        }

        View emptyView;
        if (thatday == 1 || thatday == 7) {
            emptyView = root.findViewById(R.id.blank_weekend_jadwal);
        } else {
            emptyView = root.findViewById(R.id.blank_weekday_jadwal);
        }

        Jadwal adapterJadwal = new Jadwal(getActivity(), listItem);
        lvItem.setAdapter(adapterJadwal);

        emptyView.setVisibility(View.VISIBLE);
        lvItem.setEmptyView(emptyView);

        return root;
    }
}
