package com.example.vector.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vector.R
import com.example.vector.data.User
import com.example.vector.data.UserViewModel
import com.example.vector.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        binding.registrationBtn.setOnClickListener {
            insertDataToDatabase()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun insertDataToDatabase() {
        if (inputCheck()) {
            val user = User(login =
                binding.loginEt.text.toString().trim(),
                email =binding.emailEt.text.toString().trim(),
                password = binding.pwdFirstEt.text.toString().trim()
            )
            mUserViewModel.addUser(user)
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun inputCheck(): Boolean {
        return (loginCheck() && emailCheck() && passwordCheck() && passwordMatch() && agreeCheck())
    }

    private fun loginCheck(): Boolean {
        if (binding.loginEt.text.toString().length < 5) {
            binding.loginTl.error = "Меньше 5 символов"
            return false
        }
        binding.loginTl.error = null
        return true
    }

    private fun emailCheck(): Boolean {
        val email = binding.emailEt.text.toString()
        if (email.length < 7 || !email.contains("@") || !(email.contains(".com") || email.contains(".ru"))) {
            binding.emailTl.error = "Неправильный адрес"
            return false
        }
        binding.emailTl.error = null
        return true
    }

    private fun passwordMatch(): Boolean {
        val pwdFirst = binding.pwdFirstEt.text.toString()
        val pwdSecond = binding.pwdSecondEt.text.toString()
        if (pwdFirst != pwdSecond) {
            binding.pwdFirstTl.error = "Пароли не совпадают"
            binding.pwdSecondTl.error = "Пароли не совпадают"
            return false
        }
        binding.pwdFirstTl.error = null
        binding.pwdSecondTl.error = null
        return true
    }

    private fun passwordCheck(): Boolean {
        if (binding.pwdFirstEt.text.toString().length < 6) {
            binding.pwdFirstTl.error = "Меньше 6 символов"
            return false
        }
        binding.pwdFirstTl.error = null
        return true
    }

    private fun agreeCheck(): Boolean {
        return binding.agreeSwtch.isChecked
    }
}