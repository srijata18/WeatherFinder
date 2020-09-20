package com.example.weatherapp.dashboard.dataRepositories

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.weatherapp.dashboard.dataBase.AppDatabase
import com.example.weatherapp.dashboard.dataBase.RequestConverter
import com.example.weatherapp.dashboard.useCase.IDataSource
import com.example.weatherapp.utils.AppInitializer

class DashboardLocalRepository() :
    IDataSource {

    private var db : AppDatabase? = null
    var dbResponse : String? = null

    override fun getVariantDetails(callBack: IDataSource.getDetails, dbValue: AppDatabase?) {
        if (dbResponse != null){
            val res = RequestConverter().stringToModel(dbResponse)
            if (res != null) {
                callBack.onPostSuccess(res)
            }
        }
    }

    fun setAppDatabase(dbValue : AppDatabase){
        db = dbValue
        dbResponse = db?.weatherDao()?.loadLocationWeather(AppInitializer.location.toUpperCase())
    }
}