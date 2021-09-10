package com.ucph.mc.dui.domain.memory

import com.ucph.mc.dui.data.local.list.ListStorage

class MemoryLogic {
    private val storage = ListStorage.getInstance()

    fun generateNumber(): String{
        return (0..9).random().toString()
    }

    fun setNumberMisses(correctSequence: ArrayList<String>, inputSequence: ArrayList<String>){
        var misses = 0
        for (i in 0 until correctSequence.size){
            if (correctSequence[i] != inputSequence[i]){
                misses++
            }
        }
        storage.setMemoryMisses(misses)
    }

    fun setAverageTime(decisionTimes: ArrayList<Double>){
        storage.setAverageDecisionTimeMemory(decisionTimes.average())
    }
}