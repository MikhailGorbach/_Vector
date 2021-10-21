package com.example.vector.domain

import com.example.vector.domain.local.entity.UserDto
import com.example.vector.domain.local.UserDao

class UserRepository(private val userDao: UserDao) {

    suspend fun addUser(login: String, email: String, password: String) {
        val user = UserDto(login = login, email = email, password = password)
        userDao.addUser(user)
    }

    fun findUser(login: String?): UserDto? {
        return userDao.findUser(login)
    }
}
