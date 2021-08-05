package com.example.islamiapp.TabsFragment.switchFragment.ahadeth;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class QuotesFragment extends BaseFragment {

    RecyclerView ahadethRecyclerView ;
    QuotesListAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    static ArrayList<String> hadethData;

    public static String[] listOfAhadethNames = {"الحديث الأول","الحديث الثاني","الحديث الـثـالـث","الحديث الـرابع","الحديث الخامس","الحديث السادس","الحديث السابع","الحديث الثامن","الحديث التاسع","الحديث العاشر",
            "الحديث الحادي عشر","الحديث الثانى عشر","الحديث الثالث عشر","الحد يث الرابع عشر","الحديث الخامس عشر","الحديث السادس عشر","الحديث السابع عشر","الحد يث الثامن عشر","الحد يث التاسع عشر","الحديث العشرون",
            "الحديث الحادي والعشرون","الحديث الثانى والعشرون","الحديث الثالث والعشرون","الحديث الرابع والعشرون","الحديث الخامس والعشرون","الحديث السادس والعشرون","الحديث السابع والعشرون","الحديث الثامن والعشرون","الحديث التاسع والعشرون","الحديث الثلاثون",
            "الحديث الحادي والثلاثون","الحديث الثانى والثلاثون","الحديث الثالث والثلاثون","الحديث الرابع والثلاثون","الحديث الخامس والثلاثون","الحديث السادس والثلاثون","الحديث السابع والثلاثون","الحديث الثامن والثلاثون","الحديث التاسع والثلاثون","الحديث الأربعون",
            "الحديث الحادي والأربعون","الحديث الثانى والأربعون","الحديث الثالث والأربعون","الحديث الرابع والأربعون","الحديث الخامس والأربعون","الحديث السادس والأربعون","الحديث السابع والأربعون","الحديث الثامن والأربعون","الحديث التاسع والأربعون","الحديث الخمسون",
          };


    public QuotesFragment() {
        // Required empty public constructor
    }

    View view ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        view =  inflater.inflate(R.layout.fragment_quotes, container, false);
        ahadethRecyclerView = view.findViewById(R.id.quotes_recyclerView);
        //listOfHadethName = new ArrayList<>();
         hadethData = txtReader("ahadeth.txt");
        adapter =  new QuotesListAdapter(listOfAhadethNames);
        layoutManager = new GridLayoutManager(getContext() , 2);
        ahadethRecyclerView.setAdapter(adapter);
        ahadethRecyclerView.setLayoutManager(layoutManager);
        adapter.setOnTextClickListener(new QuotesListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position, String hadethName) {
                showConfirmationMessage(hadethName, hadethData.get(position), R.string.ok, new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();

                    }
                });
            }
        });
        return view;
    }


    InputStream inputStream;
    BufferedReader bufferedReader;
    int countLinesOfText = 0;


    public ArrayList<String> txtReader(String fileName) {
        try {
            inputStream = Objects.requireNonNull(getActivity()).getAssets().open(fileName);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String lineIs = null;
            ArrayList<String> hadeth = new ArrayList<>(countLinesOfText);
            try {
                while ((lineIs = bufferedReader.readLine()) != null) {
                    String total=lineIs;
                    while ((lineIs = bufferedReader.readLine()) != null) {
                        if("#".equals(lineIs.trim()))break;
                        total=total+"\n"+lineIs;
                    }
                    hadeth.add(total);

                }
                hadethData=hadeth;


            } catch (Exception f) {
                f.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return hadethData;
    }
    }


