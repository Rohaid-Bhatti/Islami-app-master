package com.example.islamiapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.islamiapp.Adapter.QuranContentAdapter;
import com.example.islamiapp.Adapter.QuranListAdapter;
import com.example.islamiapp.Base.BaseActivity;
import com.example.islamiapp.TabsFragment.QuranFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SouraContentActivity extends BaseActivity {
    RecyclerView quranContentrecyclerView;
    QuranContentAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    static String suraFileName;

    public static void setSuraFileName(String suraFileName) {
        SouraContentActivity.suraFileName = suraFileName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soura_content);
        int suraIndex=Integer.parseInt(suraFileName)-1;
        getSupportActionBar().setTitle( "سورة "+QuranFragment.listOfSewarNames[suraIndex]);
        quranContentrecyclerView = findViewById(R.id.ayat_content_recyclerView);
        adapter = new QuranContentAdapter(txtReader());
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        quranContentrecyclerView.setAdapter(adapter);
        quranContentrecyclerView.setLayoutManager(layoutManager);

    }

    InputStream inputStream, streamCountLines;
    BufferedReader bufferedReader, readerCountLines;
    int countLinesOfText = 0;
    ArrayList<String> suraContent;

    public ArrayList<String> txtReader() {
        try {
            // count the file lines
            streamCountLines = this.getAssets().open(SouraContentActivity.suraFileName + ".txt");
            readerCountLines = new BufferedReader(new InputStreamReader(streamCountLines));
            try {
                while (readerCountLines.readLine() != null) {
                    countLinesOfText++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

// now you know how many lines in the text
            // then start to arrange the text in an array list to pass it to the adapter
            inputStream = this.getAssets().open(SouraContentActivity.suraFileName + ".txt");
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            try {
                // create the array list size with numbers of lines you count
                suraContent = new ArrayList<>(countLinesOfText);
                for (int i = 0; i < countLinesOfText; i++) {
                    suraContent.add(bufferedReader.readLine());
                }
            } catch (Exception f) {
                f.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return suraContent;
    }
}
