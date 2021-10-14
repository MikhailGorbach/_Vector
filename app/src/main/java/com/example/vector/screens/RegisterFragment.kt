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
import kotlinx.android.synthetic.main.fragment_register.pwdFirstEt
import kotlinx.android.synthetic.main.fragment_register.pwdSecondEt

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
            val user = User(id,
                binding.loginEt.text.toString(),
                binding.emailEt.text.toString(),
                binding.pwdFirstEt.text.toString()
            )
            mUserViewModel.addUser(user)
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun inputCheck(): Boolean {
        return (loginCheck() && emailCheck() && passwordCheck() && passwordMatch() && binding.agreeSwtch.isChecked)
    }

    private fun passwordMatch(): Boolean {
        if (pwdFirstEt.text.toString() != pwdSecondEt.text.toString()) {
            binding.pwdFirstTl.error = "Пароли не совпадают"
            binding.pwdSecondTl.error = "Пароли не совпадают"
            return false
        }
        binding.pwdFirstTl.error = null
        binding.pwdSecondTl.error = null
        return true
    }

    private fun loginCheck(): Boolean {
        val login = binding.loginEt.text.toString()
        binding.pwdFirstTl.onFocusChangeListener
        if (login.length < 5) {
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

    private fun passwordCheck(): Boolean {
        val password = binding.pwdFirstEt.text.toString()
        if (password.length < 6) {
            binding.pwdFirstTl.error = "Меньше 6 символов"
            return false
        }
        binding.pwdFirstTl.error = null
        return true
    }
}

