package com.example.weatherapp.dashboard.useCase

import com.example.weatherapp.dashboard.dataBase.AppDatabase

interface IDataSource{
    interface getDetails{
        fun onPostSuccess(response: Any)
        fun onPostFailure(errorMsg: String, code: Int? = 0)
    }
    fun getVariantDetails(callBack: getDetails, db : AppDatabase? = null)
}