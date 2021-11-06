package com.example.vector.ui.map

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.vector.R
import com.example.vector.databinding.FragmentMapBinding
import com.example.vector.domain.local.entity.MarkDto
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapBinding
    private lateinit var mMap: GoogleMap
    private lateinit var mMapViewModel: MapViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)

        mMapViewModel = ViewModelProvider(this)[MapViewModel::class.java]
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

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        map.uiSettings.isZoomControlsEnabled = true
        setUpPermission()
        mMap.setOnMapLongClickListener { latlng ->
            val placeFromView = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_mark, null)
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Создать маркер")
                .setPositiveButton("Создать", null)
                .setNegativeButton("Отмена", null)
                .setView(placeFromView)
                .show()
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                val title = placeFromView.findViewById<TextInputEditText>(R.id.titleTextInputEditText).text.toString().trim()
                val description = placeFromView.findViewById<TextInputEditText>(R.id.descriptionTextInputEditText).text.toString().trim()
                val titleTextInputLayout = placeFromView.findViewById<TextInputLayout>(R.id.titleTextInputLayout)
                val descriptionTextInputLayout = placeFromView.findViewById<TextInputLayout>(R.id.descriptionTextInputLayout)
                if (inputCheck(title, description, titleTextInputLayout, descriptionTextInputLayout)) {
                    mMap.addMarker(MarkerOptions().position(latlng).title(title).snippet(description))
                    mMapViewModel.addMark(title, description, latlng.longitude.toString().trim(), latlng.latitude.toString().trim())
                    dialog.dismiss()
                } else {
                    return@setOnClickListener
                }
            }
        }
        mMap.setOnInfoWindowClickListener { markerToDelete ->
            val latitude = markerToDelete.position.latitude.toString().trim()
            val longitude = markerToDelete.position.longitude.toString().trim()
            lifecycleScope.launch(Main) {
                val mark = findMarker(longitude, latitude)
                if (mark != null) {
                    mMapViewModel.deleteMark(mark)
                    markerToDelete.remove()
                }
            }
        }
    }

    private fun inputCheck(
        title: String,
        description: String,
        titleTextInputLayout: TextInputLayout,
        descriptionTextInputLayout: TextInputLayout
    ): Boolean {
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

    private suspend fun findMarker(longitude: String, latitude: String): MarkDto? =
        withContext(Dispatchers.IO) {
            return@withContext mMapViewModel.findMarker(longitude, latitude)
        }

    private fun setUpPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
    }
}