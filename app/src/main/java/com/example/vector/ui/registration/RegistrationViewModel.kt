package com.example.vector.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vector.domain.local.entity.UserDto
import com.example.vector.domain.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    fun addUser(login: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(login, email, password)
        }
    }

    suspend fun findUser(login: String?): UserDto? = withContext(Dispatchers.IO) {
        return@withContext repository.findUser(login)
    }
}
