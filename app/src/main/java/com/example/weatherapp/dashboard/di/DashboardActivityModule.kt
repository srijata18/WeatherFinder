package com.example.weatherapp.dashboard.di

import com.example.weatherapp.networkClasses.UseCaseHandler
import com.example.weatherapp.dashboard.useCase.DummyUseCase
import com.example.weatherapp.dashboard.viewModel.DashboardViewModel
import dagger.Module
import dagger.Provides

@Module
class DashboardActivityModule {

    @Provides
    fun provideMainViewModel(useCaseHandler: UseCaseHandler, dummyUseCase: DummyUseCase): DashboardViewModel {
        return DashboardViewModel(
            useCaseHandler,
            dummyUseCase
        )
    }
}