package com.example.islamiapp.API;

import com.example.islamiapp.API.Model.RadiosResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Apis {

    @GET("radio/radio_ar.json")
    Call<RadiosResponse>getRadioChannels();

}
