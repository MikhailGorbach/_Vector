package com.example.vector.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vector.R
import com.example.vector.databinding.FragmentWayBinding

class WayFragment : Fragment(R.layout.fragment_way) {

    private lateinit var binding: FragmentWayBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWayBinding.inflate(inflater, container, false)
        return binding.root
    }
}