package com.example.vector.domain.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.vector.domain.local.entity.MarkDto
import com.google.android.gms.maps.model.LatLng

@Dao
interface MarkDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMark(markDto: MarkDto)

    @Delete
    suspend fun deleteMark(markDto: MarkDto)
}
