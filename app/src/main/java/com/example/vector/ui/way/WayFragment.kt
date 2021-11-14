package com.example.vector.ui.way

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vector.R
import com.example.vector.databinding.FragmentWayBinding
import com.example.vector.domain.local.entity.MarkDto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WayFragment : Fragment(R.layout.fragment_way) {

    private lateinit var binding: FragmentWayBinding
    private val mWayViewModel: WayViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWayBinding.inflate(inflater, container, false)
        val adapter = ListAdapter()
        val recycleView = binding.markRecyclerView
        recycleView.adapter = adapter
        adapter.setOnItemClickListener(object : ListAdapter.OnItemClickListener {
            override fun onItemClick(markDto: MarkDto) {
                val action = WayFragmentDirections.actionWayFragmentToMapFragment(markDto.longitude, markDto.latitude)
                findNavController().navigate(action)
            }
        })
        recycleView.layoutManager = LinearLayoutManager(requireContext())
        lifecycleScope.launchWhenStarted {
            mWayViewModel.readAllMarkers.observe(viewLifecycleOwner, Observer { mark ->
                adapter.setData(mark)
            })
        }
        return binding.root
    }
}