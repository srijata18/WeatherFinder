package com.example.weatherapp.utils

import java.util.*
/*
*
* WeatherQuotes class responsible for generating quotes in accordance to the type of weather
* */
object WeatherQuotes {

    fun displayThoughts():String{
        val list = arrayListOf<String>()
        list.apply {
            add("Red skies at night, sailors delight.")
            add("Red sky in the morning, sailors take warning.....")
            add("They say, When your bone joint hurt a storm is coming....")
            add("Weather man says.. When the wind howls around corners and cracks, and down chimneys rain is coming.")
        }
        list.shuffle()
        return list.get(0)
    }

}