package com.example.vector.ui.way

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vector.R
import com.example.vector.databinding.FragmentWayBinding

class WayFragment : Fragment(R.layout.fragment_way) {

    private lateinit var binding: FragmentWayBinding
    private lateinit var mWayViewModel: WayViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWayBinding.inflate(inflater, container, false)
        mWayViewModel = ViewModelProvider(this)[WayViewModel::class.java]
        val adapter = ListAdapter()
        val recycleView = binding.markRecyclerView
        recycleView.adapter = adapter
        recycleView.layoutManager = LinearLayoutManager(requireContext())
        lifecycleScope.launchWhenStarted {
            mWayViewModel.readAllMarkers.observe(viewLifecycleOwner, Observer { mark ->
                adapter.setData(mark)
            })
        }
        return binding.root
    }
}