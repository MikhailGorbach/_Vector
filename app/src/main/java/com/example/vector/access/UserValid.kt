package com.example.vector.access

import android.app.Activity
import android.widget.Switch
import com.example.vector.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class UserValid(_activity: Activity) {
    private val act = _activity

    fun inputCheck(): Boolean {
        return (loginCheck() && emailCheck() && passwordCheck() && passwordMatch() && agreeCheck())
    }

    private fun loginCheck(): Boolean {
        if (act.findViewById<TextInputEditText>(R.id.loginEt).text.toString().length < 5) {
            act.findViewById<TextInputLayout>(R.id.loginTl).error = "Меньше 5 символов"
            return false
        }
        act.findViewById<TextInputLayout>(R.id.loginTl).error = null
        return true
    }

    private fun emailCheck(): Boolean {
        val email = act.findViewById<TextInputEditText>(R.id.emailEt).text.toString()
        if (email.length < 7 || !email.contains("@") || !(email.contains(".com") || email.contains(".ru"))) {
            act.findViewById<TextInputLayout>(R.id.emailTl).error = "Неправильный адрес"
            return false
        }
        act.findViewById<TextInputLayout>(R.id.emailTl).error = null
        return true
    }

    private fun passwordMatch(): Boolean {
        val pwdFirst = act.findViewById<TextInputEditText>(R.id.pwdFirstEt).text.toString()
        val pwdSecond = act.findViewById<TextInputEditText>(R.id.pwdSecondEt).text.toString()
        if (pwdFirst != pwdSecond) {
            act.findViewById<TextInputLayout>(R.id.pwdFirstTl).error = "Пароли не совпадают"
            act.findViewById<TextInputLayout>(R.id.pwdSecondTl).error = "Пароли не совпадают"
            return false
        }
        act.findViewById<TextInputLayout>(R.id.pwdFirstTl).error = null
        act.findViewById<TextInputLayout>(R.id.pwdSecondTl).error = null
        return true
    }

    private fun passwordCheck(): Boolean {
        if (act.findViewById<TextInputEditText>(R.id.pwdFirstEt).text.toString().length < 6) {
            act.findViewById<TextInputLayout>(R.id.pwdFirstTl).error = "Меньше 6 символов"
            return false
        }
        act.findViewById<TextInputLayout>(R.id.pwdFirstTl).error = null
        return true
    }

    private fun agreeCheck(): Boolean {
        return act.findViewById<Switch>(R.id.agreeSwtch).isChecked
    }
}