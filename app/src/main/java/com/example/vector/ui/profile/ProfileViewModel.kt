package com.example.vector.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.vector.domain.UserRepository
import com.example.vector.domain.local.UserDatabase
import com.example.vector.domain.local.entity.UserDto

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    fun findUser(login: String?): UserDto? {
        return repository.findUser(login)
    }
}
