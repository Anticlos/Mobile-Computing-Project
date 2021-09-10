package com.ucph.mc.dui.data.sensors.orientation

import android.hardware.SensorEvent

interface OnOrientationChanged {

    fun onOrientationChanged(event: FloatArray?)
}