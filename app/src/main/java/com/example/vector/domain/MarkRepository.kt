package com.example.vector.domain

import com.example.vector.domain.local.MarkDao
import com.example.vector.domain.local.entity.MarkDto

class MarkRepository(private val markDao: MarkDao) {

    suspend fun addMark(title: String, description: String, longitude: String, latitude: String) {
        val mark = MarkDto(title = title, description = description, longitude = longitude, latitude = latitude)
        markDao.addMark(mark)
    }
}
