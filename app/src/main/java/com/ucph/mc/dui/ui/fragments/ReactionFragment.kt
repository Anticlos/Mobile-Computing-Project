package com.ucph.mc.dui.ui.fragments

import android.R.color
import android.content.Context
import android.graphics.Color.CYAN
import android.graphics.Color.GREEN
import android.graphics.Color.RED
import android.graphics.Color.MAGENTA
import android.graphics.Color.YELLOW

import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import butterknife.OnClick
import com.ucph.mc.dui.R
import com.ucph.mc.dui.ui.utils.NavigationManager
import com.ucph.mc.dui.ui.viewmodels.ReactionViewModel
import kotlinx.android.synthetic.main.fragment_reaction.*
import kotlinx.android.synthetic.main.fragment_reaction.next
import kotlinx.android.synthetic.main.fragment_reaction.start
import kotlinx.android.synthetic.main.fragment_reaction.text
import kotlinx.android.synthetic.main.fragment_walking.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class ReactionFragment : Fragment() {

    private var count = 0
    private var misses = 0
    lateinit var vm: ReactionViewModel
    lateinit var vibrator: Vibrator
    private lateinit var countDown: CountDownTimer
    private lateinit var timer: CountDownTimer
    private var colors = arrayOf(MAGENTA, GREEN,CYAN, YELLOW,RED)
    lateinit var startTime: Calendar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = getString(R.string.reaction_task_title)
        val view = inflater.inflate(R.layout.fragment_reaction, container, false)
        vm = ViewModelProviders.of(this).get(ReactionViewModel::class.java)
        ButterKnife.bind(this, view)

        vibrator = (activity as Context).getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        countDown = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                text.text = "${getString(R.string.count_down)} ${(millisUntilFinished / 1000 + 1)}"
            }

            override fun onFinish() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    vibrator.vibrate(500)
                }

                text.visibility = View.GONE
                timer.start()

            }
        }



        timer = object : CountDownTimer(1000, 1000) {

            override fun onTick(p0: Long) {

            }
            override fun onFinish() {
                square.setX(vm.getRandomWitdh())
                square.setY(vm.getRandomHeight())
                startTime = Calendar.getInstance()
                square.setBackgroundColor(colors[count])
                start.setVisibility(View.GONE)
                square.setVisibility(View.VISIBLE)
            }
        }

        return view
    }

    override fun onDestroy() {
        vm = ViewModelProviders.of(this).get(ReactionViewModel::class.java)
        super.onDestroy()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @OnClick(R.id.start)
    fun onClickStart(view:View){
        start.visibility = View.GONE
        countDown.start()

    }

    @OnClick(R.id.square)
    fun onClickSquare1(view:View){
        val endTime = Calendar.getInstance()
        vm.saveInputTime(startTime, endTime)
        count++
        if(count<5) {
            square.visibility = View.GONE
            timer.start()

        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vibrator.vibrate(500)
            }
            vm.setReactionMisses(misses)
            vm.setAverageTime()
            square.setVisibility(View.GONE)
            text.visibility = View.VISIBLE
            text.text = getString(R.string.success)
            next.visibility = View.VISIBLE
        }
    }

    @OnClick(R.id.next)
    fun onClickNext(view:View){
        fragmentManager?.let {
            NavigationManager.goToMemoryFragment(
                it
            )
        }
    }
    @OnClick(R.id.layout)
    fun onClickMiss(view:View){
        misses ++

    }

}
