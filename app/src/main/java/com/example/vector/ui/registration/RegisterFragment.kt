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
        with(binding) {
            if (inputCheck()) {
                mRegistrationViewModel.addUser(
                    loginEt.text.toString(),
                    emailEt.text.toString(),
                    pwdFirstEt.text.toString()
                )
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }

    private fun inputCheck(): Boolean {
        return (loginCheck() && emailCheck() && passwordCheck() && passwordMatch() && agreeCheck())
    }

    private fun loginCheck(): Boolean {
        with(binding) {
            return if (loginEt.text.toString().length < 5) {
                loginRegTextInputLayout.error = "Меньше 5 символов"
                false
            } else {
                loginRegTextInputLayout.error = null
                true
            }
        }
    }

    private fun emailCheck(): Boolean {
        val email = binding.emailEt.text.toString()
        return if (email.length < 7 || !email.contains("@") || !(email.contains(".com") || email.contains(".ru"))) {
            binding.emailRegTextInputLayout.error = "Неправильный адрес"
            false
        } else {
            binding.emailRegTextInputLayout.error = null
            true
        }
    }

    private fun passwordMatch(): Boolean {
        with(binding) {
            val pwdFirst = pwdFirstEt.text.toString()
            val pwdSecond = pwdSecondEt.text.toString()
            return if (pwdFirst != pwdSecond) {
                pwdFirstTextInputLayout.error = "Пароли не совпадают"
                pwdSecondTextInputLayout.error = "Пароли не совпадают"
                false
            } else {
                pwdFirstTextInputLayout.error = null
                pwdSecondTextInputLayout.error = null
                true
            }
        }
    }

    private fun passwordCheck(): Boolean {
        with(binding) {
            return if (pwdFirstEt.text.toString().length < 6) {
                pwdFirstTextInputLayout.error = "Меньше 6 символов"
                false
            } else {
                pwdFirstTextInputLayout.error = null
                true
            }
        }
    }

    private fun agreeCheck(): Boolean {
        return binding.agreeSwtch.isChecked
    }
}
