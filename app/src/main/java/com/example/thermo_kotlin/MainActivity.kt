package com.example.thermo_kotlin

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var manager: SensorManager
    private var tempSensor: Sensor? = null
    private lateinit var thermometerView: Thermometre

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        thermometerView = Thermometre(this)
        setContentView(thermometerView)

        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        tempSensor = manager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

        manager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        thermometerView.updateTemperature(sensorEvent.values[0])
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}
