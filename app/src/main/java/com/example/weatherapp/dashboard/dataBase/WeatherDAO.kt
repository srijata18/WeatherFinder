package com.example.weatherapp.dashboard.dataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.weatherapp.dashboard.dataModel.WeatherDataModel

@Dao
interface WeatherDAO {

    @Query("SELECT weatherModel FROM weatherdb WHERE location_name like Upper(:locationName)")
    fun loadLocationWeather(locationName: String): String?

    @Insert
    fun insertLocation(weatherDataModel: WeatherDB)

    @Query("DELETE FROM WeatherDB")
    fun deleteLocations()

    @Query("SELECT * FROM weatherdb")
    fun allList(): List<WeatherDB>
}