package com.example.weatherapp.networkClasses.retrofit

import com.example.weatherapp.networkClasses.retrofit.RequestUrl.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object {

        private const val TIME_OUT = 60L
        fun create(): RetrofitServiceAnnotator {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client1 = OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)

            val base = if (RequestUrl.APP_ID.isBlank()){
                RequestUrl.TEST_BASE_URL
            }else BASE_URL
            val retrofitClient = Retrofit.Builder()
                .baseUrl(base)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client1.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return retrofitClient.create(RetrofitServiceAnnotator::class.java)
        }
    }
}