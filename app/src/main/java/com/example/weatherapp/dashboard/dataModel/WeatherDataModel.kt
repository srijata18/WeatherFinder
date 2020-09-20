package com.example.weatherapp.dashboard.dataModel

import androidx.room.Embedded

data class WeatherDataModel(
    @Embedded
    val coord : Coord? = null,
    @Embedded
    val weather : List<Weather>? = null,
    val base : String? = null,
    @Embedded
    val main : Main? = null,
    val visibility : Int? = null,
    @Embedded
    val wind : Wind? = null,
    val dt : Int? = null,
    @Embedded
    val sys : Sys? = null,
    val timezone : Int? = null,
    val name : String? = null,
    val cod : Int? = null
)

data class Coord (
    val lon : Double? = null,
    val lat : Double? = null
)

data class Main (
    val temp : Double? = null,
    val feels_like : Double? = null,
    val temp_min : Double? = null,
    val temp_max : Double? = null,
    val pressure : Int? = null,
    val humidity : Int? = null
)

data class Sys (
    val type : Int? = null,
    val country : String? = null,
    val sunrise : Int? = null,
    val sunset : Int? = null
)

data class Weather (
    val main : String? = null,
    val description : String? = null,
    val icon : String? = null
)

data class Wind (
    val speed : Double? = null,
    val deg : Int? = null
)