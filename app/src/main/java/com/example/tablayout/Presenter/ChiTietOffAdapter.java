package com.example.tablayout.Presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tablayout.R;

import java.io.FileInputStream;
import java.util.ArrayList;

public class ChiTietOffAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final String TAG = "ketqua";
    private Context context;
    private ArrayList<String> arr = new ArrayList<>();

    public ChiTietOffAdapter(Context context, ArrayList<String> arr){
        this.context = context;
        this.arr = arr;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return 2;
        }
        else if(position == 1){
            return 3;
        }
        else if(arr.get(position).contains("img_hoanganhktd")){
            return 1;
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
                return new ChiTietOffAdapter.ImageViewHolder(view);
            case 0:
                View view2 = layoutInflater.inflate(R.layout.item_recyclerview_text,viewGroup, false);
                return new ChiTietOffAdapter.TextViewHolder(view2);
            case 2:
                View view3  = layoutInflater.inflate(R.layout.item_recyclerview_text,viewGroup, false);
                return new ChiTietOffAdapter.TextViewHolder(view3);
            case 3:
                View view4  = layoutInflater.inflate(R.layout.item_recyclerview_text,viewGroup, false);
                return new ChiTietOffAdapter.TextViewHolder(view4);
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (getItemViewType(i)){
            case 1:
                ChiTietOffAdapter.ImageViewHolder imageViewHolder = (ChiTietOffAdapter.ImageViewHolder) viewHolder;
                String s = arr.get(i);
                int a = s.indexOf("img_hoanganhktd");
                String ss = s.substring(0,a);
                String sss = s.substring(a+15);
                int b = ss.lastIndexOf("/");
                final String nameImage = ss.substring(b+1);
//                Glide.with(context).load(ss).into(imageViewHolder.img);
                Bitmap bit = loadImageBitmap(context, nameImage);
                imageViewHolder.img.setImageBitmap(bit);
                imageViewHolder.textView.setText(sss);
                break;
            case 0:
                ChiTietOffAdapter.TextViewHolder textViewHolder = (ChiTietOffAdapter.TextViewHolder) viewHolder;
                textViewHolder.textView.setText(arr.get(i));
                break;
            case 2:
                ChiTietOffAdapter.TextViewHolder textViewHolder2 = (ChiTietOffAdapter.TextViewHolder) viewHolder;
                textViewHolder2.textView.setText(arr.get(i));
                textViewHolder2.textView.setTextSize(20);
                textViewHolder2.textView.setTextColor(Color.BLACK);
                break;
            case 3:
                ChiTietOffAdapter.TextViewHolder textViewHolder3 = (ChiTietOffAdapter.TextViewHolder) viewHolder;
                textViewHolder3.textView.setText(arr.get(i));
                textViewHolder3.textView.setTextSize(10);
                textViewHolder3.textView.setTextColor(Color.GRAY);
                break;
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
}
