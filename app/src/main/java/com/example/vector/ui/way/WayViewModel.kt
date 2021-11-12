package com.example.vector.ui.way

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.vector.domain.local.DataBase
import com.example.vector.domain.local.entity.MarkDto
import com.example.vector.domain.repositories.MarkRepository

class WayViewModel(application: Application) : AndroidViewModel(application) {

    val readAllMarkers: LiveData<List<MarkDto>>
    private val repository: MarkRepository

    init {
        val markDao = DataBase.getDatabase(application).markDao()
        repository = MarkRepository(markDao)
        readAllMarkers = repository.readAllMarkers
    }
}