package com.example.weatherapp.dashboard.dataRepositories

import android.util.Log
import com.example.weatherapp.dashboard.dataBase.AppDatabase
import com.example.weatherapp.dashboard.useCase.IDataSource
import com.example.weatherapp.utils.AppInitializer

class DashboardDataRepository (val remoteRepository: DashboardRemoteRepository,
                               val localRepository: DashboardLocalRepository
) : IDataSource {

    override fun getVariantDetails(callBack: IDataSource.getDetails, db : AppDatabase?) {

        if (db != null) {
            localRepository.setAppDatabase(db)
            Log.i("---info---","$db , ${AppInitializer.location} ${localRepository.dbResponse}")
        }
        val localRes = localRepository.dbResponse//localRepository.getVariantDetails(callBack)

        if (localRes!= null){
            AppInitializer.isFromLocalStorage = true
            localRepository.getVariantDetails(object :
                IDataSource.getDetails{
                override fun onPostSuccess(response: Any) {
                    callBack.onPostSuccess(response)
                }

                override fun onPostFailure(errorMsg: String, code: Int?) {
                }
            })
        }else
            remoteRepository.getVariantDetails(object :
                IDataSource.getDetails{
                override fun onPostSuccess(response: Any) {
                    callBack.onPostSuccess(response)
                }

                override fun onPostFailure(errorMsg: String, code: Int?) {
                    callBack.onPostFailure(errorMsg, code)
                }
            })
    }

}