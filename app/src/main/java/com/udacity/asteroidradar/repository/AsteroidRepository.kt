package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.asDatabaseModel
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.asDomainModel
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.domain.AsteroidDomain
import com.udacity.asteroidradar.domain.PictureOfDayDomain
import com.udacity.asteroidradar.getLastDayOfWeek
import com.udacity.asteroidradar.getTodayDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(private val database: AsteroidDatabase) {

    private val _pictureOfDay = MutableLiveData<PictureOfDayDomain>()

    val pictureOfDay: LiveData<PictureOfDayDomain>
        get() = _pictureOfDay


    val asteroids: LiveData<List<AsteroidDomain>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }

    val todayAsteroids: LiveData<List<AsteroidDomain>> = Transformations.map(
        database.asteroidDao.getTodayAsteroids(
            getTodayDate()
        )
    ) {
        it.asDomainModel()
    }

    val weekAsteroids: LiveData<List<AsteroidDomain>> = Transformations.map(
        database.asteroidDao.getWeekAsteroids(
            getTodayDate(),
            getLastDayOfWeek()
        )
    ) {
        it.asDomainModel()
    }


    suspend fun refreshAsteroids(startDate: String ) {
        withContext(Dispatchers.IO) {
            val asteroidList = Network.asteroidService.getAsteroidlist(startDate).await()
            database.asteroidDao.insertAll(*parseAsteroidsJsonResult(JSONObject(asteroidList)).asDatabaseModel())
        }
    }


    suspend fun deleteOldAsteroid(date: String) {
        withContext(Dispatchers.IO) {
            database.asteroidDao.deleteOldAsteroids(date)
        }
    }


    suspend fun getImageOfDay() {
        withContext(Dispatchers.IO) {
            val image = Network.asteroidService.getImageOfDay().await()
            _pictureOfDay.postValue(image.asDomainModel())
        }
    }
}