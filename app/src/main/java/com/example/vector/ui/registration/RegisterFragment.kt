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
import kotlinx.android.synthetic.main.fragment_login.loginTextInputLayout

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var mRegistrationViewModel: RegistrationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        mRegistrationViewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)
        binding.registrationBtn.setOnClickListener {
            insertDataToDatabase()
        }
        return binding.root
    }

    private fun insertDataToDatabase() {
        if (inputCheck()) {
            val user = UserDto(
                login = binding.loginEt.text.toString().trim(),
                email = binding.emailEt.text.toString().trim(),
                password = binding.pwdFirstEt.text.toString().trim()
            )
            mRegistrationViewModel.addUser(user)
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun inputCheck(): Boolean {
        return (loginCheck() && emailCheck() && passwordCheck() && passwordMatch() && agreeCheck())
    }

    private fun loginCheck(): Boolean {
        with(binding) {
            if (loginEt.text.toString().length < 5) {
                loginRegTextInputLayout.error = "Меньше 5 символов"
                return false
            }
            loginRegTextInputLayout.error = null
            return true
        }
    }

    private fun emailCheck(): Boolean {
        val email = binding.emailEt.text.toString()
        if (email.length < 7 || !email.contains("@") || !(email.contains(".com") || email.contains(".ru"))) {
            binding.emailRegTextInputLayout.error = "Неправильный адрес"
            return false
        }
        binding.emailRegTextInputLayout.error = null
        return true
    }

    private fun passwordMatch(): Boolean {
        with(binding) {
            val pwdFirst = pwdFirstEt.text.toString()
            val pwdSecond = pwdSecondEt.text.toString()
            if (pwdFirst != pwdSecond) {
                pwdFirstTextInputLayout.error = "Пароли не совпадают"
                pwdSecondTextInputLayout.error = "Пароли не совпадают"
                return false
            }
            pwdFirstTextInputLayout.error = null
            pwdSecondTextInputLayout.error = null
            return true
        }
    }

    private fun passwordCheck(): Boolean {
        with(binding) {
            if (pwdFirstEt.text.toString().length < 6) {
                pwdFirstTextInputLayout.error = "Меньше 6 символов"
                return false
            }
            pwdFirstTextInputLayout.error = null
            return true
        }
    }

    private fun agreeCheck(): Boolean {
        return binding.agreeSwtch.isChecked
    }
}
