package com.rahadi.sipadu.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.rahadi.sipadu.R;
import com.rahadi.sipadu.adapters.ArrayContainer;
import com.rahadi.sipadu.adapters.Nilai;
import com.rahadi.sipadu.gettersetter.NilaiGetsetter;


import java.util.ArrayList;


public class NilaiAktifFragment extends Fragment {

    private String nim;
    private ArrayList<NilaiGetsetter> listItem;

    public NilaiAktifFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nilai_aktif, container, false);

        Bundle bundle = this.getArguments();
        String nim = bundle.getString("NIM");

        listItem = new ArrayList<>();
        NilaiGetsetter nilai;

        for (int i = 0; i < ArrayContainer.konten_nilai_aktif.length; i++){
            nilai = new NilaiGetsetter();
            nilai.setInisial(ArrayContainer.konten_nilai_aktif[i][0].substring(0, 1).toUpperCase());
            nilai.setNamamatkul(ArrayContainer.konten_nilai_aktif[i][0]);
            nilai.setNamadosen(ArrayContainer.konten_nilai_aktif[i][1]);
            nilai.setNilai(ArrayContainer.konten_nilai_aktif[i][2]);

            listItem.add(nilai);
        }

        Nilai adapter = new Nilai(getActivity(), listItem);
        // Attach the adapter to a ListView
        ListView listNilaiAktif = (ListView) v.findViewById(R.id.lv_nilaiaktif);
        TextView emptyfiller = (TextView) v.findViewById(R.id.tv_empty);
        View view = v.findViewById(R.id.v_emptySemesterAktif);
        emptyfiller.setText("Daftar Nilai Belum Tersedia");

        listNilaiAktif.setAdapter(adapter);
        listNilaiAktif.setEmptyView(view);
        
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        inflater.inflate(R.menu.menu_nilaiaktif, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item1 = menu.findItem(R.id.info);
        item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                View info = getActivity().getLayoutInflater().inflate(R.layout.nilai_info, null);
                dialogBuilder.setView(info);
                final AlertDialog info_nilai = dialogBuilder.create();
                Button close = (Button)info.findViewById(R.id.info_btn);
                close.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        info_nilai.dismiss();
                    }
                });
                info_nilai.show();
                return true;
            }
        });
    }
}
