package com.example.sensoresguiadsm

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private lateinit var tvSensorData: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Vincula el TextView desde el XML
        tvSensorData = findViewById(R.id.tvSensorData)
        // Inicializa el SensorManager
        sensorManager = getSystemService(SENSOR_SERVICE) as
                SensorManager
// Obtén el sensor de acelerómetro
        accelerometer =
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        // Verifica si el sensor está disponible
        if (accelerometer == null) {
            tvSensorData.text = "El acelerómetro no está disponible en este dispositivo."
        }
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.also { sensor ->
            sensorManager.registerListener(this, sensor,
                SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            // Obtén los valores del acelerómetro
            val x = it.values[0]
            val y = it.values[1]
            val z = it.values[2]
            // Muestra los datos en el TextView
            tvSensorData.text = "Aceleración:\nX: $x\nY: $y\nZ: $z"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Puedes manejar cambios en la precisión si es necesario
    }


}
