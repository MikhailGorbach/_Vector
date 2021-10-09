package com.example.vector.screens

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.vector.MainActivity
import com.example.vector.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment: Fragment(R.layout.fragment_login) {
    lateinit var sharedPreferences: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        var isLogined = sharedPreferences.getBoolean("USER_DEFINED", false)
        if (isLogined) {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
        else {
            view.findViewById<Button>(R.id.enterBtn).setOnClickListener {
                isLogined = true
                saveData(isLogined)
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            }
        }
    }
    private fun saveData(isLogined: Boolean) {
        val loginText = loginEdt.text.toString()
        val passwordText = pwdEdt.text.toString()
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("STRING_KEY", "Login: " + loginText + "\nPassword: " + passwordText)
            putBoolean("USER_DEFINED", isLogined)
        }.apply()
        Toast.makeText(activity, "Data saved", Toast.LENGTH_LONG).show()
    }
}