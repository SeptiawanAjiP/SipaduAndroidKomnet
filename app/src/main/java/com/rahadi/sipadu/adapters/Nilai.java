package com.rahadi.sipadu.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rahadi.sipadu.R;
import com.rahadi.sipadu.gettersetter.NilaiGetsetter;

import java.util.ArrayList;

/**
 * Created by Rahadi on 12/04/2016.
 */
public class Nilai extends BaseAdapter {
    Activity activity;
    ArrayList<NilaiGetsetter> listItem;

    public Nilai(Activity activity, ArrayList<NilaiGetsetter> listItem){
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
            view = inflater.inflate(R.layout.nilai_list, null);
            holder.inisial = (TextView)view.findViewById(R.id.tv_kode);
            holder.namamatkul = (TextView)view.findViewById(R.id.tv_matakuliah);
            holder.namadosen = (TextView)view.findViewById(R.id.tv_dosen);
            holder.nilai = (TextView)view.findViewById(R.id.tv_nilai);
            holder.plus = (TextView)view.findViewById(R.id.tv_pos);
            holder.minus = (TextView)view.findViewById(R.id.tv_min);

//            holder.berita.setSelected(true);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        NilaiGetsetter getset = (NilaiGetsetter) getItem(position);
        holder.inisial.setText(getset.getInisial());
        holder.namamatkul.setText(getset.getNamamatkul());
        holder.namadosen.setText(getset.getNamadosen());
        holder.nilai.setText(getset.getNilai().substring(0, 1));

        holder.plus.setBackgroundColor(view.getResources().getColor(R.color.switchInactive));
        holder.minus.setBackgroundColor(view.getResources().getColor(R.color.switchInactive));


        if(getset.getNilai().length() > 1) {
            if(getset.getNilai().substring(1,2).equalsIgnoreCase("-")) {
                holder.minus.setBackgroundColor(view.getResources().getColor(R.color.colorPrimary));
            } else if(getset.getNilai().substring(1,2).equalsIgnoreCase("+")) {
                holder.plus.setBackgroundColor(view.getResources().getColor(R.color.colorPrimary));
            }
        } else {
            holder.plus.setBackgroundColor(view.getResources().getColor(R.color.switchInactive));
            holder.minus.setBackgroundColor(view.getResources().getColor(R.color.switchInactive));
        }

        return view;
    }

    static class ViewHolder {
        TextView inisial, namamatkul, namadosen, nilai, plus, minus;
    }
}
