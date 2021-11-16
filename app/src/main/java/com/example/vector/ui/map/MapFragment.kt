package com.example.vector.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vector.R
import com.example.vector.databinding.FragmentMapBinding
import com.example.vector.domain.local.entity.MarkDto
import com.example.vector.ui.map.utils.setClickable
import com.example.vector.ui.map.utils.setVisibility
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

const val mapScale = 12f
const val onlyOneAddress = 1

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.routate_open_fab_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.routate_close_fab_anim) }
    private val fromTop: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.from_top_of_fab_anim) }
    private val toTop: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.to_top_of_fab_anim) }
    private val args: MapFragmentArgs by navArgs()
    private val mMapViewModel: MapViewModel by viewModels()
    private var clicked = false
    private var markers = mutableListOf<MarkDto>()
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: FragmentMapBinding
    private lateinit var mMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.onResume()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
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
        binding.getUserLocationFloatingActionBtn.setOnClickListener {
            setUpPermission()
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        lastLocation = location
                        val currentLatLng = LatLng(location.latitude, location.longitude)
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                        mMap.addMarker(MarkerOptions().position(currentLatLng).title("Вы здесь"))
                    }
                }
            } catch (e: SecurityException) {
                println(e)
            }
        }
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
        if (args.permitionToDelete) {
            lifecycleScope.launch(Main) {
                markers.forEach {
                    val marker = it
                    mMapViewModel.deleteMark(marker)
                }
                markers.clear()
                mMap.clear()
            }
        }
        binding.layersFloatingActionBtn.setOnClickListener {
            onAddButtonClicked()
        }
        binding.normalFloatingActionBtn.setOnClickListener {
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            onAddButtonClicked()
        }
        binding.terrainFloatingActionBtn.setOnClickListener {
            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            onAddButtonClicked()
        }
        binding.satelliteFloatingActionBtn.setOnClickListener {
            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            onAddButtonClicked()
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
                if (mark != null) {
                    mMapViewModel.deleteMark(mark)
                }
            }
        }
    }

    private fun onAddButtonClicked() {
        with(binding) {
            requireActivity().setClickable(clicked, normalFloatingActionBtn, terrainFloatingActionBtn, satelliteFloatingActionBtn)
            requireActivity().setVisibility(clicked, normalFloatingActionBtn, terrainFloatingActionBtn, satelliteFloatingActionBtn)
            setAnimation(clicked)
            clicked = !clicked
        }
    }

    private fun setAnimation(clicked: Boolean) {
        with(binding) {
            if (!clicked) {
                normalFloatingActionBtn.startAnimation(fromTop)
                terrainFloatingActionBtn.startAnimation(fromTop)
                satelliteFloatingActionBtn.startAnimation(fromTop)
                layersFloatingActionBtn.startAnimation(rotateOpen)
            } else {
                normalFloatingActionBtn.startAnimation(toTop)
                terrainFloatingActionBtn.startAnimation(toTop)
                satelliteFloatingActionBtn.startAnimation(toTop)
                layersFloatingActionBtn.startAnimation(rotateClose)
            }
        }
    }

    private suspend fun findMarker(longitude: String, latitude: String): MarkDto? {
        return mMapViewModel.findMarker(longitude, latitude)
    }

    private fun setUpPermission() {
        if (permissionNotGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                1
            )
        }
    }

    private fun permissionNotGranted(): Boolean {
        return (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
    }
}
