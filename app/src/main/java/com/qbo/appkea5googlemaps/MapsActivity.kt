package com.qbo.appkea5googlemaps

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.PolyUtil
import com.qbo.appkea5googlemaps.commom.AppMensaje
import com.qbo.appkea5googlemaps.commom.TipoMensaje
import com.qbo.appkea5googlemaps.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
        GoogleMap.OnMapClickListener, GoogleMap.OnMarkerDragListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var lstLatLong = ArrayList<LatLng>()

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
        mMap.setOnMapClickListener(this)
        mMap.setOnMarkerDragListener(this)
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
        //mMap.isTrafficEnabled = true
        //mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        try{
            mMap.isMyLocationEnabled = true
        }catch (ex: SecurityException){
            AppMensaje.enviarMensaje(binding.root,
            getString(R.string.errorgps), TipoMensaje.ERROR)
        }
        agregarPoligono()
    }

    private fun agregarPoligono(){
        val poligonoMap = mMap.addPolygon(
            PolygonOptions()
                .add(
                    LatLng(-12.092776,-76.995812),
                    LatLng(-12.089579,-77.001294),
                    LatLng(-12.088362,-77.003419),
                    LatLng(-12.093985,-77.002711)
                )
        )
        poligonoMap.tag = "QBO"
        var puntoValidar = PolyUtil.containsLocation(
            LatLng(-12.074566, -77.036039),
            poligonoMap.points, false
        )
        if(puntoValidar){
            AppMensaje.enviarMensaje(binding.root,
            getString(R.string.msjpuntodentro),
            TipoMensaje.EXITO)
        }else{
            AppMensaje.enviarMensaje(binding.root,
                getString(R.string.msjpuntoafuera),
                TipoMensaje.ERROR)
        }

    }


    override fun onMapClick(p0: LatLng) {
        mMap.addMarker(
            MarkerOptions()
                .position(p0)
                .title("Nuevo marcador")
        )
        mMap.animateCamera(CameraUpdateFactory.newLatLng(p0))
        lstLatLong.add(p0)
        val polyLinea = PolylineOptions()
        polyLinea.color(Color.RED)
        polyLinea.width(6F)
        polyLinea.addAll(lstLatLong)
        mMap.addPolyline(polyLinea)
    }

    override fun onMarkerDragStart(p0: Marker) {
        p0.showInfoWindow()
    }

    override fun onMarkerDrag(p0: Marker) {
        var posicion = p0.position
        p0.snippet = posicion.latitude.toString()+" - "+
                posicion.longitude.toString()
        p0.showInfoWindow()
        mMap.animateCamera(CameraUpdateFactory.newLatLng(posicion))
    }

    override fun onMarkerDragEnd(p0: Marker) {
        p0.title = "Nueva posición"
        p0.showInfoWindow()
        mMap.animateCamera(CameraUpdateFactory.newLatLng(p0.position))
    }
}