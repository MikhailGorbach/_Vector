package com.example.vector.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.vector.domain.UserRepository
import com.example.vector.domain.local.DataBase
import com.example.vector.domain.local.entity.UserDto

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    init {
        val userDao = DataBase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    fun findUser(login: String?): UserDto? {
        return repository.findUser(login)
    }
}
