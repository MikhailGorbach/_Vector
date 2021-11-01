package com.example.vector.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.vector.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapBinding
    private lateinit var mMap: GoogleMap
    private var markers: MutableList<Marker> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.onResume()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                val location = binding.searchView.query.toString()
                if (location != "") {
                    val geocoder = Geocoder(requireContext())
                    try {
                        val addressList: List<Address> = geocoder.getFromLocationName(location, 1)
                        if (addressList.isNotEmpty()) {
                            val address = addressList[0]
                            val latLng = LatLng(address.latitude, address.longitude)
                            placeMarker(latLng)
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
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

    override fun onMapReady(map: GoogleMap?) {
        if (map != null) {
            mMap = map
        }
        map?.uiSettings?.isZoomControlsEnabled = true
        setUpMap()
        mMap.setOnMapLongClickListener {
            latlng -> val marker = mMap.addMarker(MarkerOptions().position(latlng).title("$latlng"))
            if (marker != null) {
                markers.add(marker)
            }
        }
        mMap.setOnInfoWindowClickListener {
            markerToDelete -> markers.remove(markerToDelete)
            markerToDelete.remove()
        }
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
    }

    private fun placeMarker(currentLatLong: LatLng) {
        val markerOptions = MarkerOptions().position(currentLatLong)
        markerOptions.title("$currentLatLong")
        val marker = mMap.addMarker(markerOptions)
        if (marker != null) {
            markers.add(marker)
        }
    }
}