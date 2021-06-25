package com.qbo.appkea5googlemaps.commom

import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.qbo.appkea5googlemaps.R

object AppMensaje {

    fun enviarMensaje(vista: View, mensaje: String, tipo: TipoMensaje){
        val snackbar = Snackbar.make(vista, mensaje, Snackbar.LENGTH_LONG)
        val snackBarView = snackbar.view
        when (tipo) {
            TipoMensaje.ERROR -> {
                snackBarView.setBackgroundColor(ContextCompat.getColor
                    (MiApp.instancia, R.color.snackbarerror)
                )
            }
            TipoMensaje.EXITO -> {
                snackBarView.setBackgroundColor(ContextCompat.getColor(
                    MiApp.instancia, R.color.snackbarexito)
                )
            }
            else -> {
                snackBarView.setBackgroundColor(ContextCompat.getColor(
                    MiApp.instancia, R.color.snackbaradvert)
                )
            }
        }
        snackbar.show()
    }
}