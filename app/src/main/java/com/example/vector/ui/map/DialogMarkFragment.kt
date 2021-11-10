package com.example.vector.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vector.R
import com.example.vector.databinding.FragmentMarkBinding

class DialogMarkFragment : DialogFragment() {

    private lateinit var binding: FragmentMarkBinding
    private val args: DialogMarkFragmentArgs by navArgs()
    private lateinit var mMapViewModel: MapViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMarkBinding.inflate(inflater, container, false)
        mMapViewModel = ViewModelProvider(this)[MapViewModel::class.java]

        binding.applyMarkerBtn.setOnClickListener {
            if (inputCheck()) {
                val title = binding.titleTextInputEditText.text.toString().trim()
                val description = binding.descriptionTextInputEditText.text.toString().trim()
                mMapViewModel.addMark(title, description, args.longitude!!, args.latitude!!)
                findNavController().navigate(R.id.action_dialogMarkFragment_to_mapFragment)
                dismiss()
            } else {
                return@setOnClickListener
            }
        }
        binding.dismissDialogBtn.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    private fun inputCheck(): Boolean {
        with(binding) {
            val title = titleTextInputEditText.text.toString().trim()
            val description = descriptionTextInputEditText.text.toString().trim()
            return when {
                title.isEmpty() -> {
                    descriptionTextInputLayout.error = null
                    titleTextInputLayout.error = "Обязательное поле для заполнения"
                    false
                }
                description.isEmpty() -> {
                    descriptionTextInputLayout.error = "Обязательное поле для заполнения"
                    titleTextInputLayout.error = null
                    false
                }
                else -> {
                    descriptionTextInputLayout.error = null
                    titleTextInputLayout.error = null
                    true
                }
            }
        }
    }
}