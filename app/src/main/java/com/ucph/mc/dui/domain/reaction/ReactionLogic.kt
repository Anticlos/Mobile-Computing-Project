package com.ucph.mc.dui.domain.reaction

import com.ucph.mc.dui.data.local.list.ListStorage
import java.util.*

class ReactionLogic {
    private val storage = ListStorage.getInstance()


    fun getRandomWitdh(): Float {
        return storage.getRandomWitdh()
    }
    fun getRandomHeight(): Float {
        return storage.getRandomHeight()
    }
    fun setReactionMisses(reactionMisses: Int){
        storage.setReactionMisses(reactionMisses)
    }

    fun setAverageTime(decisionTimes: ArrayList<Double>){
        storage.setAverageDecisionTimeReaction(decisionTimes.average())
    }

}