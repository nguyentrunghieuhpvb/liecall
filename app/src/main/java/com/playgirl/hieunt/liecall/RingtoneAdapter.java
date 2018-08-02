package com.playgirl.hieunt.liecall;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RingtoneAdapter extends RecyclerView.Adapter<RingtoneAdapter.MyViewHolder> {

    private ArrayList<String> listRingTone;
    private int pos = 0;

    interface OnItemSelectedListener {
        void onItemSelected(int pos);
    }

    private OnItemSelectedListener onItemSelectedListener;

    public RingtoneAdapter(ArrayList<String> listRingTone, OnItemSelectedListener onItemSelectedListener) {
        this.listRingTone = listRingTone;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public ArrayList<String> getListRingTone() {
        return listRingTone;
    }

    public void setListRingTone(ArrayList<String> listRingTone) {
        this.listRingTone.clear();
        this.listRingTone.addAll(listRingTone);
        notifyDataSetChanged();
    }

    public void addRingTone(String name) {
        listRingTone.add(name);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_ringtone, viewGroup, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {
        holder.tvName.setText(listRingTone.get(i));
        if (i == pos) {
            holder.rlRingtone.setBackgroundColor(R.color.item_selcted);
        } else {
            holder.rlRingtone.setBackgroundColor(Color.WHITE);
        }
        holder.rlRingtone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                pos = i;
                notifyDataSetChanged();
                onItemSelectedListener.onItemSelected(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listRingTone.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private RelativeLayout rlRingtone;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_ringtone_name);
            rlRingtone = itemView.findViewById(R.id.rl_item_ringtone);
        }
    }
}
