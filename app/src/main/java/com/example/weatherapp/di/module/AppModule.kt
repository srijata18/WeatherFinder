package com.example.weatherapp.di.module

import android.app.Application
import android.content.Context
import com.example.weatherapp.networkClasses.UseCaseHandler
import com.example.weatherapp.networkClasses.retrofit.RetrofitClient
import com.example.weatherapp.networkClasses.retrofit.RetrofitServiceAnnotator
import com.example.weatherapp.dashboard.dataRepositories.DashboardDataRepository
import com.example.weatherapp.dashboard.dataRepositories.DashboardLocalRepository
import com.example.weatherapp.dashboard.dataRepositories.DashboardRemoteRepository
import com.example.weatherapp.dashboard.useCase.DummyUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    internal fun provideUseCaseHandler() = UseCaseHandler.getInstance()

    @Provides
    @Singleton
    internal fun provideRetrofitServiceAnnotator(): RetrofitServiceAnnotator = RetrofitClient.create()

    @Provides
    internal fun provideUseCase(dataRepository: DashboardDataRepository): DummyUseCase =
        DummyUseCase(dataRepository)

    @Provides
    internal fun provideRemoteData(retrofitServiceAnnotator: RetrofitServiceAnnotator) : DashboardRemoteRepository =
        DashboardRemoteRepository(
            retrofitServiceAnnotator
        )

    @Provides
    internal fun provideLocalData() : DashboardLocalRepository =
        DashboardLocalRepository()

    @Provides
    internal fun provideDataRepository(remoteRepository: DashboardRemoteRepository,
                                       localRepository: DashboardLocalRepository
    ) : DashboardDataRepository =
        DashboardDataRepository(
            remoteRepository,
            localRepository
        )

}