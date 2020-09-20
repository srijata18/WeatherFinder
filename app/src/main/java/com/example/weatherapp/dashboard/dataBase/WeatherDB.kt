package com.example.weatherapp.dashboard.dataBase

import androidx.room.*
import com.example.weatherapp.dashboard.dataModel.WeatherDataModel
import java.util.*

@Entity
data class WeatherDB(
    @PrimaryKey(autoGenerate = true) val id: Int = 101,
    @ColumnInfo(name = "location_name") val locationName: String?,
    @TypeConverters(RequestConverter::class)
    @ColumnInfo(name = "weatherModel") val weatherModel: String?
)
