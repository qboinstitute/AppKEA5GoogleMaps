package com.qbo.appkea5googlemaps

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.qbo.appkea5googlemaps.commom.AppMensaje
import com.qbo.appkea5googlemaps.commom.Constantes
import com.qbo.appkea5googlemaps.commom.TipoMensaje
import com.qbo.appkea5googlemaps.databinding.ActivityPermisoBinding

class PermisoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPermisoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermisoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnirmaps.setOnClickListener {
            if(verificiarPermisoGPS()){
                startActivity(Intent(applicationContext,
                    MapsActivity::class.java))
            }else{
                solicitarPermisoGPS()
            }
        }

    }
    private fun verificiarPermisoGPS() : Boolean{
        val resultadoPermiso = ContextCompat.checkSelfPermission(
            applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        return resultadoPermiso == PackageManager.PERMISSION_GRANTED
    }
    private fun solicitarPermisoGPS(){
        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            Constantes.ID_REQUEST_PERMISSION_GPS)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == Constantes.ID_REQUEST_PERMISSION_GPS){
            if(grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startActivity(Intent(applicationContext,
                    MapsActivity::class.java))
            }else{
                AppMensaje.enviarMensaje(binding.root,
                    getString(R.string.errorpermisogps), TipoMensaje.ERROR)
            }
        }
    }
}