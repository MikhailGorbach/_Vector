package com.example.vector.screens

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vector.R
import com.google.android.material.textfield.TextInputEditText

class RegisterFragment: Fragment(R.layout.fragment_register)
{
    val pwd = view?.findViewById<TextInputEditText>(R.id.reg_tiet_password)
    val pwd_again = view?.findViewById<TextInputEditText>(R.id.reg_tiet_pass_again)
    val switch = view?.findViewById<SwitchCompat>(R.id.reg_sw_agree)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            view.findViewById<Button>(R.id.reg_bt_enter).setOnClickListener {
                if (validationIsOk()) {
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
            }
    }
    private fun validationIsOk(): Boolean {
        return true
    }
}