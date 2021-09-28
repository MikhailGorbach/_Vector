package com.example.vector.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vector.R

class SplachFragment: Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.postDelayed({
            findNavController().navigate(R.id.action_splachFragment_to_loginFragment)
        }, 3000)
    }

}