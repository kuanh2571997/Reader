package com.example.tablayout.View;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tablayout.Presenter.ChiTietOffAdapter;
import com.example.tablayout.Model.Database;
import com.example.tablayout.R;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;

public class ChiTietOff extends AppCompatActivity {

    ArrayList<String> arr = new ArrayList<>();
    final String TAG = "ketqua";
    private RecyclerView recyclerView;
    TextView textTieuDe;
    //FloatingActionButton back;
    ActionBar actionBar;
    int idTitle;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_off);
        Slidr.attach(this);

        recyclerView = findViewById(R.id.recyclerView);
        textTieuDe = findViewById(R.id.textView_tieu_de);
        actionBar = getSupportActionBar();
        //actionBar.setTitle("Bài đã lưu");
        actionBar.setTitle(Html.fromHtml("<font color='#000000'>Tin đã lưu</font>"));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D6CFCF")));
        //back = findViewById(R.id.back);
        Intent intent = getIntent();
        idTitle = (int) intent.getIntExtra("idTitle",-1);
        String title = intent.getStringExtra("Title");
        textTieuDe.setText(title);
        String Des = intent.getStringExtra("Description");
        Log.d(TAG, "onCreate: "+idTitle);
        arr.add(Des);

        database = new Database(getApplicationContext(), "Title.sqlite", null, 1);
        Cursor cursorContent = database.GetData("select Content from tblContent where idTitle="+String.valueOf(idTitle));
        while(cursorContent.moveToNext()){
            arr.add(cursorContent.getString(0));
        }

        ChiTietOffAdapter recyclerViewAdapter = new ChiTietOffAdapter(getApplicationContext(), arr);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//
//            }
//
//            @SuppressLint("RestrictedApi")
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                Log.d(TAG, "onScrolled: "+dx+"----"+dy);
//                if (dy > 0) {
////                    back.setVisibility(View.GONE);
//                    Log.d(TAG, "onScrolled: up");
//                } else {
////                    back.setVisibility(View.VISIBLE);
//                    Log.d(TAG, "onScrolled: donw");
//                }
//
//            }
//        });
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                overridePendingTransition(R.anim.right_in, R.anim.left_out);
//            }
//        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_xoa) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(ChiTietOff.this);
            builder1.setMessage("Bạn có chắc chắn xóa tin này?.");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Hủy bỏ",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            builder1.setNegativeButton(
                    "Xóa",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            database.QueryData("delete from tblContent where idTitle = "+idTitle);
                            database.QueryData("delete from tblTitle where id = "+idTitle);
                            Toast.makeText(ChiTietOff.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
