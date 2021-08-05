package com.example.islamiapp.TabsFragment.switchFragment.azkar;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.islamiapp.R;


public class azkarListAdapter extends RecyclerView.Adapter<azkarListAdapter.ViewHolder> {
    public String[] listOfAzkarNames;
    onItemClickListener onTextClickListener;


    public azkarListAdapter(String[] listOfAzkarNames) {
        this.listOfAzkarNames = listOfAzkarNames;
    }

    public String[] getListOfAzkarNames() {
        return listOfAzkarNames;
    }

    public void setListOfAzkarNames(String[] listOfAzkarNames) {
        this.listOfAzkarNames = listOfAzkarNames;
    }

    public onItemClickListener getOnTextClickListener() {
        return onTextClickListener;
    }

    public void setOnTextClickListener(onItemClickListener onTextClickListener) {
        this.onTextClickListener = onTextClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.azkar_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        viewHolder.azkarName.setText(listOfAzkarNames[position]);

        if (onTextClickListener!=null){
            viewHolder.azkarName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTextClickListener.onItemClick(position, listOfAzkarNames[position]);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listOfAzkarNames.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView azkarName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            azkarName = itemView.findViewById(R.id.azkar_text);

        }
    }
    public interface onItemClickListener {
        void onItemClick(int position, String azkarName);
    }
}
