package com.rahadi.sipadu.Fragments;


import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.rahadi.sipadu.R;
import com.rahadi.sipadu.adapters.ArrayContainer;
import com.rahadi.sipadu.adapters.Nilai;
import com.rahadi.sipadu.gettersetter.NilaiGetsetter;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class NilaiHistoryFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    public NilaiHistoryFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_nilai_history, container, false);
        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        inflater.inflate(R.menu.menu_nilaihistory, menu);
        super.onCreateOptionsMenu(menu, inflater);

        //Create Spinner for this fragment only
        MenuItem item1 = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item1);
        ArrayList<String> listsemester = new ArrayList<>();

        for (int i = 1; i<=ArrayContainer.semester; i++){
            listsemester.add("Semester " + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getBaseContext(), R.layout.nilai_spinner_item, listsemester);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinner);

            // Set popupWindow height to 500px
            popupWindow.setHeight(300);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {

        }

        spinner.getBackground().setColorFilter(getResources().getColor(R.color.textColorPrimary), PorterDuff.Mode.SRC_ATOP);
        spinner.setOnItemSelectedListener(this);

        MenuItem item2 = menu.findItem(R.id.info);
        item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
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

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        setHistoryNilai(pos);
//        ActionBar ac = ((AppCompatActivity) getActivity()).getSupportActionBar();
//        ac.setTitle("Semester " + (pos+1));
    }

    public void setHistoryNilai(int semester){
        Bundle bundle = this.getArguments();
        String nim = bundle.getString("NIM");

        NilaiGetsetter nilai;
        ArrayList<NilaiGetsetter> listItem = new ArrayList<>();


        switch (semester){
            case 0:
                for (int i = 0; i < ArrayContainer.konten_nilai_history1.length; i++){
                    nilai = new NilaiGetsetter();
                    nilai.setInisial(ArrayContainer.konten_nilai_history1[i][0].substring(0, 1).toUpperCase());
                    nilai.setNamamatkul(ArrayContainer.konten_nilai_history1[i][0]);
                    nilai.setNamadosen(ArrayContainer.konten_nilai_history1[i][1]);
                    nilai.setNilai(ArrayContainer.konten_nilai_history1[i][2]);

                    listItem.add(nilai);
                }
                break;
            case 1:
                for (int i = 0; i < ArrayContainer.konten_nilai_history2.length; i++){
                    nilai = new NilaiGetsetter();
                    nilai.setInisial(ArrayContainer.konten_nilai_history2[i][0].substring(0, 1).toUpperCase());
                    nilai.setNamamatkul(ArrayContainer.konten_nilai_history2[i][0]);
                    nilai.setNamadosen(ArrayContainer.konten_nilai_history2[i][1]);
                    nilai.setNilai(ArrayContainer.konten_nilai_history2[i][2]);

                    listItem.add(nilai);
                }
                break;
            case 2:
                for (int i = 0; i < ArrayContainer.konten_nilai_history3.length; i++){
                    nilai = new NilaiGetsetter();
                    nilai.setInisial(ArrayContainer.konten_nilai_history3[i][0].substring(0, 1).toUpperCase());
                    nilai.setNamamatkul(ArrayContainer.konten_nilai_history3[i][0]);
                    nilai.setNamadosen(ArrayContainer.konten_nilai_history3[i][1]);
                    nilai.setNilai(ArrayContainer.konten_nilai_history3[i][2]);

                    listItem.add(nilai);
                }
                break;
        }

        Nilai adapter = new Nilai(getActivity(), listItem);
        // Attach the adapter to a ListView
        ListView listHistoryNilai = (ListView)getActivity().findViewById(R.id.lv_historynilai);
        TextView emptyfiller2 = (TextView) getView().findViewById(R.id.tv_empty);
        View emptyview = getActivity().findViewById(R.id.v_emptyHistoryNilai);
        emptyfiller2.setText("History Nilai Tidak Tersedia");
        listHistoryNilai.setAdapter(adapter);
        listHistoryNilai.setEmptyView(emptyview);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
