package com.example.vector.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vector.databinding.BottomsheetFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetFragmentBinding
    private lateinit var mMapViewModel: MapViewModel
    private val args: BottomSheetFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomsheetFragmentBinding.inflate(inflater, container, false)
        mMapViewModel = ViewModelProvider(this)[MapViewModel::class.java]

        binding.addMarkerBtn.setOnClickListener {
            val action = BottomSheetFragmentDirections.actionBottomSheetFragmentToDialogMarkFragment(args.longitude, args.latitude)
            findNavController().navigate(action)
        }
        return binding.root
    }
}