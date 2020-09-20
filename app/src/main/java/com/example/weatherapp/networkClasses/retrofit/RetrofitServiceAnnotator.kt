package com.example.weatherapp.networkClasses.retrofit

import com.example.weatherapp.dashboard.dataModel.WeatherDataModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface RetrofitServiceAnnotator {

    @GET
    fun doGetRequest(@Url url : String) : Call<WeatherDataModel>

}