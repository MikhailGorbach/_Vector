package com.example.vector.ui.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vector.domain.local.DataBase
import com.example.vector.domain.repositories.MarkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MarkRepository

    init {
        val markDao = DataBase.getDatabase(application).markDao()
        repository = MarkRepository(markDao)
    }

    fun addMark(title: String, description: String, longitude: String, latitude: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMark(title, description, longitude, latitude)
        }
    }

    fun deleteMark(id: Int, title: String?, description: String?, longitude: String?, latitude: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMark(id, title, description, longitude, latitude)
        }
    }
}