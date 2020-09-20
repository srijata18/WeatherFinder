package com.example.weatherapp.di.component

import com.example.weatherapp.dashboard.views.DashBoardActivity
import com.example.weatherapp.dashboard.di.DashboardActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [DashboardActivityModule::class])
    internal abstract fun bindMainActivity(): DashBoardActivity


}