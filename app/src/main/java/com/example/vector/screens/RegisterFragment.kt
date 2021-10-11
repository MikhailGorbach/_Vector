package com.example.vector.screens

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vector.R
import com.example.vector.data.User
import com.example.vector.data.UserViewModel
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment: Fragment(R.layout.fragment_register)
{
    private lateinit var mUserViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        view.findViewById<AppCompatButton>(R.id.registrationBtn).setOnClickListener {
            insertDataToDatabase()
        }
        return view
    }
    private fun insertDataToDatabase() {
        val login = loginEt.text.toString()
        val email = emailEt.text.toString()
        val password = pwdFirstEt.text.toString()
        val passwordAgain = pwdSecondEt.text.toString()
        val agree = agreeSwtch.isChecked
        if (inputCheck(login, email, password, passwordAgain) && agree) {
            val user = User(0, login, email, password)
            mUserViewModel.addUser(user)
            Toast.makeText(activity, "Пользователь добавлен", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        else {
            Toast.makeText(activity, "Введите корректные данные", Toast.LENGTH_SHORT).show()
        }
    }
    private fun inputCheck(login: String, email: String, password: String, passwordAgain: String): Boolean {
        return !(TextUtils.isEmpty(login) || TextUtils.isEmpty(email)
                || TextUtils.isEmpty(password) || passwordAgain != password
                || !email.contains("@") || password.length < 6
                || login.length < 5 || email.length < 5)
    }
}