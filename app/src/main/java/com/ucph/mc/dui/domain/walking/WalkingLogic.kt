package com.ucph.mc.dui.domain.walking

import android.graphics.drawable.GradientDrawable
import android.util.Log
import com.ucph.mc.dui.data.local.list.ListStorage
import org.nield.kotlinstatistics.standardDeviation
import kotlin.math.abs

class WalkingLogic {
    private val storage = ListStorage.getInstance()

    fun calculateAxisOrientationDeviationValues(yAxisOrientationValues: ArrayList<Double>, xAxisOrientationValues: ArrayList<Double>){
        val yAxisOrientationDeviation = yAxisOrientationValues.standardDeviation()
        val xAxisOrientationDeviation = xAxisOrientationValues.standardDeviation()

        saveAxisOrientationDeviationValues(yAxisOrientationDeviation, xAxisOrientationDeviation)
    }

    private fun saveAxisOrientationDeviationValues(yAxisOrientationDeviation: Double, xAxisOrientationDeviation: Double){
        storage.setYAxisOrientationDeviation(yAxisOrientationDeviation)
        storage.setXAxisOrientationDeviation(xAxisOrientationDeviation)
    }
}