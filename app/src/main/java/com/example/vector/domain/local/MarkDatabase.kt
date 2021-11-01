package com.example.vector.domain.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vector.domain.local.entity.MarkDto

@Database(entities = [MarkDto::class], version = 1, exportSchema = false)
abstract class MarkDatabase : RoomDatabase() {

    abstract fun markDao(): MarkDao

    companion object {
        @Volatile
        private var INSTANCE: MarkDatabase? = null

        fun getDatabase(context: Context): MarkDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MarkDatabase::class.java,
                    "mark_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
