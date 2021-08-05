package com.example.islamiapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.islamiapp.R;

import java.util.ArrayList;

public class QuranContentAdapter extends RecyclerView.Adapter<QuranContentAdapter.ViewHolder> {
    ArrayList<String> listOfSouraLines;

    public QuranContentAdapter(ArrayList<String> listOfSouraLines) {
        this.listOfSouraLines = listOfSouraLines;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quran_content_recycler_view,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        viewHolder.quranContent.setText( listOfSouraLines.get(position));
        viewHolder.ayaNumber.setText(position+1+"");


    }

    @Override
    public int getItemCount() {
        return listOfSouraLines.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView quranContent , ayaNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            quranContent = itemView.findViewById(R.id.quran_content);
            ayaNumber = itemView.findViewById(R.id.aya_number);

        }
    }
}
