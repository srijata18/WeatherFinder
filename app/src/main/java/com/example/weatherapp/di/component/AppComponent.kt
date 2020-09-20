package com.example.weatherapp.di.component

import com.example.weatherapp.WeatherApplication
import com.example.weatherapp.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Component(modules = [(AndroidInjectionModule::class), (AppModule::class), (ActivityBuilder::class)])
@Singleton
abstract class AppComponent : AndroidInjector<WeatherApplication>{
    abstract override fun inject(instance: WeatherApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: WeatherApplication): Builder
        fun build(): AppComponent
    }
}