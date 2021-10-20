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
import kotlinx.android.synthetic.main.fragment_login.loginEdt
import kotlinx.android.synthetic.main.fragment_login.pwdEdt
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
        mLoginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        if (isSignIn()) {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        } else {
            binding.enterBtn.setOnClickListener {
                lifecycleScope.launch(Main) {
                    if (userDefined(
                            loginEdt.text.toString().trim(),
                            pwdEdt.text.toString().trim()
                        )
                    ) {
                        saveSession()
                        startActivity(Intent(requireActivity(), MainActivity::class.java))
                    } else {
                        binding.loginTil.error = "Неправильный логин или пароль"
                    }
                }
            }
        }
        return binding.root
    }

    //Добавить проверку на существование пользователя с ошибкой - Пользователь не существует
    // Добавить проверку несовпадения паролей

    private fun saveSession() {
        sharedPreferences.edit().putBoolean("USER_DEFINED", true).apply()
    }

    private fun isSignIn(): Boolean {
        sharedPreferences =
            requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("USER_DEFINED", false)
    }

    private suspend fun userDefined(loginText: String, passwordText: String): Boolean =
        withContext(Dispatchers.IO) {
            val user = mLoginViewModel.findUser(loginText, passwordText)
            return@withContext user != null
        }
}
