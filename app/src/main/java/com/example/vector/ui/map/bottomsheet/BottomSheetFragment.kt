package com.example.vector.ui.map.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vector.databinding.BottomsheetFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {

    private val args: BottomSheetFragmentArgs by navArgs()
    private lateinit var binding: BottomsheetFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomsheetFragmentBinding.inflate(inflater, container, false)

        binding.addMarkerImageBtn.setOnClickListener {
            val action = BottomSheetFragmentDirections.actionBottomSheetFragmentToDialogMarkFragment(args.longitude, args.latitude)
            findNavController().navigate(action)
        }
        binding.deleteMarkersImageBtn.setOnClickListener {
            val action = BottomSheetFragmentDirections.actionBottomSheetFragmentToMapFragment(permitionToDelete = true)
            findNavController().navigate(action)
        }
        binding.shareMarkerImageBtn.setOnClickListener {
            val shareBody = "Вот мои координаты: ${args.latitude},${args.longitude}"
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(shareIntent)
        }
        return binding.root
    }
}