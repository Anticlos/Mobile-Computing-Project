package com.ucph.mc.dui.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.OnClick

import com.ucph.mc.dui.R
import com.ucph.mc.dui.ui.utils.NavigationManager

/**
 * A simple [Fragment] subclass.
 */
class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = getString(R.string.app_name)
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    @OnClick(R.id.start)
    fun onClickStartTest(view: View) {
        fragmentManager?.let {
            NavigationManager.goToReactionFragment(
                it
            )
        }
    }

}
