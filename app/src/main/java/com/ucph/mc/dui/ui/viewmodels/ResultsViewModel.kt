package com.ucph.mc.dui.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ucph.mc.dui.domain.results.ResultsLogic

class ResultsViewModel(application: Application): AndroidViewModel(application) {

    private val resultsLogic = ResultsLogic()

    fun getMemoryMisses(): Int{
        return resultsLogic.getMemoryMisses()
    }

    fun getAverageDecisionTimeMemory(): Double{
        return resultsLogic.getAverageDecisionTimeMemory()
    }

    fun getYAxisOrientationDeviation(): Double{
        return resultsLogic.getYAxisOrientationDeviation()
    }

    fun getXAxisOrientationDeviation(): Double{
        return resultsLogic.getXAxisOrientationDeviation()
    }
    fun getReactionMisses(): Int{
        return resultsLogic.getReactionMisses()
    }

    fun getAverageDecisionTimeReaction(): Double{
        return resultsLogic.getAverageDecisionTimeReaction()
    }

    fun getPrediction(): Int{
        return resultsLogic.getPrediction()
    }
}