package com.ucph.mc.dui.ui.fragments

import android.app.Activity
import android.content.Context
import android.os.*
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import butterknife.OnClick

import com.ucph.mc.dui.R
import com.ucph.mc.dui.ui.utils.NavigationManager
import com.ucph.mc.dui.ui.viewmodels.MemoryViewModel
import kotlinx.android.synthetic.main.fragment_memory.*
import kotlinx.android.synthetic.main.fragment_memory.view.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class MemoryFragment : Fragment() {

    lateinit var vibrator: Vibrator
    private lateinit var countDown: CountDownTimer
    private lateinit var taskCountDown: CountDownTimer
    lateinit var vm: MemoryViewModel
    private var iterationsCompleted = 0
    lateinit var startTime: Calendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = getString(R.string.memory_task_title)
        val view = inflater.inflate(R.layout.fragment_memory, container, false)
        ButterKnife.bind(this, view)
        vibrator = (activity as Context).getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        view.sequenceInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                view.sequence.error = null
                if(s.length == 5 && view.sequenceInput.selectionStart == 5){
                    val imm: InputMethodManager =
                        context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                    view.submit.requestFocus()
                }
            }
        })

        vm = ViewModelProviders.of(this).get(MemoryViewModel::class.java)

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
                taskCountDown.start()
            }
        }

        taskCountDown = object : CountDownTimer(7000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (millisUntilFinished <= 6000L && millisUntilFinished >= 1000L) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
                    } else {
                        vibrator.vibrate(500)
                    }
                    number.visibility = View.VISIBLE
                    number.text = "${vm.getRandomNumber()}"
                }else{
                    number.visibility = View.GONE
                }
            }

            override fun onFinish() {
                startTime = Calendar.getInstance()

                sequence.visibility = View.VISIBLE
                submit.visibility = View.VISIBLE
            }
        }

        return view
    }

    override fun onDestroy() {
        vm = ViewModelProviders.of(this).get(MemoryViewModel::class.java)
        super.onDestroy()
    }

    @OnClick(R.id.start)
    fun onClickStart(view:View){
        start.visibility = View.GONE
        countDown.start()
    }

    @OnClick(R.id.next)
    fun onClickNext(view:View){
        fragmentManager?.let {
            NavigationManager.goToWalkingFragment(
                it
            )
        }
    }

    @OnClick(R.id.submit)
    fun onClickSubmit(view:View){
        if (sequenceInput.text.toString().length != 5){
            sequence.error = getString(R.string.input_error)
        }else{
            val endTime = Calendar.getInstance()
            vm.saveInputTime(startTime, endTime)
            vm.saveInputSequence(sequenceInput.text.toString())
            sequence.error = null
            sequenceInput.text = null
            iterationsCompleted ++
            sequence.visibility = View.GONE
            submit.visibility = View.GONE
            if (iterationsCompleted == 3){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    vibrator.vibrate(500)
                }

                vm.setNumberMisses()
                vm.setAverageTime()
                text.visibility = View.VISIBLE
                text.text = getString(R.string.success)
                next.visibility = View.VISIBLE
            }else{
                taskCountDown.start()
            }
        }
    }
}
