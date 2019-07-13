package com.example.tablayout.Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tablayout.R;

import java.util.ArrayList;

public class VideoAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "VideoAdapter";
    private Context context;
    private ArrayList<DataVideo> arr = new ArrayList<>();

    public VideoAdapter2(Context context, ArrayList<DataVideo> arr){
        this.context = context;
        this.arr = arr;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_video2, viewGroup, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final int num = i;
        VideoViewHolder videoViewHolder = (VideoViewHolder) viewHolder;
        videoViewHolder.textTieuDe.setText(arr.get(i).getTieuDe());
        videoViewHolder.textThoiGian.setText(arr.get(i).getDate());
        Glide.with(context).load(arr.get(i).getLinkAvatar()).into(videoViewHolder.img);
//        videoViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, PlayVideo.class);
//                intent.putExtra("link",arr.get(num).getUrlVideo());
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("array", arr);
//                intent.putExtra("bundle",bundle);
//                intent.putExtra("possition",num);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{
        private TextView textTieuDe, textThoiGian;
        private ImageView img;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            textTieuDe = itemView.findViewById(R.id.textTieuDe);
            textThoiGian = itemView.findViewById(R.id.textThoiGian);
            img = itemView.findViewById(R.id.img);
        }
    }
}