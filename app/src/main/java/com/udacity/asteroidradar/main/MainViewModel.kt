package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.domain.AsteroidDomain
import com.udacity.asteroidradar.getTodayDate
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = getDatabase(application)
    private val nasaRepository = AsteroidRepository(db)

    private val _navigateToDetails = MutableLiveData<AsteroidDomain?>()


    val navigateToAsteroidDetails: LiveData<AsteroidDomain?>
        get() = _navigateToDetails

    fun displayAsteroidDetails(asteroid: AsteroidDomain) {
        _navigateToDetails.value = asteroid
    }

    fun displayAsteroidDetailsComplete() {
        _navigateToDetails.value = null
    }

    private val timeFilter = MutableLiveData<TimeFilter>()

    init {
        timeFilter.value = TimeFilter.TODAY
        viewModelScope.launch {

            try {
                nasaRepository.refreshAsteroids(getTodayDate())
            } catch (e: Exception) {

            }
        }
        viewModelScope.launch {
            try {
                nasaRepository.getImageOfDay()
            } catch (e: Exception) {

            }
        }
    }

    val imageOfDay = nasaRepository.pictureOfDay
    val asteroidList = Transformations.switchMap(timeFilter) { filter ->
        return@switchMap when (filter) {
            TimeFilter.TODAY -> nasaRepository.todayAsteroids
            TimeFilter.WEEK -> nasaRepository.weekAsteroids
            TimeFilter.ALL -> nasaRepository.asteroids
        }
    }

    fun setFilterAll() {
        timeFilter.value = TimeFilter.ALL
    }

    fun setFilterWeek() {
        timeFilter.value = TimeFilter.WEEK

    }

    fun setFilterToday() {
        timeFilter.value = TimeFilter.TODAY
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}