package com.example.vector.ui.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.vector.domain.local.DataBase
import com.example.vector.domain.local.entity.MarkDto
import com.example.vector.domain.repositories.MarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    val readAllMarkers: LiveData<List<MarkDto>>
    private val repository: MarkRepository

    init {
        val markDao = DataBase.getDatabase(application).markDao()
        repository = MarkRepository(markDao)
        readAllMarkers = repository.readAllMarkers
    }

    fun deleteMark(markDto: MarkDto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMark(markDto)
        }
    }

    suspend fun findMarker(longitude: String, latitude: String): MarkDto? = withContext(Dispatchers.IO) {
        return@withContext repository.findMarker(longitude, latitude)
    }
}
