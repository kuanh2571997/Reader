package com.example.tablayout.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.example.tablayout.Model.Data;
import com.example.tablayout.Model.Database;
import com.example.tablayout.Presenter.RecyclerItemClickListener;
import com.example.tablayout.Presenter.RecyclerViewAdapter2;
import com.example.tablayout.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;

public class HisAndSave extends AppCompatActivity {
    ArrayList<Data> arr = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerViewAdapter2 recyclerViewAdapter2;
    FloatingActionButton clean;
    FloatingActionMenu materialDesignFAM;
    com.github.clans.fab.FloatingActionButton floatingActionButton1, floatingActionButton2;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_his_and_save);
        Slidr.attach(this);
        actionBar = getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<font color='#000000'>Tin đã đọc</font>"));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D6CFCF")));
        final Database database = new Database(this, "Title.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS tblHistory(Id INTEGER PRIMARY KEY AUTOINCREMENT, TieuDe VARCHAR(1000), Link VARCHAR(1000), LinkImg VARCHAR(1000), Date VARCHAR(200))");
        Cursor cursor = database.GetData("select * from tblHistory");
        if(cursor.getCount()>0){
            cursor.moveToLast();
            Data data1 = new Data();
            data1.setTieude(cursor.getString(1));
            data1.setLink(cursor.getString(2).toString());
            data1.setLinkImg(cursor.getString(3).toString());
            data1.setThoigian(cursor.getString(4).toString());
            arr.add(data1);
        }
        while (cursor.moveToPrevious()){
            Data data = new Data();
            data.setTieude(cursor.getString(1));
            data.setLink(cursor.getString(2).toString());
            data.setLinkImg(cursor.getString(3).toString());
            data.setThoigian(cursor.getString(4).toString());
            arr.add(data);
        }
//        clean = findViewById(R.id.clean);
        recyclerView = findViewById(R.id.recyclerView);
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.back);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.clear);;
        recyclerViewAdapter2 = new RecyclerViewAdapter2(getApplicationContext(), arr);
        recyclerView.setAdapter(recyclerViewAdapter2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Data data = arr.get(position);
                Intent intent = new Intent(getApplicationContext(),ChiTiet.class);
                intent.putExtra("link",data.getLink());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                arr.clear();
                recyclerViewAdapter2.notifyDataSetChanged();
                database.QueryData("drop table tblHistory");
                Toast.makeText(HisAndSave.this, "Clear Success!", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    //clean.setVisibility(View.GONE);
                } else {
                    //clean.setVisibility(View.VISIBLE);
                }

            }
        });
//        clean.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                arr.clear();
//                recyclerViewAdapter2.notifyDataSetChanged();
//                database.QueryData("drop table tblHistory");
//                Toast.makeText(HisAndSave.this, "Clear Success!", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        return true;
    }
}
