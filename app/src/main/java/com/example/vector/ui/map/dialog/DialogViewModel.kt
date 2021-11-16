package com.example.vector.ui.map.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vector.domain.repositories.MarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DialogViewModel @Inject constructor(private val repository: MarkRepository) : ViewModel() {

    fun addMark(title: String, description: String, longitude: String, latitude: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMark(title, description, longitude, latitude)
        }
    }
}