package com.example.vector.screens

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SwitchCompat
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

    var enter = view?.findViewById<AppCompatButton>(R.id.registrationBtn)
    var agree = view?.findViewById<SwitchCompat>(R.id.agreeSwtch)

    lateinit var binding : RegisterFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Toast.makeText(activity, "before", Toast.LENGTH_SHORT).show()
            }

            override fun afterTextChanged(p0: Editable?) {
                Toast.makeText(activity, "after", Toast.LENGTH_SHORT).show()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Toast.makeText(activity, "onText", Toast.LENGTH_SHORT).show()
            }
        })
        view.findViewById<Button>(R.id.registrationBtn).setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}

