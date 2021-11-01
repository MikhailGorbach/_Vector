package com.example.vector.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vector.domain.MarkRepository
import com.example.vector.domain.local.DataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MarkViewModel(application: Application) : AndroidViewModel(application) {

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
}
