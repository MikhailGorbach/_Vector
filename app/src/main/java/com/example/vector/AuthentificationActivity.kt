package com.example.vector

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.findNavController
import com.example.vector.databinding.ActivityAuthentificationBinding
import com.example.vector.utils.setBtnTextColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthentificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthentificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthentificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.registrBtn.setOnClickListener {
            changeButtons(binding.registrBtn, binding.loginBtn, true)
            if (findNavController(R.id.fragmentContainerView).currentDestination?.id != R.id.registerFragment) {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
        binding.loginBtn.setOnClickListener {
            changeButtons(binding.loginBtn, binding.registrBtn, false)
            if (findNavController(R.id.fragmentContainerView).currentDestination?.id != R.id.loginFragment) {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }

    private fun changeButtons(notSelectedBtn: AppCompatButton, selectedBtn: AppCompatButton, valid: Boolean) {
        notSelectedBtn.isSelected = valid
        selectedBtn.isSelected = valid
        setBtnTextColor(notSelectedBtn, R.color.white)
        setBtnTextColor(selectedBtn, R.color.blue)
    }
}
