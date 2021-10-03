package com.example.vector.screens

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vector.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterFragment: Fragment(R.layout.fragment_register)
{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            view.findViewById<Button>(R.id.btn_new_user).setOnClickListener {
                if (validationIsOk()) {
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
            }
    }
    private fun validationIsOk(): Boolean {
        return true
    }
}
