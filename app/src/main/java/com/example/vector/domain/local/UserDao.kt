package com.example.vector.domain.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vector.domain.local.entity.UserDto

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(userDto: UserDto)

    @Query("SELECT * FROM user_table u WHERE u.login = :login")
    fun findUser(login: String): UserDto?
}
