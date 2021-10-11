package com.example.vector.screens

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vector.MainActivity
import com.example.vector.R
import com.example.vector.data.UserViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment: Fragment(R.layout.fragment_login) {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var mUserViewModel: UserViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        sharedPreferences = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        var isLogined = sharedPreferences.getBoolean("USER_DEFINED", false)
        if (isLogined) {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
        else {
            view.findViewById<Button>(R.id.enterBtn).setOnClickListener {
                if (userDefined()) {
                    isLogined = true
                    val loginText = loginEdt.text.toString()
                    val passwordText = pwdEdt.text.toString()
                    saveSession(isLogined, loginText, passwordText)
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                }
            }
        }
    }
    private fun userDefined(): Boolean {
        val loginText = loginEdt.text.toString()
        val passwordText = pwdEdt.text.toString()
        val user = mUserViewModel.findUser(loginText, passwordText)
        if (user == null) {
            Toast.makeText(activity, "Пользователь не найден", Toast.LENGTH_SHORT).show()
            return false
        }
        else {
            Toast.makeText(activity, "Пользователь найден", Toast.LENGTH_SHORT).show()
            return true
        }
    }
    private fun saveSession(isLogined: Boolean, loginText: String, passwordText: String) {
        val editor = sharedPreferences.edit()
        editor.apply {
            putBoolean("USER_DEFINED", isLogined)
            putString("USER_LOGIN_PASSWORD", loginText + " " + passwordText)
            }.apply()
        Toast.makeText(activity, "Данные сохранены", Toast.LENGTH_SHORT).show()
    }
}