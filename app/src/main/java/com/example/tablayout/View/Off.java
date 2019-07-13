package com.example.tablayout.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tablayout.Model.Database;
import com.example.tablayout.R;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;

public class Off extends AppCompatActivity {
    ListView listView;
    ArrayList<String> arr = new ArrayList();
    ArrayList<Integer> arrInt = new ArrayList<>();
    ArrayList<String> arrDes = new ArrayList<>();
    final String TAG = "ketqua";
    EditText timkiem;
    ImageView img;
    //FloatingActionButton back;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_off);
        Slidr.attach(this);
        listView = findViewById(R.id.list);
        timkiem = findViewById(R.id.text_timkiem);
        img = findViewById(R.id.img_search);
        actionBar = getSupportActionBar();
        //actionBar.setTitle("Bài đã lưu");
        actionBar.setTitle(Html.fromHtml("<font color='#000000'>Tin đã lưu</font>"));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D6CFCF")));

//        back = findViewById(R.id.back);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                overridePendingTransition(R.anim.right_in, R.anim.left_out);
//            }
//        });
        final Database database = new Database(getApplicationContext(), "Title.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS tblTitle(Id INTEGER PRIMARY KEY AUTOINCREMENT, Title VARCHAR(1000), Description VARCHAR(1000))");
        database.QueryData("CREATE TABLE IF NOT EXISTS tblContent(Id INTEGER PRIMARY KEY AUTOINCREMENT, idTitle INTEGER, Content VARCHAR(3000), Status VARCHAR(100))");
        Cursor cursor2 = database.GetData("select count(*) from tblTitle");
        cursor2.moveToFirst();
        if(cursor2.getInt(0)==0){
            Toast.makeText(this, "Chưa có bài viết được lưu", Toast.LENGTH_SHORT).show();
        }
        else{
            final Cursor cursor = database.GetData("select * from tblTitle");
            while (cursor.moveToNext()){
                arr.add(cursor.getString(1).toString());
                arrInt.add(cursor.getInt(0));
                arrDes.add(cursor.getString(2));
            }
            final ArrayAdapter <String> adapter = new ArrayAdapter<>(getApplication(), R.layout.item_list_save, R.id.textView, arr);
            //final ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, arr);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Off.this,ChiTietOff.class);
                    intent.putExtra("idTitle", arrInt.get(position));
                    intent.putExtra("Title",arr.get(position));
                    intent.putExtra("Description",arrDes.get(position));
                    startActivity(intent);

                }
            });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    final int pos = position;
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Off.this);
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
                                    Cursor cursor = database.GetData("select Content from tblContent where idTitle="+arrInt.get(pos));
                                    cursor.moveToFirst();
                                    while(cursor.moveToNext()){
                                        String str = cursor.getString(0);
                                        if(str.contains("img_hoanganhktd")) {
                                            int a = str.indexOf("img_hoanganhktd");
                                            final String name = str.substring(0, a);
                                            int b = name.lastIndexOf("/");
                                            final String nameImage = name.substring(b + 1);
                                            deleteImage(nameImage);
                                            //Log.d(TAG, "onClick: "+nameImage);
                                        }
                                    }
                                    database.QueryData("delete from tblContent where idTitle = "+arrInt.get(pos));
                                    database.QueryData("delete from tblTitle where id = "+arrInt.get(pos));
                                    arrInt.remove(pos);
                                    arr.remove(arr.indexOf(arr.get(pos)));
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(Off.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                    return true;

                }
            });
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String data = timkiem.getText().toString();
                    arr.clear();
                    //Log.d(TAG, "onClick: "+"select * from tblTitle where Titile like '%"+data+"%'");
                    final Cursor cursor1 = database.GetData("select * from tblTitle where Title like '%"+data+"%'");
                    while (cursor1.moveToNext()){
                        arr.add(cursor1.getString(1).toString());
                        arrInt.add(cursor1.getInt(0));
                        arrDes.add(cursor1.getString(2));
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        }

    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        return true;
    }



    public void deleteImage(String linkImg){
        try {
            getApplicationContext().deleteFile(linkImg);
        }catch (Exception e){
            Log.d(TAG, "deleteImage: Lỗi xóa ảnh");
        }
    }
}
