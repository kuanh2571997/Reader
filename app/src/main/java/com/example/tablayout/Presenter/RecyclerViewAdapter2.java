package com.example.tablayout.Presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tablayout.Model.Data;
import com.example.tablayout.R;

import java.util.ArrayList;

public class RecyclerViewAdapter2 extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final String TAG = "ketqua";
    private Context context;
    private ArrayList<Data> arr = new ArrayList<>();

    public RecyclerViewAdapter2(Context context, ArrayList<Data> arr){
        this.context = context;
        this.arr = arr;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
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
                View view = layoutInflater.inflate(R.layout.tin_hot,viewGroup, false);
                return new RecyclerViewAdapter2.FirstViewHolder(view);
            case 0:
                View view2 = layoutInflater.inflate(R.layout.item_listview,viewGroup, false);
                return new RecyclerViewAdapter2.ManyViewHolder(view2);
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (getItemViewType(i)){
            case 1:
                RecyclerViewAdapter2.FirstViewHolder firstViewHolder = (RecyclerViewAdapter2.FirstViewHolder) viewHolder;
                final Data data = arr.get(i);
                firstViewHolder.textTieuDe.setText(data.getTieude());
                firstViewHolder.textThoiGian.setText(data.getThoigian());
                firstViewHolder.textMoTa.setText(data.getDes());
                Glide.with(context).load(data.getLinkImg()).error(R.drawable.error).into(firstViewHolder.img);
                break;
            case 0:
                RecyclerViewAdapter2.ManyViewHolder manyViewHolder = (RecyclerViewAdapter2.ManyViewHolder) viewHolder;
                Data data2 = arr.get(i);
                manyViewHolder.textTieuDe.setText(data2.getTieude());
                manyViewHolder.textThoiGian.setText(data2.getThoigian());
                Glide.with(context).load(data2.getLinkImg()).error(R.drawable.error).into(manyViewHolder.img);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class FirstViewHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        private TextView textTieuDe, textThoiGian, textMoTa;

        public FirstViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_tin_hot);
            textTieuDe = itemView.findViewById(R.id.textView_tieu_de);
            textMoTa = itemView.findViewById(R.id.textView_mo_ta);
            textThoiGian = itemView.findViewById(R.id.textView_thoi_gian);
        }
    }

    public class ManyViewHolder extends  RecyclerView.ViewHolder{

        private ImageView img;
        private TextView textTieuDe, textThoiGian;

        public ManyViewHolder(@NonNull View itemView) {
            super(itemView);
            textTieuDe = itemView.findViewById(R.id.textTieuDe);
            textThoiGian = itemView.findViewById(R.id.textThoiGian);
            img = itemView.findViewById(R.id.img);
        }
    }

//    public interface OnItemClickedListener {
//        void onItemClick(String username);
//    }
//
//    private OnItemClickedListener onItemClickedListener;
//
//    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
//        this.onItemClickedListener = onItemClickedListener;
//    }
}
