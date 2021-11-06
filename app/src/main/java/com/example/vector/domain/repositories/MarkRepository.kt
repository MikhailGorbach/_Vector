package com.example.vector.domain.repositories

import com.example.vector.domain.local.MarkDao
import com.example.vector.domain.local.entity.MarkDto

class MarkRepository(private val markDao: MarkDao) {

    suspend fun addMark(title: String, description: String, longitude: String, latitude: String) {
        val mark = MarkDto(title = title, description = description, longitude = longitude, latitude = latitude)
        markDao.addMark(mark)
    }

    suspend fun deleteMark(id: Int, title: String?, description: String?, longitude: String?, latitude: String?) {
        if (title != null && description != null && longitude != null && latitude != null) {
            val mark = MarkDto(id = id, title = title, description = description, longitude = longitude, latitude = latitude)
            markDao.deleteMark(mark)
        }
    }
}
