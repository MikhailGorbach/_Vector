package com.example.vector

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

class EnterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter)
        findViewById<Button>(R.id.registrBtn).setOnClickListener {
            if (findNavController(R.id.fragmentContainerView).currentDestination?.id != R.id.registerFragment) {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
        findViewById<Button>(R.id.loginBtn).setOnClickListener {
            if (findNavController(R.id.fragmentContainerView).currentDestination?.id != R.id.loginFragment) {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }
}