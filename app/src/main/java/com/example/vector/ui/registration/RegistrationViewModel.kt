package com.example.vector.ui.registration

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vector.domain.repositories.UserRepository
import com.example.vector.domain.local.DataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {

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
}
