package com.example.islamiapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.islamiapp.R;




public class QuranListAdapter extends RecyclerView.Adapter<QuranListAdapter.ViewHolder>{
     public String[] listOfSewarNames;
    onItemClickListener onTextClickListener;

    public void setListOfSewarNames(String[] listOfSewarNames) {
        this.listOfSewarNames = listOfSewarNames;
    }

    public String[] getListOfSewarNames() {
        return listOfSewarNames;
    }

    public QuranListAdapter(String[] listOfSewarNames) {
        this.listOfSewarNames = listOfSewarNames;
    }

    public void setOnTextClickListener(onItemClickListener onTextClickListener) {
        this.onTextClickListener = onTextClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quran_recyclerview_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        viewHolder.quranName.setText(listOfSewarNames[position]);

        if (onTextClickListener!=null){
            viewHolder.quranName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTextClickListener.onItemClick(position, listOfSewarNames[position]);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listOfSewarNames.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView quranName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            quranName = itemView.findViewById(R.id.quran_text);

        }
    }


    public interface onItemClickListener{
        void onItemClick(int position, String suraName);
    }



}
