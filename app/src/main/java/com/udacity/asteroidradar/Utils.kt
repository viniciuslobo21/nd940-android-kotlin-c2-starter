package com.udacity.asteroidradar

import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.database.AsteroidEntity
import java.text.SimpleDateFormat
import java.util.*

fun getTodayDate(): String {
    val calendar = Calendar.getInstance()
    val df = SimpleDateFormat("yyyy-MM-dd");
    return df.format(calendar.time)
}

fun getLastDayOfWeek(): String {
    val calendar = Calendar.getInstance()
    calendar.add(7, Calendar.DAY_OF_YEAR)
    val df = SimpleDateFormat("yyyy-MM-dd");
    return df.format(calendar.time)
}