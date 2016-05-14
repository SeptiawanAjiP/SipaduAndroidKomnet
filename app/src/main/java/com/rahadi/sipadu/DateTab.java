package com.rahadi.sipadu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rahadi.sipadu.gettersetter.DateTabGetsetter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Rahadi on 05/05/2016.
 */
public class DateTab extends LinearLayout {

    Calendar c;
    DateTabGetsetter[] konten;
    int[][] jadwal = new int[][]{
            {2,3},
            {1},
            {},
            {1,2,3},
            {3}
    };

    public DateTab(Context context) {
        this(context, null);
    }

    public DateTab(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.datetab, this, true);

        TextView[] t = new TextView[5];
        t[0] = (TextView)findViewById(R.id.tanggal_1);
        t[1] = (TextView)findViewById(R.id.tanggal_2);
        t[2] = (TextView)findViewById(R.id.tanggal_3);
        t[3] = (TextView)findViewById(R.id.tanggal_4);
        t[4] = (TextView)findViewById(R.id.tanggal_5);

        View[] h = new View[5];
        h[0] = findViewById(R.id.hari_1);
        h[1] = findViewById(R.id.hari_2);
        h[2] = findViewById(R.id.hari_3);
        h[3] = findViewById(R.id.hari_4);
        h[4] = findViewById(R.id.hari_5);

        View[] in = new View[5];
        in[0] = findViewById(R.id.indi_1);
        in[1] = findViewById(R.id.indi_2);
        in[2] = findViewById(R.id.indi_3);
        in[3] = findViewById(R.id.indi_4);
        in[4] = findViewById(R.id.indi_5);

        konten = new DateTabGetsetter[5];
        int todayno = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        ImageView[] s;

        for(int i = 0; i < 5; i++) {
            c = Calendar.getInstance();
            c.setFirstDayOfWeek(Calendar.SUNDAY);
            c.set(Calendar.DAY_OF_WEEK, (i + 2));

            konten[i] = new DateTabGetsetter();
            konten[i].setDate(c.getTime());
            konten[i].setSesi(jadwal[i]);
        }

        for(int i = 0; i < 5; i++) {
            t[i].setText(konten[i].getDate());
            if((i+2) == todayno) {
                ImageView[] a = getImageViewList(i);
                h[i].setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                in[i].setBackgroundColor(getResources().getColor(android.R.color.white));
                for(ImageView b : a) {
                    b.setImageResource(R.drawable.kosong_focus);
                }
            }
        }

        int sesi[][] = new int[5][];

        for(int i = 0; i < 5; i ++) {
            s = getImageViewList(i);
            sesi[i] = konten[i].getSesi();

            for (int ss : sesi[i]) {
                for (int j = 0; j < 4; j++) {
                    if (ss == (j + 1)) {
                        if (todayno == (i+2)) {
                            s[j].setImageResource(R.drawable.sesi_focus);
                        } else {
                            s[j].setImageResource(R.drawable.sesi_infocus);
                        }
                    }
                }
            }
        }
    }

    public DateTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ImageView[] getImageViewList(int dayofweek) {
        ImageView[] i = new ImageView[4];
        if(dayofweek == 0) {
            i[0] = (ImageView)findViewById(R.id.sesi_1_1);
            i[1] = (ImageView)findViewById(R.id.sesi_1_2);
            i[2] = (ImageView)findViewById(R.id.sesi_1_3);
            i[3] = (ImageView)findViewById(R.id.sesi_1_4);
        }
        if(dayofweek == 1) {
            i[0] = (ImageView)findViewById(R.id.sesi_2_1);
            i[1] = (ImageView)findViewById(R.id.sesi_2_2);
            i[2] = (ImageView)findViewById(R.id.sesi_2_3);
            i[3] = (ImageView)findViewById(R.id.sesi_2_4);
        }
        if(dayofweek == 2) {
            i[0] = (ImageView)findViewById(R.id.sesi_3_1);
            i[1] = (ImageView)findViewById(R.id.sesi_3_2);
            i[2] = (ImageView)findViewById(R.id.sesi_3_3);
            i[3] = (ImageView)findViewById(R.id.sesi_3_4);
        }
        if(dayofweek == 3) {
            i[0] = (ImageView)findViewById(R.id.sesi_4_1);
            i[1] = (ImageView)findViewById(R.id.sesi_4_2);
            i[2] = (ImageView)findViewById(R.id.sesi_4_3);
            i[3] = (ImageView)findViewById(R.id.sesi_4_4);
        }
        if(dayofweek == 4) {
            i[0] = (ImageView)findViewById(R.id.sesi_5_1);
            i[1] = (ImageView)findViewById(R.id.sesi_5_2);
            i[2] = (ImageView)findViewById(R.id.sesi_5_3);
            i[3] = (ImageView)findViewById(R.id.sesi_5_4);
        }
        return i;
    }

}
