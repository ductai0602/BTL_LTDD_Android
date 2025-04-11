package com.example.finaltest_ltdd.ViewModel

import androidx.lifecycle.LiveData
import com.example.finaltest_ltdd.Domain.LocationModel
import com.example.finaltest_ltdd.Repository.MainRepository

class MainViewModel {
    private val repository = MainRepository()

    fun loadLocations(): LiveData<MutableList<LocationModel>> {
        return repository.loadLocation()
    }
}