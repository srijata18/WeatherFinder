package com.example.weatherapp.dashboard.dataRepositories

import android.util.Log
import com.example.weatherapp.dashboard.dataBase.AppDatabase
import com.example.weatherapp.dashboard.dataModel.WeatherDataModel
import com.example.weatherapp.networkClasses.retrofit.RequestUrl
import com.example.weatherapp.networkClasses.retrofit.RetrofitServiceAnnotator
import com.example.weatherapp.dashboard.useCase.IDataSource
import com.example.weatherapp.utils.AppInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class DashboardRemoteRepository (private val retrofitServiceAnnotator: RetrofitServiceAnnotator):
    IDataSource {

    override fun getVariantDetails(callBack: IDataSource.getDetails, db:AppDatabase?) {
        val url = if (RequestUrl.APP_ID.isBlank()){
            "${RequestUrl.TEST_BASE_URL}${RequestUrl.TEST_URL}"
        }
        else String.format("${RequestUrl.BASE_URL}${RequestUrl.DATA_URL}",AppInitializer.location)
        val call = retrofitServiceAnnotator.doGetRequest(url)
        call.enqueue(object : Callback<WeatherDataModel> {
            override fun onFailure(call: Call<WeatherDataModel>, t: Throwable) {
                if (t is HttpException)
                    callBack.onPostFailure(t.message(),t.code())
                else
                    t.message?.let { callBack.onPostFailure(it) }

            }
            override fun onResponse(call: Call<WeatherDataModel>, response: Response<WeatherDataModel>) {
                if(response.body() != null && response.body() is WeatherDataModel){
                    callBack.onPostSuccess(response.body() as WeatherDataModel)
                }else{
                    callBack.onPostFailure(response.message(),response.code())
                }
            }
        })
    }

}