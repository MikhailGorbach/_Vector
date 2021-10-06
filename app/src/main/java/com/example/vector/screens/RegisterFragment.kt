package com.example.vector.screens

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vector.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterFragment: Fragment(R.layout.fragment_register)
{
    var login = view?.findViewById<TextInputEditText>(R.id.loginEt)
    var pwd = view?.findViewById<TextInputEditText>(R.id.pwdSecondEt)
    var email = view?.findViewById<TextInputEditText>(R.id.emailEt)
    var pwdAgain = view?.findViewById<TextInputEditText>(R.id.pwdSecondEt)

    var loginTl = view?.findViewById<TextInputLayout>(R.id.loginTl)
    var emailTl = view?.findViewById<TextInputLayout>(R.id.emailTl)
    var pwdTl = view?.findViewById<TextInputLayout>(R.id.pwdFirstTl)
    var pwdAgainTl = view?.findViewById<TextInputLayout>(R.id.pwdSecondTl)

    lateinit var binding : RegisterFragment
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            view.findViewById<Button>(R.id.registrationBtn).setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
    }
    inner class TextValidation(private val view: View) : TextWatcher {

        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
        private fun validateUser(): Boolean {
            if (binding.login?.text.toString().trim().isEmpty()) {
                binding.loginTl?.error = "Обязательное поле"
                binding.login?.requestFocus()
                return false
            }
            binding.loginTl?.isErrorEnabled = false
            return true
        }
        private fun validateEmail(): Boolean {
            if (binding.email?.text.toString().trim().isEmpty()) {
                binding.emailTl?.error = "Обязательное поле"
                binding.email?.requestFocus()
                return false
            }
            //else if (!isValid())
            return true
        }
        private fun isValid(): Boolean {
            if (validateEmail() && validateUser())
            return true
            return false
        }
    }
}

