package com.example.vector.ui.way

import androidx.lifecycle.ViewModel
import com.example.vector.domain.repositories.MarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WayViewModel @Inject constructor(repository: MarkRepository) : ViewModel() {

    val readAllMarkers = repository.readAllMarkers
}