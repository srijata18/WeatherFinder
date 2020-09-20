package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.di.component.DaggerAppComponent
import com.example.weatherapp.utils.AppInitializer
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class WeatherApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
       DaggerAppComponent.builder().application(this).build().inject(this)
    }

}