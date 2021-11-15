package com.example.vector.ui.login

import androidx.lifecycle.ViewModel
import com.example.vector.domain.local.entity.UserDto
import com.example.vector.domain.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    suspend fun findUser(login: String?): UserDto? = withContext(Dispatchers.IO) {
        return@withContext repository.findUser(login)
    }
}
