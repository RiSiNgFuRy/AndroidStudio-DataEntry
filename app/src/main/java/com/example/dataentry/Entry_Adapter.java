package com.example.dataentry;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;

public class Entry_Adapter extends RecyclerView.Adapter<Entry_Adapter.ViewHolder> {
    private ArrayList<data_items> items;
    ItemSelected activity;
    interface ItemSelected
    {
        void onItemClicked(int index);
        void onDeleteClick(int index);
    }
    public Entry_Adapter(Context context, ArrayList<data_items> list)
    {
        items=list;
        activity=(ItemSelected) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sav_name,sav_email_id;
        ImageView del_btn,client_img;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            sav_name=itemView.findViewById(R.id.sav_name);
            sav_email_id=itemView.findViewById(R.id.sav_email);
            client_img=itemView.findViewById(R.id.sav_client_img);
            del_btn=itemView.findViewById(R.id.del_btn);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(items.indexOf((data_items)v.getTag()));
                }
            });
            del_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onDeleteClick(items.indexOf(itemView.getTag()));
                }
            });
        }
    }

    @NonNull
    @Override
    public Entry_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.data_view,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Entry_Adapter.ViewHolder holder, final int position){
        holder.itemView.setTag(items.get(position));
        holder.sav_name.setText(items.get(position).getName());
        holder.sav_email_id.setText(items.get(position).getEmail());
        holder.client_img.setImageURI(items.get(position).getImg_src());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}



