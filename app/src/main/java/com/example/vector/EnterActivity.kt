package com.example.vector

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.vector.databinding.ActivityEnterBinding

class EnterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnterBinding.inflate(layoutInflater)
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

    private fun changeButtons(
        notSelectedBtn: AppCompatButton,
        selectedBtn: AppCompatButton,
        valid: Boolean
    ) {
        notSelectedBtn.isSelected = valid
        selectedBtn.isSelected = valid
        notSelectedBtn.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
        selectedBtn.setTextColor(ContextCompat.getColor(applicationContext, R.color.blue))
    }
}