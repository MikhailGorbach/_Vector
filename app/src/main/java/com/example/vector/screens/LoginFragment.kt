package com.example.vector.screens

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.vector.MainActivity
import com.example.vector.R
import com.example.vector.data.User
import com.example.vector.data.UserViewModel
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_login.loginEdt
import kotlinx.android.synthetic.main.fragment_login.pwdEdt
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var mUserViewModel: UserViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        sharedPreferences =
            requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        var isLogined = sharedPreferences.getBoolean("USER_DEFINED", false)
        if (isLogined) {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        } else {
            view.findViewById<Button>(R.id.enterBtn).setOnClickListener {
                lifecycleScope.launch {
                    if (userDefined()) {
                        isLogined = true
                        saveSession(isLogined)
                        startActivity(Intent(requireActivity(), MainActivity::class.java))
                    } else {
                        view.findViewById<TextInputLayout>(R.id.loginTil).error = "Неправильный логин или пароль"
                        view.findViewById<TextInputLayout>(R.id.pwdTil).error = "Неправильный логин или пароль"
                    }
                }
            }
        }
    }

    private suspend fun userDefined(): Boolean {
        val loginText = loginEdt.text.toString()
        val passwordText = pwdEdt.text.toString()
        var user: User?
        coroutineScope {
            user = async(IO) { mUserViewModel.findUser(loginText, passwordText) }.await()
        }
        return user != null
    }

    private fun saveSession(isLogined: Boolean) {
        val editor = sharedPreferences.edit()
        editor.apply {
            putBoolean("USER_DEFINED", isLogined)
        }.apply()
    }
}