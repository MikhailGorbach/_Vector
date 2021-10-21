package com.example.vector.domain

import com.example.vector.domain.local.entity.UserDto
import com.example.vector.domain.local.UserDao

class UserRepository(private val userDao: UserDao) {

    suspend fun addUser(userDto: UserDto) {
        userDao.addUser(userDto)
    }

    fun findUser(login: String): UserDto? {
        return userDao.findUser(login)
    }
}