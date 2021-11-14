package com.example.vector.ui.map.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vector.R
import com.example.vector.databinding.FragmentMarkBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogMarkFragment : DialogFragment() {

    private val args: DialogMarkFragmentArgs by navArgs()
    private val mDialogViewModel: DialogViewModel by viewModels()
    private lateinit var binding: FragmentMarkBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMarkBinding.inflate(inflater, container, false)

        binding.applyMarkerBtn.setOnClickListener {
            if (inputCheck()) {
                val title = binding.titleTextInputEditText.text.toString().trim()
                val description = binding.descriptionTextInputEditText.text.toString().trim()
                mDialogViewModel.addMark(title, description, args.longitude, args.latitude)
                findNavController().navigate(R.id.action_dialogMarkFragment_to_mapFragment)
                dismiss()
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