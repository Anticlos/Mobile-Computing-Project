package com.ucph.mc.dui.data.sensors.orientation

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

class Orientation private constructor(context: Context): SensorEventListener{
    private val TAG = Orientation::class.java.simpleName

    private var sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val accelerometerReading = FloatArray(3)
    private val magnetometerReading = FloatArray(3)
    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)

    companion object{

        private var instance: Orientation? = null
        private var orientationListener: OnOrientationChanged? = null

        fun start(context: Context){
            instance = if(instance == null) Orientation(context) else instance
            instance?.start()
        }

        fun registerListener(orientation: OnOrientationChanged){
            this.orientationListener = orientation
        }

        fun notifyListener(current: FloatArray?){
            orientationListener?.onOrientationChanged(current)
        }

        fun unregisterListener(){
            this.orientationListener = null
        }
    }

    private fun start(){
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_STATUS_ACCURACY_HIGH)
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_STATUS_ACCURACY_HIGH)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.i(TAG, "onAccuracyChanged")
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, accelerometerReading, 0, accelerometerReading.size)
        } else if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, magnetometerReading, 0, magnetometerReading.size)
        }

        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReading, magnetometerReading)
        SensorManager.getOrientation(rotationMatrix, orientationAngles)

        notifyListener(orientationAngles)
    }
}