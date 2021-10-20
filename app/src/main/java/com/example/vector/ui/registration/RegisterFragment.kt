package com.example.vector.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vector.R
import com.example.vector.databinding.FragmentRegisterBinding
import com.example.vector.domain.local.entity.UserDto
import com.example.vector.ui.login.LoginViewModel

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var mLoginViewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        mLoginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.registrationBtn.setOnClickListener {
            insertDataToDatabase()
        }
        return binding.root
    }

    private fun insertDataToDatabase() {
        if (inputCheck()) {
            val user = UserDto(
                login =
                binding.loginEt.text.toString().trim(),
                email = binding.emailEt.text.toString().trim(),
                password = binding.pwdFirstEt.text.toString().trim()
            )
            mLoginViewModel.addUser(user)
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun inputCheck(): Boolean {
        return (loginCheck() && emailCheck() && passwordCheck() && passwordMatch() && agreeCheck())
    }

    private fun loginCheck(): Boolean {
        if (binding.loginEt.text.toString().length < 5) {
            binding.loginTl.error = "Меньше 5 символов"
            return false
        }
        binding.loginTl.error = null
        return true
    }

    private fun emailCheck(): Boolean {
        val email = binding.emailEt.text.toString()
        if (email.length < 7 || !email.contains("@") || !(email.contains(".com") || email.contains(".ru"))) {
            binding.emailTl.error = "Неправильный адрес"
            return false
        }
        binding.emailTl.error = null
        return true
    }

    private fun passwordMatch(): Boolean {
        with(binding) {
            val pwdFirst = pwdFirstEt.text.toString()
            val pwdSecond = pwdSecondEt.text.toString()
            if (pwdFirst != pwdSecond) {
                pwdFirstTl.error = "Пароли не совпадают"
                pwdSecondTl.error = "Пароли не совпадают"
                return false
            }
            pwdFirstTl.error = null
            pwdSecondTl.error = null
            return true
        }
    }

    private fun passwordCheck(): Boolean {
        if (binding.pwdFirstEt.text.toString().length < 6) {
            binding.pwdFirstTl.error = "Меньше 6 символов"
            return false
        }
        binding.pwdFirstTl.error = null
        return true
    }

    private fun agreeCheck(): Boolean {
        return binding.agreeSwtch.isChecked
    }
}