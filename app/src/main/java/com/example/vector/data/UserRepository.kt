package com.example.vector.data

class UserRepository(private val userDao: UserDao) {

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }

    fun findUser(login: String, password: String): User? {
        return userDao.findUser(login, password).value
    }
}