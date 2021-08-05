package com.example.islamiapp.TabsFragment.switchFragment.azkar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.islamiapp.Base.BaseFragment;
import com.example.islamiapp.R;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class AzkarFragment extends BaseFragment {

    View view;

    InputStream inputStream;
    BufferedReader bufferedReader;
    int countLinesOfText = 0;
    static ArrayList<String> azkarData;
    RecyclerView azkarRecyclerView ;
    azkarListAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    public static String[] listOfAzkarNames = {"اذكار الصباح","اذكار المساء","اذكار النوم"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_azkar, container, false);
        azkarRecyclerView = view.findViewById(R.id.azkar_recyclerView);
        //listOfHadethName = new ArrayList<>();
        azkarData = txtReader("azkar.txt");
        adapter =  new azkarListAdapter(listOfAzkarNames);
        layoutManager =new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL,false);
        azkarRecyclerView.setAdapter(adapter);
        azkarRecyclerView.setLayoutManager(layoutManager);
        adapter.setOnTextClickListener(new azkarListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position, String hadethName) {
                showConfirmationMessage(hadethName, azkarData.get(position), R.string.ok, new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                });
            }
        });

        return view;
    }

    public ArrayList<String> txtReader(String fileName) {
        try {
            inputStream = Objects.requireNonNull(getActivity()).getAssets().open(fileName);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String lineIs = null;
            ArrayList<String> azkar = new ArrayList<>(countLinesOfText);
            try {
                while ((lineIs = bufferedReader.readLine()) != null) {
                    String total=lineIs;
                    while ((lineIs = bufferedReader.readLine()) != null) {
                        if("#".equals(lineIs.trim()))break;
                        total=total+"\n"+lineIs;
                    }
                    azkar.add(total);

                }
                azkarData=azkar;


            } catch (Exception f) {
                f.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return azkarData;
    }
}