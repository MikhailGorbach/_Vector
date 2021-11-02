package com.example.vector.ui.mark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vector.R
import com.example.vector.databinding.FragmentMarkBinding

class MarkFragment : Fragment(R.layout.fragment_mark) {

    private lateinit var binding: FragmentMarkBinding
    private lateinit var mMarkViewModel: MarkViewModel
    private val args: MarkFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMarkBinding.inflate(inflater, container, false)
        mMarkViewModel = ViewModelProvider(this)[MarkViewModel::class.java]
        binding.addMarkerBtn.setOnClickListener {
            if (insertDataToDatabase()) {
                if (findNavController().currentDestination?.id != R.id.mapFragment) {
                    findNavController().navigate(R.id.action_markFragment_to_mapFragment)
                }
            }
        }
        return binding.root
    }

    private fun insertDataToDatabase(): Boolean {
        if (inputCheck()) {
            with(binding) {
                val latitude = args.latitude
                val longitude = args.longitude
                mMarkViewModel.addMark(
                    titleTextInputEditText.text.toString().trim(),
                    descriptionTextInputEditText.text.toString().trim(),
                    longitude, latitude
                )
                return true
            }
        }
        return false
    }

    private fun inputCheck(): Boolean {
        return (titleCheck() && descriptionCheck())
    }

    private fun titleCheck(): Boolean {
        with(binding) {
            return if (titleTextInputEditText.text.toString().length < 3) {
                titleTextInputLayout.error = "Меньше 3 символов"
                false
            } else {
                titleTextInputLayout.error = null
                true
            }
        }
    }

    private fun descriptionCheck(): Boolean {
        with(binding) {
            return if (descriptionTextInputEditText.text.toString().length < 5) {
                descriptionTextInputLayout.error = "Меньше 5 символов"
                false
            } else {
                titleTextInputLayout.error = null
                true
            }
        }
    }
}