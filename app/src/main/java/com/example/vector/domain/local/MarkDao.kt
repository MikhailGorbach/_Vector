package com.example.vector.domain.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.vector.domain.local.entity.MarkDto

@Dao
interface MarkDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlace(markDto: MarkDto)
}
