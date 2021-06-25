package com.qbo.appkea5googlemaps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.qbo.appkea5googlemaps.commom.AppMensaje
import com.qbo.appkea5googlemaps.commom.TipoMensaje
import com.qbo.appkea5googlemaps.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
        GoogleMap.OnMapClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val puntoReferencia = LatLng(-12.074566, -77.036039)
        mMap.addMarker(
            MarkerOptions()
            .position(puntoReferencia)
            .title("Punto de Referencia")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.carrofamiliar))
            .draggable(true)
            .snippet("Ahora mismo me encuentro aquí"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(puntoReferencia,
            16.0F))
        mMap.isTrafficEnabled = true
        //mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        try{
            mMap.isMyLocationEnabled = true
        }catch (ex: SecurityException){
            AppMensaje.enviarMensaje(binding.root,
            getString(R.string.errorgps), TipoMensaje.ERROR)
        }

    }

    override fun onMapClick(p0: LatLng) {
        mMap.addMarker(
            MarkerOptions()
                .position(p0)
                .title("Nuevo marcador")
        )
        mMap.animateCamera(CameraUpdateFactory.newLatLng(p0))
    }
}