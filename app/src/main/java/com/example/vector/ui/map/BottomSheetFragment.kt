package com.example.vector.ui.map

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vector.R
import com.example.vector.databinding.BottomsheetFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetFragmentBinding
    private lateinit var mMapViewModel: MapViewModel
    private val args: BottomSheetFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomsheetFragmentBinding.inflate(inflater, container, false)
        mMapViewModel = ViewModelProvider(this)[MapViewModel::class.java]

        binding.addMarkerBtn.setOnClickListener {
            val viewFromFragmentMark = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_mark, null)
            val alertDialog = createAlertDialog(viewFromFragmentMark)
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                if (inputCheck(viewFromFragmentMark)) {
                    val title = viewFromFragmentMark.findViewById<TextInputEditText>(R.id.titleTextInputEditText).text.toString().trim()
                    val description = viewFromFragmentMark.findViewById<TextInputEditText>(R.id.descriptionTextInputEditText).text.toString().trim()
                    mMapViewModel.addMark(title, description, args.longitude!!, args.latitude!!)
                    alertDialog.dismiss()
                    findNavController().navigate(R.id.action_bottomSheetFragment_to_mapFragment)
                } else {
                    return@setOnClickListener
                }
            }
        }
        return binding.root
    }

    private fun createAlertDialog(viewFromFragmentMark: View): AlertDialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Создать маркер")
            .setPositiveButton("Создать", null)
            .setNegativeButton("Отмена", null)
            .setView(viewFromFragmentMark)
            .show()
    }

    private fun inputCheck(viewFromFragmentMark: View): Boolean {
        val title = viewFromFragmentMark.findViewById<TextInputEditText>(R.id.titleTextInputEditText).text.toString().trim()
        val description = viewFromFragmentMark.findViewById<TextInputEditText>(R.id.descriptionTextInputEditText).text.toString().trim()
        val titleTextInputLayout = viewFromFragmentMark.findViewById<TextInputLayout>(R.id.titleTextInputLayout)
        val descriptionTextInputLayout = viewFromFragmentMark.findViewById<TextInputLayout>(R.id.descriptionTextInputLayout)
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