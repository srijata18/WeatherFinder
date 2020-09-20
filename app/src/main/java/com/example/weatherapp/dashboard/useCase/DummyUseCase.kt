package com.example.weatherapp.dashboard.useCase

import com.example.weatherapp.dashboard.dataBase.AppDatabase
import com.example.weatherapp.networkClasses.UseCase
import com.example.weatherapp.dashboard.dataRepositories.DashboardDataRepository

class DummyUseCase(private val dataRepository: DashboardDataRepository) :
    UseCase<DummyUseCase.RequestValues, DummyUseCase.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues?) {
        dataRepository.getVariantDetails(
            object :
                IDataSource.getDetails {
                override fun onPostSuccess(response: Any) {
                    val res =
                        ResponseValue(
                            response
                        )
                    useCaseCallback?.onSuccess(res)
                }

                override fun onPostFailure(errorMsg: String, code: Int?) {
                    useCaseCallback?.onError(errorMsg, code)
                }
            }, requestValues?.db
        )
    }

    class RequestValues(val db : AppDatabase) :
        UseCase.RequestValues

    class ResponseValue(val response: Any) : UseCase.ResponseValue

}