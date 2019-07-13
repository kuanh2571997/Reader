package com.example.tablayout.Presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tablayout.R;

import java.util.List;

public class CustomMenu extends  RecyclerView.Adapter<CustomMenu.recyclerviewHolder> {

    List<String> arrString;
    Context context;

    public CustomMenu(List<String> arrString, Context context) {
        this.arrString = arrString;
        this.context = context;
    }

    @NonNull
    @Override
    public recyclerviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view  = inflater.inflate(R.layout.item_menu,viewGroup, false);
        return new recyclerviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerviewHolder recyclerviewHolder, int i) {
        recyclerviewHolder.textView.setText(arrString.get(i));
    }

    @Override
    public int getItemCount() {
        return arrString.size();
    }

    public class recyclerviewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        public recyclerviewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewMenu);

        }
    }

}
