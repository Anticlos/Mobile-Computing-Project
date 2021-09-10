package com.ucph.mc.dui.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife

import com.ucph.mc.dui.R
import com.ucph.mc.dui.ui.viewmodels.ResultsViewModel
import kotlinx.android.synthetic.main.fragment_results.*
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * A simple [Fragment] subclass.
 */
class ResultsFragment : Fragment() {

    lateinit var vm: ResultsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = getString(R.string.results_title)
        val view = inflater.inflate(R.layout.fragment_results, container, false)
        ButterKnife.bind(this, view)
        vm = ViewModelProviders.of(this).get(ResultsViewModel::class.java)
        return view
    }

    override fun onStart() {
        //WALKING TASK
        xaxis.text = "${getString(R.string.xaxis_text)} ${BigDecimal(vm.getXAxisOrientationDeviation()).setScale(2, RoundingMode.HALF_EVEN)} ${getString(R.string.degrees)}"
        yaxis.text = "${getString(R.string.yaxis_text)} ${BigDecimal(vm.getYAxisOrientationDeviation()).setScale(2, RoundingMode.HALF_EVEN)} ${getString(R.string.degrees)}"

        //MEMORY TASK
        incorrect.text = "${getString(R.string.incorrect_number)} ${vm.getMemoryMisses()}"
        averageDecisionMemory.text = "${getString(R.string.avg_decision_time)} ${BigDecimal(vm.getAverageDecisionTimeMemory()).setScale(2, RoundingMode.HALF_EVEN)} ${getString(R.string.seconds)}"

        //REACTION TASK
        misses.text = "${getString(R.string.misses_text)} ${vm.getReactionMisses()}"
        averageDecisionReaction.text = "${getString(R.string.avg_reaction_time)} ${BigDecimal(vm.getAverageDecisionTimeReaction()).setScale(2, RoundingMode.HALF_EVEN)} ${getString(R.string.seconds)}"

        var pred = vm.getPrediction()
        if (pred == 0){
            prediction.text = "${getString(R.string.safe)}"
        } else if (pred == 1){
            prediction.text = "${getString(R.string.inconc)}"
        } else{
            prediction.text = "${getString(R.string.unsafe)}"
        }

        super.onStart()
    }

    override fun onDestroy() {
        vm = ViewModelProviders.of(this).get(ResultsViewModel::class.java)
        super.onDestroy()
    }

}
