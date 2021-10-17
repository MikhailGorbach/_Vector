package com.example.vector.screens

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.vector.EnterActivity
import com.example.vector.R

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<AppCompatButton>(R.id.logOutBtn).setOnClickListener {
            signOut()
            startActivity(Intent(requireActivity(), EnterActivity::class.java))
        }
    }

    private fun signOut() {
        sharedPreferences =
            requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("USER_DEFINED", false).apply()
    }
}