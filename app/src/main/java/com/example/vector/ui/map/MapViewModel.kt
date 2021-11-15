package com.example.vector.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vector.domain.local.entity.MarkDto
import com.example.vector.domain.repositories.MarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val repository: MarkRepository) : ViewModel() {

    val readAllMarkers = repository.readAllMarkers

    fun deleteMark(markDto: MarkDto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMark(markDto)
        }
    }

    suspend fun findMarker(longitude: String, latitude: String): MarkDto? = withContext(Dispatchers.IO) {
        return@withContext repository.findMarker(longitude, latitude)
    }
}
