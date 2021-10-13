package com.example.vector.screens

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.vector.MainActivity
import com.example.vector.R
import com.example.vector.data.User
import com.example.vector.data.UserViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlin.coroutines.suspendCoroutine

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
                    }
                }
            }
        }
    }

    private suspend fun userDefined(): Boolean {
        val loginText = loginEdt.text.toString()
        val passwordText = pwdEdt.text.toString()
        var user: Deferred<User?>
        var us: User?
        coroutineScope {
            user = async(IO) { mUserViewModel.findUser(loginText, passwordText) }
            us = user.await()
        }
        return us != null
    }

    private fun saveSession(isLogined: Boolean) {
        val editor = sharedPreferences.edit()
        editor.apply {
            putBoolean("USER_DEFINED", isLogined)
        }.apply()
    }
}