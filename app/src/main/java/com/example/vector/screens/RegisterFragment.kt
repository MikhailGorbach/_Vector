package com.example.vector.screens

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vector.R

class RegisterFragment: Fragment(R.layout.fragment_register)
{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            /*view.findViewById<Button>(R.id.reg_rb_log).setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }*/
    }
}