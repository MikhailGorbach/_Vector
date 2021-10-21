package com.example.vector.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.vector.domain.UserRepository
import com.example.vector.domain.local.UserDatabase
import com.example.vector.domain.local.entity.UserDto

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    fun findUser(login: String): UserDto? {
        return repository.findUser(login)
    }
}