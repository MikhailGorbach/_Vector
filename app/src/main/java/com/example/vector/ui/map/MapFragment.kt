package com.example.vector.ui.map

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
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
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapBinding
    private lateinit var mMap: GoogleMap
    private lateinit var mMapViewModel: MapViewModel
    private var markers = mutableListOf<MarkDto>()

    companion object {
        const val mapScale = 12f
        const val onlyOneAddress = 1
    }

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
        mMap.setOnMapLongClickListener { currentLatLng ->
            val viewFromFragmentMark = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_mark, null)
            val dialog = createAlertDialog(viewFromFragmentMark)
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                if (inputCheck(viewFromFragmentMark)) {
                    addMarker(currentLatLng, viewFromFragmentMark)
                    dialog.dismiss()
                } else {
                    return@setOnClickListener
                }
            }
        }
        mMap.setOnInfoWindowClickListener { markerToDelete ->
            val latitude = markerToDelete.position.latitude.toString()
            val longitude = markerToDelete.position.longitude.toString()
            lifecycleScope.launch(Main) {
                val mark = findMarker(longitude, latitude)
                markers.remove(mark)
                markerToDelete.remove()
                mMapViewModel.deleteMark(mark!!)
            }
        }
    }

    private fun addMarker(latLng: LatLng, viewFromFragmentMark: View) {
        val title = viewFromFragmentMark.findViewById<TextInputEditText>(R.id.titleTextInputEditText).text.toString().trim()
        val description = viewFromFragmentMark.findViewById<TextInputEditText>(R.id.descriptionTextInputEditText).text.toString().trim()
        mMap.addMarker(MarkerOptions().position(latLng).title(title).snippet(description))
        mMapViewModel.addMark(title, description, latLng.longitude.toString(), latLng.latitude.toString())
        lifecycleScope.launch(Main) {
            val mark = findMarker(latLng.longitude.toString(), latLng.latitude.toString())
            markers.add(mark!!)
        }
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
        return (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED)
    }
}