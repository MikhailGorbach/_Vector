package com.example.vector.ui.registration

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vector.domain.repositories.UserRepository
import com.example.vector.domain.local.DataBase
import com.example.vector.domain.local.entity.UserDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    init {
        val userDao = DataBase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    fun addUser(login: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(login, email, password)
        }
    }

    suspend fun findUser(login: String?): UserDto? = withContext(Dispatchers.IO) {
        return@withContext repository.findUser(login)
    }
}

