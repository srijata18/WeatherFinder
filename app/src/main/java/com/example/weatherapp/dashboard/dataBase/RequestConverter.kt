package com.example.weatherapp.dashboard.dataBase

import androidx.room.TypeConverter
import com.example.weatherapp.dashboard.dataModel.WeatherDataModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class RequestConverter {
    private val gson = Gson()

    @TypeConverter
    fun stringToModel(data: String?): WeatherDataModel? {
        val listType =
            object : TypeToken<WeatherDataModel>() {}.type
        return gson.fromJson<WeatherDataModel>(data, listType)
    }

    @TypeConverter
    fun modelToString(someObjects: WeatherDataModel?): String? {
        return gson.toJson(someObjects)
    }
}