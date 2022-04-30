package com.example.myapplication.fragments.mainFragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMapBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions

class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var map: MapView
    private lateinit var binding: FragmentMapBinding
    private lateinit var gMap: GoogleMap
    private var points = ArrayList<LatLng>()
    private var polyline: Polyline? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater)
        map = binding.mapView
        map.onCreate(savedInstanceState)
        map.getMapAsync(this)
        initListeners()
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = MapFragment()
    }

    private fun initListeners() {
        binding.apply {
            clearLineButton.setOnClickListener {
                points.clear()
                polyline?.remove()
                gMap.addPolyline(PolylineOptions())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onStart() {
        super.onStart()
        map.onStart()
    }

    override fun onStop() {
        super.onStop()
        map.onStop()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        map.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map.onLowMemory()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return
        }
        googleMap.isMyLocationEnabled = true
        googleMap.setOnMapLongClickListener {
            points.add(it)
            polyline?.remove()
            polyline = googleMap.addPolyline(PolylineOptions().addAll(points))
            polyline!!.color = ContextCompat.getColor(requireContext(), R.color.color_highlight)
        }
        gMap = googleMap
    }
}