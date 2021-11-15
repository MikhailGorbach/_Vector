package com.example.vector.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.vector.domain.local.DataBase
import com.example.vector.domain.local.entity.UserDto
import com.example.vector.domain.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    init {
        val userDao = DataBase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    suspend fun findUser(login: String?): UserDto? = withContext(Dispatchers.IO) {
        return@withContext repository.findUser(login)
    }
}
