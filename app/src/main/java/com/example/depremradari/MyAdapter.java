package com.example.depremradari;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private List<MyItem> itemList;

    public MyAdapter(Context context, List<MyItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyItem item = itemList.get(position);
        holder.textViewName.setText(Double.toString(item.getMag()));
        UpdateColorItem(holder, item.getMag());
        holder.textViewDistrict.setText(item.getDistrict().toUpperCase());
        holder.textViewProvince.setText(item.getProvince().toUpperCase());
        holder.textViewHours.setText(item.getHours());
        holder.textViewDepth.setText(Double.toString(item.getDepth()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewDistrict, textViewProvince, textViewHours, textViewDepth;
        LinearLayout magBlock;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.magBlockText);
            textViewDistrict = itemView.findViewById(R.id.district);
            textViewProvince = itemView.findViewById(R.id.province);
            textViewHours = itemView.findViewById(R.id.hours);
            textViewDepth = itemView.findViewById(R.id.depth);
            magBlock = itemView.findViewById(R.id.magBlock);
        }
    }

    public void UpdateColorItem(@NonNull MyViewHolder holder, double magValue){

        if ((magValue) < 2) {
            holder.magBlock.setBackgroundColor(context.getResources().getColor(R.color.green));
            holder.textViewName.setTextColor(context.getResources().getColor(R.color.green));
            holder.textViewName.setBackgroundColor(context.getResources().getColor(R.color.lightgreen));

        } else if ((magValue) >= 2 && (magValue) < 3) {
            holder.magBlock.setBackgroundColor(context.getResources().getColor(R.color.yellow));
            holder.textViewName.setTextColor(context.getResources().getColor(R.color.yellow));
            holder.textViewName.setBackgroundColor(context.getResources().getColor(R.color.lightYellow));
        } else if ((magValue) >= 3 && (magValue) < 4) {
            holder.magBlock.setBackgroundColor(context.getResources().getColor(R.color.orange));
            holder.textViewName.setTextColor(context.getResources().getColor(R.color.orange));
            holder.textViewName.setBackgroundColor(context.getResources().getColor(R.color.lightorange));
        }
        else if((magValue) >= 4){
            holder.magBlock.setBackgroundColor(context.getResources().getColor(R.color.Depremred));
            holder.textViewName.setTextColor(context.getResources().getColor(R.color.Depremred));
            holder.textViewName.setBackgroundColor(context.getResources().getColor(R.color.lightred));
        }
    }
}