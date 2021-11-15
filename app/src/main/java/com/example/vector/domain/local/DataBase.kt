package com.example.vector.domain.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vector.domain.local.entity.MarkDto
import com.example.vector.domain.local.entity.UserDto

@Database(entities = [UserDto::class, MarkDto::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun markDao(): MarkDao

    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null

        fun getDatabase(context: Context): DataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
