package com.example.vector

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.vector.screens.LoginFragment
import com.example.vector.screens.RegisterFragment

class EnterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter)
        val btn_reg = findViewById<Button>(R.id.reg_btn_register)
        btn_reg.setOnClickListener {
            intent = Intent(this, RegisterFragment::class.java)
            startActivity(intent)
        }
        val btn_log = findViewById<Button>(R.id.reg_btn_enter)
        btn_log.setOnClickListener {
            intent = Intent(this, LoginFragment::class.java)
            startActivity(intent)
        }
    }
}