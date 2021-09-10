package com.ucph.mc.dui.data.local.list

import RandomForestClassifier.predict
import android.content.res.Resources
import kotlin.random.Random


class ListStorage private constructor() {

    private var yAxisOrientationDeviation = 0.0
    private var xAxisOrientationDeviation = 0.0

    private var memoryMisses = 0
    private var averageDecisionTimeMemory = 0.000

    private var reactionMisses = 0
    private var averageDecisionTimeReaction = 0.000

    private var height = Resources.getSystem().getDisplayMetrics().heightPixels
    private var width = Resources.getSystem().getDisplayMetrics().widthPixels


    companion object {

        private var instance: ListStorage? = null

        fun getInstance(): ListStorage {
            synchronized(this) {
                if (instance == null) {
                    instance =
                        ListStorage()
                }
                return instance as ListStorage
            }
        }

    }
    //WALKING TASK
    fun setYAxisOrientationDeviation(yAxisOrientationDeviation: Double){
        this.yAxisOrientationDeviation = yAxisOrientationDeviation
    }

    fun setXAxisOrientationDeviation(xAxisOrientationDeviation: Double){
        this.xAxisOrientationDeviation = xAxisOrientationDeviation
    }

    fun getYAxisOrientationDeviation(): Double{
        return this.yAxisOrientationDeviation
    }

    fun getXAxisOrientationDeviation(): Double{
        return this.xAxisOrientationDeviation
    }

    //MEMORY TASK
    fun setMemoryMisses(misses: Int){
        this.memoryMisses = misses
    }

    fun getMemoryMisses(): Int{
        return this.memoryMisses
    }

    fun setAverageDecisionTimeMemory(averageTime: Double){
        this.averageDecisionTimeMemory = averageTime
    }

    fun getAverageDecisionTimeMemory(): Double{
        return this.averageDecisionTimeMemory
    }

    //REACTION TASK
    fun getRandomWitdh(): Float {
        val a = this.width/1.3
        return Random.nextInt(0, a.toInt()).toFloat()
    }
    fun getRandomHeight(): Float {
        val a = this.height/1.3
        return Random.nextInt(0, a.toInt()).toFloat()
    }

    fun setReactionMisses(reactionMisses: Int)  {
        this.reactionMisses = reactionMisses
    }

    fun getReactionMisses(): Int {
       return this.reactionMisses
    }

    fun setAverageDecisionTimeReaction(averageTime: Double){
        this.averageDecisionTimeReaction = averageTime
    }

    fun getAverageDecisionTimeReaction(): Double{
        return this.averageDecisionTimeReaction
    }

    //PREDICTION
    fun getPrediction(): Int {

        var results = listOf<Double>(this.averageDecisionTimeReaction, this.reactionMisses.toDouble(), this.averageDecisionTimeMemory,
            this.memoryMisses.toDouble(), this.xAxisOrientationDeviation, this.yAxisOrientationDeviation)

        // Features:
        val features = DoubleArray(6)
        var i = 0
        val l = 6
        while (i < l) {
            features[i] = results[i]
            i++
        }

        // Prediction:
        return predict(features)
    }
}