package com.example.sensoresubicaciondsm

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
class MainActivity : AppCompatActivity() {
    private lateinit var locationManager: LocationManager
    private lateinit var tvLocation: TextView
    private lateinit var btnGetLocation: Button
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Inicializar vistas
        tvLocation = findViewById(R.id.tvLocation)
        btnGetLocation = findViewById(R.id.btnGetLocation)
        // Inicializar LocationManager
        locationManager = getSystemService(LOCATION_SERVICE) as
                LocationManager
        // Configurar botón para obtener ubicación
        btnGetLocation.setOnClickListener {
            checkLocationPermissionAndGetLocation()
        }
    }
    // Verifica y solicita los permisos de ubicación
    private fun checkLocationPermissionAndGetLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
                // Solicita el permiso si no está concedido
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // Si el permiso ya está concedido, obtiene la ubicación
            getLocation()
        }
    }
    // Maneja la respuesta del usuario a la solicitud de permisos
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions,
            grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED) {
                getLocation()
            } else {
                tvLocation.text = "Permiso de ubicación denegado."
            }
        }
    }
    // Obtiene la ubicación del dispositivo
    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000L, // Actualización cada segundo
                1f // Cambios de al menos 1 metro
            ) { location: Location ->
                updateLocationUI(location)
            }
        }
    }
    // Actualiza la interfaz con la ubicación
    private fun updateLocationUI(location: Location) {
        val latitude = location.latitude
        val longitude = location.longitude
        tvLocation.text = "Latitud: $latitude\nLongitud: $longitude"
    }
}