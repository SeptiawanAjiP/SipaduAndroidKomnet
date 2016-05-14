package com.rahadi.sipadu.UserInterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.rahadi.sipadu.R;
import com.rahadi.sipadu.gettersetter.BeritaGetsetter;

//import com.squareup.picasso.Picasso;


public class BeritaDetailActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {
    public static String KEY_ITEM = "item";
    private TextView txtTanggal, txtPengirim, txtDetail, txtJudul;
    //private ImageView imgDetail;
    private BeritaGetsetter item;
    private View flexibleSpaceView, toolbarView;
    private int flexibleSpaceHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita_detail);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_berita_detail);
        setSupportActionBar(toolbar);

        toolbarView = findViewById(R.id.toolbar_berita_detail);

        item = (BeritaGetsetter) getIntent().getSerializableExtra(KEY_ITEM);

//        getSupportActionBar().setTitle(item.getJudul().toUpperCase());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtJudul = (TextView)findViewById(R.id.title_berita_detail);
        txtTanggal = (TextView)findViewById(R.id.tanggal_berita_detail);
        txtPengirim = (TextView)findViewById(R.id.pengirim_berita_detail);
        txtPengirim.setSelected(true);
        txtDetail = (TextView)findViewById(R.id.txt_detail);
        //imgDetail = (ImageView)findViewById(R.id.img_detail);

        txtJudul.setText(item.getNama().toUpperCase());
        setTitle(null);
        txtTanggal.setText(item.getTanggal());
        txtPengirim.setText(item.getPengirim());
//        txtDetail.setText(Html.fromHtml("<p>"+item.getIsi()+"</p>"));
        txtDetail.setText(item.getIsi());
        //Picasso.with(DetailMobilActivity.this).load(item.getImage()).into(imgDetail);

        final ObservableScrollView scrollView = (ObservableScrollView)findViewById(R.id.scroller_berita_detail);

        scrollView.setScrollViewCallbacks(this);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_mobil, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_share) {
            share();
            return true;
        }*/

        if (id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, item.getNama() + " " + item.getTanggal() + " " + item.getPengirim());
        sendIntent.putExtra(Intent.EXTRA_TITLE, "Jual Mobile Murah");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int maxScroll = (int) ScrollUtils.getFloat(scrollY, 0, getSupportActionBar().getHeight());
        View head = findViewById(R.id.head_berita_detail);
        Toolbar t = (Toolbar)findViewById(R.id.toolbar_berita_detail);
        t.setScrollY(maxScroll);
        head.setScrollY(maxScroll);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    private void updateFlexibleSpaceText(int scrollY) {

    }
}
