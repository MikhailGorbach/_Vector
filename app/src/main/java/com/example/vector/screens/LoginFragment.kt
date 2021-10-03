package com.example.vector.screens

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.vector.MainActivity
import com.example.vector.R

class LoginFragment: Fragment(R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.enterBtn).setOnClickListener {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
    }
}