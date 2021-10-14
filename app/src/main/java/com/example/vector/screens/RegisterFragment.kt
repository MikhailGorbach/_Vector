package com.example.vector.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vector.R
import com.example.vector.access.UserValid
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
        val userVal = UserValid(requireActivity())
        if (userVal.inputCheck()) {
            val user = User(
                0,
                binding.loginEt.text.toString(),
                binding.emailEt.text.toString(),
                binding.pwdFirstEt.text.toString()
            )
            mUserViewModel.addUser(user)
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}

