package com.example.weatherapp.networkClasses.retrofit

class RequestUrl {

    companion object {

        const val BASE_URL = "http://api.openweathermap.org/"
        //add valid app id for proper construction of the url
        const val APP_ID = ""
        const val VERSION = "2.5"
        const val DATA_URL = "data/$VERSION/weather?q=%s&appid=$APP_ID"


        const val TEST_BASE_URL = "https://samples.openweathermap.org/"
        const val TEST_URL = "data/2.5/weather?q=London,uk&appid=439d4b804bc8187953eb36d2a8c26a02"
    }
}