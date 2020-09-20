package com.example.weatherapp.dashboard.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.room.RoomDatabase
import com.example.weatherapp.dashboard.dataBase.AppDatabase
import com.example.weatherapp.dashboard.dataBase.RequestConverter
import com.example.weatherapp.dashboard.dataBase.WeatherDB
import com.example.weatherapp.view.base.BaseViewModel
import com.example.weatherapp.networkClasses.ErrorModel
import com.example.weatherapp.networkClasses.UseCase
import com.example.weatherapp.networkClasses.UseCaseHandler
import com.example.weatherapp.dashboard.dataModel.WeatherDataModel
import com.example.weatherapp.dashboard.useCase.DummyUseCase
import com.example.weatherapp.utils.AppInitializer
import java.util.*

class DashboardViewModel(
    override val useCaseHandler: UseCaseHandler,
    val dummyUseCase: DummyUseCase
) : BaseViewModel(useCaseHandler) {

    val receivedResponse = MutableLiveData<WeatherDataModel>()
    val errorModel = MutableLiveData<ErrorModel>()

    fun fetchDetails(db : AppDatabase){
        val requestValue = DummyUseCase.RequestValues(db)
        useCaseHandler.execute(
            dummyUseCase, requestValue,
            object : UseCase.UseCaseCallback<DummyUseCase.ResponseValue>{
                override fun onSuccess(response: DummyUseCase.ResponseValue) {
                    if (response.response is WeatherDataModel) {
                        val locDetails = RequestConverter().modelToString(response.response)
                        if(!AppInitializer.isFromLocalStorage) {
                            db.weatherDao()
                                .insertLocation(WeatherDB(0, response.response.name, locDetails))
                            for (i in db.weatherDao().allList())
                                Log.i("---info---", "${i.locationName}")
                        }
                        receivedResponse.value = response.response
                    }
                }

                override fun onError(t: String, code: Int?) {
                    errorModel.value = ErrorModel(t,code)
                }
            }
        )
    }


}