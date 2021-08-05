package com.example.islamiapp.TabsFragment.radio;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.islamiapp.API.APIManager;
import com.example.islamiapp.API.Model.RadiosItem;
import com.example.islamiapp.API.Model.RadiosResponse;
import com.example.islamiapp.Base.BaseFragment;
import com.example.islamiapp.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class RadioFragment extends BaseFragment {
    View view;
    RadioChannelsAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;


    public RadioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_radio, container, false);
        recyclerView = view.findViewById(R.id.channels_recycler_view);
        adapter = new RadioChannelsAdapter(null);
        layoutManager = new LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        adapter.setOnPlayClickListener(new RadioChannelsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, RadiosItem channel) {
                playRadio(channel.getURL());
            }
        });
        adapter.setOnStopClickListener(new RadioChannelsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, RadiosItem channel) {
                stopRadio();
            }
        });
        getRadioChannels();

        return view;
    }

    MediaPlayer mediaPlayer;

    public void stopRadio(){
        if(mediaPlayer!=null)
            mediaPlayer.stop();
        Toast.makeText(activity, "You click stop", Toast.LENGTH_SHORT).show();
    }

    public void playRadio(String URL){
        stopRadio();
        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(URL);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    Toast.makeText(activity, "You click start", Toast.LENGTH_SHORT).show();
                }

            });
//            mediaPlayer.start();
        } catch (IOException e) {
            showMessage(R.string.error,R.string.cannot_play_channel);
        }




    }

    public void getRadioChannels(){
        showProgressBar(R.string.loading,R.string.please_wait);
        APIManager.getApis()
                .getRadioChannels()
                .enqueue(new Callback<RadiosResponse>() {
                    @Override
                    public void onResponse(Call<RadiosResponse> call,
                                           Response<RadiosResponse> response) {
                        hideProgressBar();
                        if(response.isSuccessful()){
                            adapter.changeData(response.body().getRadios());
                        }else {
                            //  response.code()
                            showMessage(R.string.error,R.string.cannot_connect_to_server);
                        }

                    }

                    @Override
                    public void onFailure(Call<RadiosResponse> call,
                                          Throwable t) {
                        hideProgressBar();
                        showMessage(getString(R.string.error),t.getLocalizedMessage());
                        // showMessage(R.string.error,R.string.cannot_connect_to_server);

                    }
                });


    }

}
