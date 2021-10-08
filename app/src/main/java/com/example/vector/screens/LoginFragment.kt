package com.example.vector.screens

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.example.vector.MainActivity
import com.example.vector.R
import com.google.android.material.textfield.TextInputEditText

class LoginFragment: Fragment(R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.enterBtn).setOnClickListener {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
    }
}