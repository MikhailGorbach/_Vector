package com.example.vector.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vector.AuthentificationActivity
import com.example.vector.R
import com.example.vector.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.logOutBtn.setOnClickListener {
            signOut()
            startActivity(Intent(requireActivity(), AuthentificationActivity::class.java))
        }
        return binding.root
    }

    private fun signOut() {
        sharedPreferences = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("USER_DEFINED", false).apply()
    }
}