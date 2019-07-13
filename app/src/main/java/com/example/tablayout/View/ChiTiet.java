package com.example.tablayout.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.example.tablayout.Model.Data;
import com.example.tablayout.Model.Database;
import com.example.tablayout.Model.LoadRSS;
import com.example.tablayout.Model.PareURL;
import com.example.tablayout.Presenter.RecyclerViewAdapter;
import com.example.tablayout.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.r0adkll.slidr.Slidr;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ChiTiet extends AppCompatActivity implements LoadRSS.pass {

    ArrayList<String> arr = new ArrayList<>();
    private RecyclerView recyclerView;
    TextView textTieuDe;
    FloatingActionButton fab;
    String Title="";
    final String TAG = "ketqua";
    int possition;
    ArrayList<Data>arrData = new ArrayList<>();
    ArrayList<String> arrChuyenMuc = new ArrayList<>();
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;
    ActionBar actionBar;
    String titleImg="";

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        Slidr.attach(this);
        final Intent intent = getIntent();
        final String s = intent.getStringExtra("link");
        recyclerView = findViewById(R.id.recyclerView);
        textTieuDe = findViewById(R.id.textView_tieu_de);
        actionBar = getSupportActionBar();
        actionBar.hide();
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);
        //fab = findViewById(R.id.fab);
        try {
            arr = new PareURL().execute(s).get();
            if(arr.size()<3){
                Log.d(TAG, "onCreate: nhỏ hơn 3");
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (Exception e){
            Log.d(TAG, "onCreate: Bài viết sai định dạng");
        }
        if(arrData.size()!=0){
            arr.add("ktd_CungChuyenMuc");
        }
        if(intent.hasExtra("Second")){
            //Log.d(TAG, "onCreate: second");
            titleImg = intent.getStringExtra("titleImg");
            //Log.d(TAG, "onCreate: "+titleImg);
            possition = intent.getIntExtra("possition",0);
            arrChuyenMuc = intent.getStringArrayListExtra("StringArr");
            arr.add("ktd_CungChuyenMuc");
            for(int i=0;i<arrChuyenMuc.size();i++){
                arr.add(arrChuyenMuc.get(i));
            }
        }
        if(intent.hasExtra("first")){
            titleImg = intent.getStringExtra("titleImg");
            //Log.d(TAG, "onCreate: "+titleImg);
            possition = intent.getIntExtra("possition",0);
            Bundle bundle = intent.getBundleExtra("bundle");
            arrData = (ArrayList<Data>) bundle.getSerializable("array");
            arr.add("ktd_CungChuyenMuc");
                for(int i=0;i<arrData.size();i++){
//                    Log.d(TAG, "onCreate: arrCungChuyenMuc=" + arrData.size());
                    Data data = arrData.get(i);
                    String str = "ktd_TieuDe:"+data.getTieude()+"ktd_ThoiGian:"+data.getThoigian()+"ktd_linkIMG:"+data.getLinkImg()+"ktd_link:"+data.getLink();
                    //Log.d(TAG, "onCreate: cùng chuyên mục: "+str);
                    arr.add(str);
                }
        }
        try{
            Title = arr.get(0);
            if(!Title.equals("ktd_CungChuyenMuc")){
                textTieuDe.setText(Title);
                arr.remove(0);
            }
            //Log.d(TAG, "onCreate: "+Title);
            RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getApplicationContext(), arr);
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);

                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    //Log.d(TAG, "onScrolled: "+dx+"----"+dy);
                    if (dy > 0) {
                        //Log.d(TAG, "onScrolled: up");
                        materialDesignFAM.setVisibility(View.GONE);
                    } else {
                        //Log.d(TAG, "onScrolled: donw");
                        materialDesignFAM.setVisibility(View.VISIBLE);
                    }

                }
            });
        }catch (Exception e){
            Toast.makeText(this, "Bài viết không thể hiển thị", Toast.LENGTH_SHORT).show();
        }

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
                finish();
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String title = Title.replace("'","`");
                Database database = new Database(getApplicationContext(), "Title.sqlite", null, 1);
                //database.QueryData("CREATE TABLE IF NOT EXISTS tblTitle(Id INTEGER PRIMARY KEY AUTOINCREMENT, Title VARCHAR(1000), Description VARCHAR(1000), TitleImg VARCHAR(1000))");
                database.QueryData("CREATE TABLE IF NOT EXISTS tblTitle(Id INTEGER PRIMARY KEY AUTOINCREMENT, Title VARCHAR(1000), Description VARCHAR(1000))");
                database.QueryData("CREATE TABLE IF NOT EXISTS tblContent(Id INTEGER PRIMARY KEY AUTOINCREMENT, idTitle INTEGER, Content VARCHAR(3000), Status VARCHAR(100))");
                Cursor cursor2 = database.GetData("select count(*) from tblTitle where Title="+"'"+title+"'");
                cursor2.moveToFirst();
                if(cursor2.getInt(0)==0){
                    String des = arr.get(0).replace("'","`");
                    //database.InsertTitle(title, des, titleImg);
                    database.InsertTitle(title, des);
                    Cursor cursor = database.GetData("select id from tblTitle where Title="+"'"+title+"'");
                    cursor.moveToFirst();
                    int id = cursor.getInt(0);
                    //Log.d(TAG, "onClick: "+id);
                    for(int i=1;i<arr.size();i++){
                        if(arr.get(i).equals("ktd_CungChuyenMuc")){
                            break;
                        }
                        String str1 = arr.get(i).replace("'","`");
                        database.InsertContent(id,str1,"n/a");
                        String str = arr.get(i);
                        if(str.contains("img_hoanganhktd")){
                            int a = str.indexOf("img_hoanganhktd");
                            final String name = str.substring(0,a);
                            int b = name.lastIndexOf("/");
                            final String nameImage = name.substring(b+1);
//                            Log.d(TAG, "onClick: nameImage: "+nameImage);
//                            Log.d(TAG, "onClick: name: "+name);
                            AndroidNetworking.get(name)
                                    .setTag("test")
                                    .setPriority(Priority.MEDIUM)
                                    .build()
                                    .getAsBitmap(new BitmapRequestListener() {
                                        @Override
                                        public void onResponse(Bitmap response) {
                                            saveImage(getApplicationContext(),response,nameImage);
                                            //Toast.makeText(ChiTiet.this, "done", Toast.LENGTH_SHORT).show();
                                            //Bitmap bit = loadImageBitmap(getApplicationContext(), "abc.png");
                                        }

                                        @Override
                                        public void onError(ANError anError) {

                                        }
                                    });
                        }
                    }
                    Toast.makeText(ChiTiet.this, "Đã lưu", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ChiTiet.this, "Bài viết đã được lưu trước đó", Toast.LENGTH_SHORT).show();
                }
        }
        });
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String title = Title.replace("'","`");
//                Database database = new Database(getApplicationContext(), "Title.sqlite", null, 1);
//                database.QueryData("CREATE TABLE IF NOT EXISTS tblTitle(Id INTEGER PRIMARY KEY AUTOINCREMENT, Title VARCHAR(1000), Description VARCHAR(1000))");
//                database.QueryData("CREATE TABLE IF NOT EXISTS tblContent(Id INTEGER PRIMARY KEY AUTOINCREMENT, idTitle INTEGER, Content VARCHAR(3000), Status VARCHAR(100))");
//                Cursor cursor2 = database.GetData("select count(*) from tblTitle where Title="+"'"+title+"'");
//                cursor2.moveToFirst();
//                if(cursor2.getInt(0)==0){
//                    String des = arr.get(0).replace("'","`");
//                    database.InsertTitle(title, des);
//                    Cursor cursor = database.GetData("select id from tblTitle where Title="+"'"+title+"'");
//                    cursor.moveToFirst();
//                    int id = cursor.getInt(0);
//                    //Log.d(TAG, "onClick: "+id);
//                    for(int i=1;i<arr.size();i++){
//                        if(arr.get(i).equals("ktd_CungChuyenMuc")){
//                            break;
//                        }
//                        String str1 = arr.get(i).replace("'","`");
//                        database.InsertContent(id,str1,"n/a");
//                        String str = arr.get(i);
//                        if(str.contains("img_hoanganhktd")){
//                            int a = str.indexOf("img_hoanganhktd");
//                            final String name = str.substring(0,a);
//                            int b = name.lastIndexOf("/");
//                            final String nameImage = name.substring(b+1);
////                            Log.d(TAG, "onClick: nameImage: "+nameImage);
////                            Log.d(TAG, "onClick: name: "+name);
//                            AndroidNetworking.get(name)
//                                    .setTag("test")
//                                    .setPriority(Priority.MEDIUM)
//                                    .build()
//                                    .getAsBitmap(new BitmapRequestListener() {
//                                        @Override
//                                        public void onResponse(Bitmap response) {
//                                            saveImage(getApplicationContext(),response,nameImage);
//                                            //Toast.makeText(ChiTiet.this, "done", Toast.LENGTH_SHORT).show();
//                                            //Bitmap bit = loadImageBitmap(getApplicationContext(), "abc.png");
//                                        }
//
//                                        @Override
//                                        public void onError(ANError anError) {
//
//                                        }
//                                    });
//                        }
//                    }
//                    Toast.makeText(ChiTiet.this, "Đã lưu", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(ChiTiet.this, "Bài viết đã được lưu trước đó", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

    }

    public void saveImage(Context context, Bitmap b, String imageName) {
        FileOutputStream foStream;
        try {
            foStream = context.openFileOutput(imageName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, foStream);
            foStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 2, Something went wrong!");
            e.printStackTrace();
        }
    }
    public Bitmap loadImageBitmap(Context context, String imageName) {
        Bitmap bitmap = null;
        FileInputStream fiStream;
        try {
            fiStream    = context.openFileInput(imageName);
            bitmap      = BitmapFactory.decodeStream(fiStream);
            fiStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 3, Something went wrong!");
            e.printStackTrace();
        }
        return bitmap;
    }

    private String getImageLinkFromCDATA(String description){
        int a = 0;
        if(description.contains("data-original")){
            a=description.indexOf("data-original")+15;
        }
        else a = description.indexOf("src")+5;

        int b = 0;
        if(description.contains(".png")){
            b = description.indexOf(".png");
        }
        else if(description.contains(".jpg")){
            b = description.indexOf(".jpg");
        }
        else if(description.contains(".gif")){
            b = description.indexOf(".gif");
        }
        else if(description.contains(".jpeg")){
            b = description.indexOf(".jpeg")+1;
        }

        return description.substring(a,b+4);
    }

    private String getDescriptionFromCDATA(String description){
        int a = description.indexOf("</a></br>");
        return description.substring(a+9);
    }

    @Override
    public void setData(String s) {

    }
}
