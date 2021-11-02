package com.example.vector.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.vector.MainActivity
import com.example.vector.R
import com.example.vector.databinding.FragmentLoginBinding
import com.example.vector.domain.local.entity.UserDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var mLoginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        mLoginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        if (isSignIn()) {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        } else {
            binding.enterBtn.setOnClickListener {
                lifecycleScope.launch(Main) {
                    with(binding) {
                        val user = findUser(loginEdt.text.toString().trim())
                        if (findUser(loginEdt.text.toString()) == null) {
                            loginTextInputLayout.error = "Такого пользователя не существует"
                            pwdTextInputLayout.error = null
                        } else if (user?.password != pwdEdt.text.toString()) {
                            pwdTextInputLayout.error = "Неправильный пароль"
                            loginTextInputLayout.error = null
                        } else if (user.login == loginEdt.text.toString()
                                .trim() && user.password == pwdEdt.text.toString().trim()
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
        sharedPreferences.edit().putString("USER_LOGIN", binding.loginEdt.text.toString()).apply()
    }

    private fun isSignIn(): Boolean {
        sharedPreferences =
            requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("USER_DEFINED", false)
    }

    private suspend fun findUser(loginText: String): UserDto? =
        withContext(Dispatchers.IO) {
            return@withContext mLoginViewModel.findUser(loginText)
        }
}
