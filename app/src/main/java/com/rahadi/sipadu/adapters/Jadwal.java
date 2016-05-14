package com.rahadi.sipadu.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rahadi.sipadu.R;
import com.rahadi.sipadu.gettersetter.JadwalGetsetter;

import java.util.ArrayList;

/**
 * Created by Rahadi on 10/04/2016.
 */
public class Jadwal extends BaseAdapter {

    Activity activity;
    ArrayList<JadwalGetsetter> listItem;

    public Jadwal(Activity activity, ArrayList<JadwalGetsetter> listItem){
        this.activity = activity;
        this.listItem = listItem;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;

        if(view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(activity.getClass().getSimpleName().equals("HomeActivity")) {
                view = inflater.inflate(R.layout.jadwal_overview_list, null);
                holder.sesi = (TextView) view.findViewById(R.id.nomor_sesi);
                holder.matkul = (TextView) view.findViewById(R.id.mata_kuliah);
                holder.dosen = (TextView) view.findViewById(R.id.nama_dosen);
                holder.ruang = (TextView) view.findViewById(R.id.ruang_kelas);
            } else if(activity.getClass().getSimpleName().equals("JadwalActivity")) {
                view = inflater.inflate(R.layout.jadwal_list, null);
                holder.sesi = (TextView) view.findViewById(R.id.nomor_sesi_jadwal);
                holder.matkul = (TextView) view.findViewById(R.id.mata_kuliah_jadwal);
                holder.dosen = (TextView) view.findViewById(R.id.nama_dosen_jadwal);
                holder.ruang = (TextView) view.findViewById(R.id.ruang_kelas_jadwal);
            }

//            holder.matkul.setSelected(true);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        JadwalGetsetter getset = (JadwalGetsetter)getItem(position);
        holder.sesi.setText(getset.getSesi());
        holder.matkul.setText(getset.getMatkul());
        holder.dosen.setText(getset.getDosen());
        holder.ruang.setText(getset.getRuang());

        return view;
    }

    static class ViewHolder {
        TextView sesi, matkul, dosen, ruang;
    }
}
