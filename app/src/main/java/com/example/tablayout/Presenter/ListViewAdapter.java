package com.example.tablayout.Presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tablayout.Model.Data;
import com.example.tablayout.R;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<Data> arr;
    private int layout;
    private Context context;

    public ListViewAdapter (Context context, int layout, ArrayList<Data> arr ){
        this.arr = arr;
        this.layout = layout;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView img;
        TextView textTieuDe, textThoiGian;

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(layout, null);

        textTieuDe = convertView.findViewById(R.id.textTieuDe);
        textThoiGian = convertView.findViewById(R.id.textThoiGian);
        img = convertView.findViewById(R.id.img);

        Data data = arr.get(position);

        textTieuDe.setText(data.getTieude());
        textThoiGian.setText(data.getThoigian());

        return convertView;
    }
}
