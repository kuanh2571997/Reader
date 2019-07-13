package com.example.tablayout.Presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tablayout.View.ChiTiet;
import com.example.tablayout.Model.Database;
import com.example.tablayout.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final String TAG = "ketqua";
    private Context context;
    private ArrayList<String> arr = new ArrayList<>();
    private ArrayList<String> arrChuyenMuc = new ArrayList<>();

    public RecyclerViewAdapter(Context context, ArrayList<String> arr){
        this.context = context;
        this.arr = arr;
        int position = arr.indexOf("ktd_CungChuyenMuc");
        for(int i = position+1; i<arr.size();i++){
            arrChuyenMuc.add(arr.get(i));
//            Log.d(TAG, "getItemViewType: da them"+arr.get(i));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(arr.get(position).contains("ktd_CungChuyenMuc")) {
            return 6;
        }
        else if(position == 0){
            return 2;
        }
        else if(position == 1 && !arr.get(position).contains("ktd_TieuDe")){
            return 3;
        }
        else if(arr.get(position).contains("img_hoanganhktd")){
            return 1;
        }
        else if(arr.get(position).contains("ktd_TieuDe")){
            return 7;
        }
        else
            return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        switch (i){
            case 1:
                View view = layoutInflater.inflate(R.layout.item_recyclerview_img,viewGroup, false);
                return new ImageViewHolder(view);
            case 0:
                View view2 = layoutInflater.inflate(R.layout.item_recyclerview_text,viewGroup, false);
                return new TextViewHolder(view2);
            case 2:
                View view3  = layoutInflater.inflate(R.layout.item_recyclerview_text,viewGroup, false);
                return new TextViewHolder(view3);
            case 3:
                View view4  = layoutInflater.inflate(R.layout.item_recyclerview_text,viewGroup, false);
                return new TextViewHolder(view4);
            case 6:
                View view7  = layoutInflater.inflate(R.layout.item_recyclerview_text,viewGroup, false);
                return new TextViewHolder(view7);
            case 7:
                View view8 = layoutInflater.inflate(R.layout.item_listview,viewGroup,false);
                return new ChuyenMucViewHolder(view8);
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        switch (getItemViewType(i)){
            case 1:
                ImageViewHolder imageViewHolder = (ImageViewHolder) viewHolder;
                String s = arr.get(i);
                int a = s.indexOf("img_hoanganhktd");
                String ss = s.substring(0,a);
                String sss = s.substring(a+15);
                Glide.with(context).load(ss).error(R.drawable.error).into(imageViewHolder.img);
                imageViewHolder.textView.setText(sss);
                break;
            case 0:
                TextViewHolder textViewHolder = (TextViewHolder) viewHolder;
                textViewHolder.textView.setText(arr.get(i));
                break;
            case 2:
                TextViewHolder textViewHolder2 = (TextViewHolder) viewHolder;
                textViewHolder2.textView.setText(arr.get(i));
                textViewHolder2.textView.setTextSize(20);
                textViewHolder2.textView.setTextColor(Color.BLACK);
                break;
            case 3:
                TextViewHolder textViewHolder3 = (TextViewHolder) viewHolder;
                textViewHolder3.textView.setText(arr.get(i));
                textViewHolder3.textView.setTextSize(10);
                textViewHolder3.textView.setTextColor(Color.GRAY);
                break;
            case 6:
                TextViewHolder textViewHolder6 = (TextViewHolder) viewHolder;
                textViewHolder6.textView.setText("Cùng chuyên mục");
                textViewHolder6.textView.setTextSize(25);
                textViewHolder6.textView.setTextColor(Color.parseColor("#0101FD"));
                break;
            case 7:
                ChuyenMucViewHolder textViewHolder7 = (ChuyenMucViewHolder) viewHolder;
                String data1 = arr.get(i);
                final int num = i;
                textViewHolder7.textTieuDe.setText(data1.substring(11,data1.indexOf("ktd_ThoiGian:")));
                final String tieude = data1.substring(11,data1.indexOf("ktd_ThoiGian:"));
                final String thoigian = data1.substring(data1.indexOf("ktd_ThoiGian:")+13,data1.indexOf("ktd_linkIMG:"));
                final String limg = data1.substring(data1.indexOf("ktd_linkIMG:")+12,data1.indexOf("ktd_link:"));
                textViewHolder7.textThoiGian.setText(data1.substring(data1.indexOf("ktd_ThoiGian:")+13,data1.indexOf("ktd_linkIMG:")));
                final String link1 = data1.substring(data1.indexOf("ktd_link:")+9);
                //Log.d(TAG, "onBindViewHolder: link ne"+link1);
                String titleImg = data1.substring(data1.indexOf("ktd_linkIMG:")+12,data1.indexOf("ktd_link:"));
                //Log.d(TAG, "onBindViewHolder: titleImg="+titleImg);
                Glide.with(context).load(data1.substring(data1.indexOf("ktd_linkIMG:")+12,data1.indexOf("ktd_link:"))).error(R.drawable.error).into(textViewHolder7.img);
                textViewHolder7.constraintLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ChiTiet.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if(link1.contains("video.vnexpress.net")){
                            Toast.makeText(context, "Đây là link video,chưa thể hiển thị.", Toast.LENGTH_SHORT).show();
                        }
                        else if(link1.contains("ttps://e.vnexpress.net")){
                            Toast.makeText(context, "bài viết tiếng anh có định dạng khác, chưa thể hiển thị.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Database database = new Database(context, "Title.sqlite", null, 1);
                            database.QueryData("CREATE TABLE IF NOT EXISTS tblHistory(Id INTEGER PRIMARY KEY AUTOINCREMENT, TieuDe VARCHAR(1000), Link VARCHAR(1000), LinkImg VARCHAR(1000), Date VARCHAR(200))");
                            database.InsertHis(tieude,link1, limg, thoigian);
                            intent.putExtra("link",link1);
                            intent.putStringArrayListExtra("StringArr",arrChuyenMuc);
                            intent.putExtra("Second","Second");
                            intent.putExtra("possiton",num);
                            intent.putExtra("titleImg",limg);
                            context.startActivity(intent);
                        }
                    }
                });
        }
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        private TextView textView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_item);
            textView = itemView.findViewById(R.id.mo_ta_anh);
        }
    }

    public class TextViewHolder extends  RecyclerView.ViewHolder{

        private TextView textView;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_item);
        }
    }

    public class ChuyenMucViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView textTieuDe, textThoiGian;
        ConstraintLayout constraintLayout;
        public ChuyenMucViewHolder(@NonNull View itemView) {
            super(itemView);
            textTieuDe = itemView.findViewById(R.id.textTieuDe);
            textThoiGian = itemView.findViewById(R.id.textThoiGian);
            img = itemView.findViewById(R.id.img);
            constraintLayout = itemView.findViewById(R.id.line);
        }
    }

}
