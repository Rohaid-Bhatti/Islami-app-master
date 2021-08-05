package com.example.islamiapp.TabsFragment.radio;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.islamiapp.API.Model.RadiosItem;
import com.example.islamiapp.R;

import java.util.List;

public class RadioChannelsAdapter extends RecyclerView.Adapter<RadioChannelsAdapter.ViewHolder> {

    List<RadiosItem> radiosItemList;

    OnItemClickListener onPlayClickListener;
    OnItemClickListener onStopClickListener;

    public void setOnPlayClickListener(OnItemClickListener onPlayClickListener) {
        this.onPlayClickListener = onPlayClickListener;
    }

    public void setOnStopClickListener(OnItemClickListener onStopClickListener) {
        this.onStopClickListener = onStopClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int pos, RadiosItem channel);
    }


    public RadioChannelsAdapter(List<RadiosItem> radiosItemList) {
        this.radiosItemList = radiosItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_radio,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final RadiosItem radiosItem = radiosItemList.get(position);
        viewHolder.name.setText(radiosItem.getName());
        if(onPlayClickListener!=null)
            viewHolder.play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPlayClickListener.onItemClick(position,radiosItem);
                }
            });

        if(onStopClickListener!=null)
            viewHolder.stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onStopClickListener.onItemClick(position,radiosItem);
                }
            });

    }

    public void changeData(List<RadiosItem> items){
        radiosItemList = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (radiosItemList == null) return 0;
        return radiosItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView play, stop;
        TextView name;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            play = itemView.findViewById(R.id.play);
            stop = itemView.findViewById(R.id.stop);
            name = itemView.findViewById(R.id.txtName);
        }
    }
}
