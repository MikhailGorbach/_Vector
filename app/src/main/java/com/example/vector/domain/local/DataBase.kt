package com.example.vector.domain.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vector.domain.local.entity.MarkDto
import com.example.vector.domain.local.entity.UserDto

@Database(entities = [UserDto::class, MarkDto::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun markDao(): MarkDao
}
