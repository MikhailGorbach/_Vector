package com.example.vector.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.vector.MainActivity
import com.example.vector.R
import com.example.vector.databinding.FragmentLoginBinding
import com.example.vector.domain.local.entity.UserDto
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val mLoginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        if (isSignIn()) {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        } else {
            binding.enterBtn.setOnClickListener {
                lifecycleScope.launch(Main) {
                    with(binding) {
                        val user = findUser(loginTextInputEditText.text.toString().trim())
                        if (findUser(loginTextInputEditText.text.toString().trim()) == null) {
                            loginTextInputLayout.error = "Такого пользователя не существует"
                            pwdTextInputLayout.error = null
                        } else if (user?.password != pwdTextInputEditText.text.toString().trim()) {
                            pwdTextInputLayout.error = "Неправильный пароль"
                            loginTextInputLayout.error = null
                        } else if (user.login == loginTextInputEditText.text.toString().trim() && user.password == pwdTextInputEditText.text.toString().trim()
                        ) {
                            saveSession()
                            startActivity(Intent(requireActivity(), MainActivity::class.java))
                        }
                    }
                }
            }
        }
        return binding.root
    }

    private fun saveSession() {
        sharedPreferences.edit().putBoolean("USER_DEFINED", true).apply()
        sharedPreferences.edit().putString("USER_LOGIN", binding.loginTextInputEditText.text.toString().trim()).apply()
    }

    private fun isSignIn(): Boolean {
        sharedPreferences =
            requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("USER_DEFINED", false)
    }

    private suspend fun findUser(loginText: String): UserDto? {
        return mLoginViewModel.findUser(loginText)
    }
}
