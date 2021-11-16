package com.example.vector.domain.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vector.domain.local.entity.MarkDto

@Dao
interface MarkDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMark(markDto: MarkDto)

    @Delete
    suspend fun deleteMark(markDto: MarkDto)

    @Query("SELECT * FROM place_table m WHERE m.longitude = :longitude AND m.latitude = :latitude")
    fun findMarker(longitude: String, latitude: String): MarkDto?

    @Query("SELECT * FROM place_table ORDER BY id ASC")
    fun readAllMarkers(): LiveData<List<MarkDto>>
}
