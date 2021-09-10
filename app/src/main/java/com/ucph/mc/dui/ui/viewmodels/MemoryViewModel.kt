package com.ucph.mc.dui.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.ucph.mc.dui.domain.memory.MemoryLogic
import java.util.*
import kotlin.collections.ArrayList

class MemoryViewModel(application: Application): AndroidViewModel(application) {

    private val memoryLogic = MemoryLogic()

    private var numberSequence: ArrayList<String> = ArrayList()
    private var inputSequence: ArrayList<String> = ArrayList()
    private var decisionTimes: ArrayList<Double> = ArrayList()

    fun getRandomNumber(): String{
        val num = memoryLogic.generateNumber()
        numberSequence.add(num)
        return num
    }

    fun saveInputSequence(input: String){
        for (element in input) {
            inputSequence.add(element.toString())
        }
    }

    fun setNumberMisses(){
        memoryLogic.setNumberMisses(numberSequence, inputSequence)
    }

    fun saveInputTime(start: Calendar, end: Calendar){
        val diff = end.timeInMillis - start.timeInMillis
        val microSecond = diff.toString().takeLast(3)
        val second = (diff / 1000) % 60
        var finalTime = microSecond

        if (second > 0) {
            finalTime = "$second.$finalTime"
        }

        decisionTimes.add(finalTime.toDouble())
    }

    fun setAverageTime(){
        memoryLogic.setAverageTime(decisionTimes)
    }


}