package com.ucph.mc.dui.ui.fragments

import android.content.Context
import android.hardware.SensorEvent
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import butterknife.OnClick
import com.ucph.mc.dui.R
import com.ucph.mc.dui.data.sensors.orientation.OnOrientationChanged
import com.ucph.mc.dui.data.sensors.orientation.Orientation
import com.ucph.mc.dui.ui.utils.NavigationManager
import com.ucph.mc.dui.ui.viewmodels.ReactionViewModel
import com.ucph.mc.dui.ui.viewmodels.WalkingViewModel
import kotlinx.android.synthetic.main.fragment_walking.*


/**
 * A simple [Fragment] subclass.
 */
class WalkingFragment : Fragment(), OnOrientationChanged {

    lateinit var vibrator: Vibrator
    private lateinit var countDown: CountDownTimer
    private lateinit var taskCountDown: CountDownTimer
    lateinit var vm: WalkingViewModel
    private var taskActive = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        activity?.title = getString(R.string.walking_task_title)
        val view = inflater.inflate(R.layout.fragment_walking, container, false)
        ButterKnife.bind(this, view)
        vibrator = (activity as Context).getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vm = ViewModelProviders.of(this).get(WalkingViewModel::class.java)

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
                taskActive = true
                taskCountDown.start()
            }
        }

        taskCountDown = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (millisUntilFinished == 5000L){
                    text.text = "${(millisUntilFinished / 1000)} ${getString(R.string.task_count_down)}"
                }else{
                    text.text = "${(millisUntilFinished / 1000) + 1} ${getString(R.string.task_count_down)}"
                }
            }

            override fun onFinish() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    vibrator.vibrate(500)
                }
                taskActive = false
                vm.calculateAxisOrientationDeviationValues()
                text.text = getString(R.string.success)
                next.visibility = View.VISIBLE
            }
        }

        return view
    }

    override fun onStart() {
        Orientation.start(activity as Context)
        Orientation.registerListener(this)
        super.onStart()
    }

    override fun onDestroyView() {
        Orientation.unregisterListener()
        super.onDestroyView()
    }

    override fun onDestroy() {
        vm = ViewModelProviders.of(this).get(WalkingViewModel::class.java)
        super.onDestroy()
    }

    override fun onOrientationChanged(event: FloatArray?) {
        if(taskActive){
            event?.get(2)?.toDouble()?.let { Math.toDegrees(it) }?.let {
                vm.addYAxisOrientationValue(
                    it
                )
            }
            event?.get(1)?.toDouble()?.let { Math.toDegrees(it) }?.let {
                vm.addXAxisOrientationValue(
                    it
                )
            }
        }
    }

    @OnClick(R.id.start)
    fun onClickStart(view:View){
        start.visibility = View.GONE
        countDown.start()
    }

    @OnClick(R.id.next)
    fun onClickNext(view:View){
        fragmentManager?.let {
            NavigationManager.goToResultsFragment(
                it
            )
        }
    }

}
