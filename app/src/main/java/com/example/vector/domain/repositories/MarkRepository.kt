package com.example.vector.domain.repositories

import androidx.lifecycle.LiveData
import com.example.vector.domain.local.MarkDao
import com.example.vector.domain.local.entity.MarkDto

class MarkRepository(private val markDao: MarkDao) {

    val readAllMarkers: LiveData<List<MarkDto>> = markDao.readAllMarkers()

    suspend fun addMark(title: String, description: String, longitude: String, latitude: String) {
        val mark = MarkDto(title = title, description = description, longitude = longitude, latitude = latitude)
        markDao.addMark(mark)
    }

    suspend fun deleteMark(markDto: MarkDto) {
        markDao.deleteMark(markDto)
    }

    fun findMarker(longitude: String, latitude: String): MarkDto? {
        return markDao.findMarker(longitude, latitude)
    }
}