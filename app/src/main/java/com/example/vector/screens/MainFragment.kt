package com.example.vector.screens

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.vector.EnterActivity
import com.example.vector.R

class MainFragment : Fragment(R.layout.fragment_main) {
    lateinit var sharedPreferences: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences =
            requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val info = sharedPreferences.getString("USER_LOGIN_PASSWORD", null)
        view.findViewById<TextView>(R.id.loginTv).text = info
        view.findViewById<AppCompatButton>(R.id.logOutBtn).setOnClickListener {
            val isLogined = false
            val editor = sharedPreferences.edit()
            editor.apply {
                putBoolean("USER_DEFINED", isLogined)
            }.apply()
            startActivity(Intent(requireActivity(), EnterActivity::class.java))
        }
    }
}