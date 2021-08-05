package com.example.islamiapp.TabsFragment.switchFragment.ahadeth;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.islamiapp.R;

public class QuotesListAdapter extends RecyclerView.Adapter<QuotesListAdapter.ViewHolder> {
    public String[] listOfAhadethNames;
    onItemClickListener onTextClickListener;

    public QuotesListAdapter(String[] listOfAhadethNames) {
        this.listOfAhadethNames = listOfAhadethNames;
    }

    public String[] getListOfAhadethNames() {
        return listOfAhadethNames;
    }

    public void setListOfAhadethNames(String[] listOfAhadethNames) {
        this.listOfAhadethNames = listOfAhadethNames;
    }

    public void setOnTextClickListener(onItemClickListener onTextClickListener) {
        this.onTextClickListener = onTextClickListener;
    }

    public onItemClickListener getOnTextClickListener() {
        return onTextClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ahdeth_content_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        viewHolder.quotesName.setText(listOfAhadethNames[position]);

        if (onTextClickListener!=null){
            viewHolder.quotesName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTextClickListener.onItemClick(position, listOfAhadethNames[position]);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listOfAhadethNames.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView quotesName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            quotesName = itemView.findViewById(R.id.quotes_text);

        }
    }
    public interface onItemClickListener {
        void onItemClick(int position, String hadethName);
    }

}
