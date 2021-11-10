package com.example.vector.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vector.databinding.FragmentMapBinding
import com.example.vector.domain.local.entity.MarkDto
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val mapScale = 12f
const val onlyOneAddress = 1

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapBinding
    private lateinit var mMap: GoogleMap
    private lateinit var mMapViewModel: MapViewModel
    private var markers = mutableListOf<MarkDto>()
    private val args: MapFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        mMapViewModel = ViewModelProvider(this)[MapViewModel::class.java]
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.onResume()
        lifecycleScope.launchWhenStarted {
            mMapViewModel.readAllMarkers.observe(viewLifecycleOwner, Observer { ListMarks ->
                markers = ListMarks.toMutableList()
            })
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                val location = binding.searchView.query.toString()
                if (location.isNotBlank()) {
                    try {
                        val address = Geocoder(requireContext()).getFromLocationName(location, onlyOneAddress)[0]
                        if (address != null) {
                            val latLng = LatLng(address.latitude, address.longitude)
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, mapScale))
                        }
                    } catch (e: Exception) {
                        println(e)
                    }
                }
                return false
            }
        })
        binding.mapView.getMapAsync(this)
        return binding.root
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        map.uiSettings.isZoomControlsEnabled = true
        setUpPermission()
        lifecycleScope.launch(Main) {
            markers.forEach {
                val latLng = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                mMap.addMarker(MarkerOptions().position(latLng).title(it.title).snippet(it.description))
            }
        }
        val latitude = args.latitude
        val longitude = args.longitude
        if (latitude != "defValue" && longitude != "defValue") {
            val latLngFromWay = LatLng(latitude.toDouble(), longitude.toDouble())
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngFromWay, mapScale))
        }
        mMap.setOnMapLongClickListener { currentLatLng ->
            val longitudeForBottomSheet = currentLatLng.longitude.toString()
            val latitudeForBottomSheet = currentLatLng.latitude.toString()
            val action = MapFragmentDirections.actionMapFragmentToBottomSheetFragment(longitudeForBottomSheet, latitudeForBottomSheet)
            findNavController().navigate(action)
        }
        mMap.setOnInfoWindowClickListener { markerToDelete ->
            val latitudeToDelete = markerToDelete.position.latitude.toString()
            val longitudeToDelete = markerToDelete.position.longitude.toString()
            lifecycleScope.launch(Main) {
                val mark = findMarker(longitudeToDelete, latitudeToDelete)
                markers.remove(mark)
                markerToDelete.remove()
                mMapViewModel.deleteMark(mark!!)
            }
        }
    }

    private suspend fun findMarker(longitude: String, latitude: String): MarkDto? =
        withContext(IO) {
            return@withContext mMapViewModel.findMarker(longitude, latitude)
        }

    private fun setUpPermission() {
        if (permissionNotGranted()) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
    }

    private fun permissionNotGranted(): Boolean {
        return (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
    }
}