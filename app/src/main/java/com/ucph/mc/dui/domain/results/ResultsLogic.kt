package com.ucph.mc.dui.domain.results

import com.ucph.mc.dui.data.local.list.ListStorage

class ResultsLogic {
    private val storage = ListStorage.getInstance()

    fun getMemoryMisses(): Int{
        return storage.getMemoryMisses()
    }

    fun getAverageDecisionTimeMemory(): Double{
        return storage.getAverageDecisionTimeMemory()
    }

    fun getYAxisOrientationDeviation(): Double{
        return storage.getYAxisOrientationDeviation()
    }

    fun getXAxisOrientationDeviation(): Double{
        return storage.getXAxisOrientationDeviation()
    }
    fun getReactionMisses(): Int{
        return storage.getReactionMisses()
    }

    fun getAverageDecisionTimeReaction(): Double{
        return storage.getAverageDecisionTimeReaction()
    }

    fun getPrediction(): Int{
        return storage.getPrediction()
    }

}