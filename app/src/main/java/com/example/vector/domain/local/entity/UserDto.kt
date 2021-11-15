package com.example.vector.domain.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val login: String,
    val email: String,
    val password: String
)
