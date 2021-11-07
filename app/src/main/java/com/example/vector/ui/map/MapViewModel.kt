package com.example.vector.ui.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.vector.domain.local.DataBase
import com.example.vector.domain.local.entity.MarkDto
import com.example.vector.domain.repositories.MarkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MarkRepository
    val readAllMarkers: LiveData<List<MarkDto>>

    init {
        val markDao = DataBase.getDatabase(application).markDao()
        repository = MarkRepository(markDao)
        readAllMarkers = repository.readAllMarkers
    }

    fun addMark(title: String, description: String, longitude: String, latitude: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMark(title, description, longitude, latitude)
        }
    }

    fun deleteMark(markDto: MarkDto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMark(markDto)
        }
    }

    fun findMarker(longitude: String, latitude: String): MarkDto? {
        return repository.findMarker(longitude, latitude)
    }
}