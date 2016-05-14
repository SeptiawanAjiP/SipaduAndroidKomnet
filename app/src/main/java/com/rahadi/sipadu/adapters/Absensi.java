package com.rahadi.sipadu.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rahadi.sipadu.R;
import com.rahadi.sipadu.gettersetter.AbsensiGetsetter;

import java.util.ArrayList;

/**
 * Created by isfann on 4/11/2016.
 */
public class Absensi extends BaseAdapter {
    ArrayList<AbsensiGetsetter> listAbsensi;
    Activity activity;

    public Absensi(Activity activity, ArrayList<AbsensiGetsetter> listAbsensi){
        this.activity = activity;
        this.listAbsensi = listAbsensi;
    }

    @Override
    public int getCount(){
        return listAbsensi.size();
    }

    @Override
    public Object getItem(int position) {
        return listAbsensi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.absensi_list, null);
            holder.textDosen = (TextView)view.findViewById(R.id.textDosen);
            holder.textIconHuruf = (TextView)view.findViewById(R.id.iconHuruf);
            holder.textMataKuliah = (TextView)view.findViewById(R.id.textMataKuliah);
            holder.textPersentase = (TextView)view.findViewById(R.id.textPersentase);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }

        AbsensiGetsetter absensi = (AbsensiGetsetter) getItem(position);
        holder.textPersentase.setText(absensi.getPersenase()+"%");
        holder.textMataKuliah.setText(absensi.getMataKuliah());
        holder.textIconHuruf.setText(absensi.getHurufDepan());
        holder.textDosen.setText(absensi.getDosen());

        return view;
    }

    static class ViewHolder{
        TextView textMataKuliah, textDosen, textPersentase, textIconHuruf;
    }

}