package com.example.vector.data

import androidx.lifecycle.LiveData
import java.net.UnknownServiceException

class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }
    fun findUser(login: String, password : String) : User?{
        return userDao.findUser(login, password).value
    }
}