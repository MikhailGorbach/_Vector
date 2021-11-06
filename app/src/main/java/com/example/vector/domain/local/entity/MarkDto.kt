package com.example.vector.domain.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "place_table")
data class MarkDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val latitude: String,
    val longitude: String
)