package com.example.vector.ui.profile

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.vector.AuthentificationActivity
import com.example.vector.R
import com.example.vector.databinding.FragmentProfileBinding
import kotlinx.android.synthetic.main.fragment_profile.welcomeTextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var mProfileViewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        mProfileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        lifecycleScope.launch(Main) {
            binding.welcomeTextView.text = userInformation()
        }
        binding.logOutBtn.setOnClickListener {
            signOut()
            startActivity(Intent(requireActivity(), AuthentificationActivity::class.java))
        }
        binding.copyBtn.setOnClickListener {
            val clipboard = requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("TextView", welcomeTextView.text.toString())
            clipboard.setPrimaryClip(clip)
        }
        return binding.root
    }

    private suspend fun userInformation(): String =
        withContext(Dispatchers.IO) {
            sharedPreferences = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
            val userLogin = sharedPreferences.getString("USER_LOGIN", null)
            val user = mProfileViewModel.findUser(userLogin)
            return@withContext "Добро пожаловать, ${user?.login}!\nПочта: ${user?.email}\nПароль: ${user?.password}"
        }

    private fun signOut() {
        sharedPreferences = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("USER_DEFINED", false).apply()
    }
}
