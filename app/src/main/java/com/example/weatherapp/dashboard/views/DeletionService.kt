package com.example.weatherapp.dashboard.views

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import androidx.room.Room
import com.example.weatherapp.dashboard.dataBase.AppDatabase
import com.example.weatherapp.utils.AppInitializer
import java.util.*

/*
* This service is responsible for clearing the data from database, after a time gap of 24hours
* */
class DeletionService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
    }

    override fun onStart(intent: Intent?, startId: Int) {
        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()
        val allList = db.weatherDao().allList()
        if (!allList.isNullOrEmpty()){
            db.weatherDao().deleteLocations()
        }
        onDestroy()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}