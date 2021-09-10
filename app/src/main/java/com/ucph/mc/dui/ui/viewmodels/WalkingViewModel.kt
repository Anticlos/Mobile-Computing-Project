package com.ucph.mc.dui.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ucph.mc.dui.domain.walking.WalkingLogic
import kotlin.math.abs

class WalkingViewModel(application: Application): AndroidViewModel(application) {

    private val walkingLogic = WalkingLogic()

    private var yAxisOrientationValues : ArrayList<Double> = ArrayList()
    private var xAxisOrientationValues : ArrayList<Double> = ArrayList()

    fun addYAxisOrientationValue(yAxisOrientationValue: Double){
        yAxisOrientationValues.add(yAxisOrientationValue)
    }

    fun addXAxisOrientationValue(xAxisOrientationValue: Double){
        xAxisOrientationValues.add(xAxisOrientationValue)
    }

    fun calculateAxisOrientationDeviationValues() {
        walkingLogic.calculateAxisOrientationDeviationValues(yAxisOrientationValues, xAxisOrientationValues)
    }

}