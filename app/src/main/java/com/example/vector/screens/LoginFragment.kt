package com.example.vector.screens

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.vector.MainActivity
import com.example.vector.R
import com.example.vector.access.UserSession
import com.example.vector.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.fragment_login.loginEdt
import kotlinx.android.synthetic.main.fragment_login.pwdEdt
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userSession = UserSession(requireContext(), this)
        if (userSession.isSignIn()) {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        } else {
            binding.enterBtn.setOnClickListener {
                lifecycleScope.launch(Main) {
                    if (userSession.userDefined(loginEdt.text.toString().trim(), pwdEdt.text.toString().trim())) {
                        userSession.saveSession()
                        startActivity(Intent(requireActivity(), MainActivity::class.java))
                    } else {
                        binding.loginTil.error =
                            "Неправильный логин или пароль"
                    }
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}