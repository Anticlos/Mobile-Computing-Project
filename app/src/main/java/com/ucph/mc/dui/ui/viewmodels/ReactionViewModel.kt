package com.ucph.mc.dui.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ucph.mc.dui.domain.reaction.ReactionLogic
import java.util.*
import kotlin.random.Random

class ReactionViewModel(application: Application): AndroidViewModel(application) {

    private val reactionLogic = ReactionLogic()
    private var decisionTimes: ArrayList<Double> = ArrayList()

    fun getRandomWitdh(): Float {
        return reactionLogic.getRandomWitdh()
    }
    fun getRandomHeight(): Float {
        return reactionLogic.getRandomHeight()
    }
    fun setReactionMisses(reactionMisses: Int){
        reactionLogic.setReactionMisses(reactionMisses)
    }
    fun saveInputTime(start: Calendar, end: Calendar){
        val diff = end.timeInMillis - start.timeInMillis
        val microSecond = diff.toString().takeLast(3)
        val second = (diff / 1000) % 60
        var finalTime = microSecond

        if (second > 0) {
            finalTime = "$second.$finalTime"
        }else{
            finalTime = "0.$finalTime"
        }

        decisionTimes.add(finalTime.toDouble())
    }

    fun setAverageTime(){
        reactionLogic.setAverageTime(decisionTimes)
    }

}