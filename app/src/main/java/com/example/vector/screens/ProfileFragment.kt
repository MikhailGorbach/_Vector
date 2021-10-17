package com.example.vector.screens

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vector.EnterActivity
import com.example.vector.R
import com.example.vector.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.logOutBtn.setOnClickListener {
            signOut()
            startActivity(Intent(requireActivity(), EnterActivity::class.java))
        }
        return binding.root
    }

    private fun signOut() {
        sharedPreferences =
            requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("USER_DEFINED", false).apply()
    }
}