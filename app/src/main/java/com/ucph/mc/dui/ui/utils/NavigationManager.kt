package com.ucph.mc.dui.ui.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ucph.mc.dui.R
import com.ucph.mc.dui.ui.fragments.*

class NavigationManager {

    companion object {
        private fun placeFragment(fm: FragmentManager, fragment: Fragment) {
            val transition = fm.beginTransaction()
            transition.setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right
            )
            transition.replace(R.id.frame, fragment)
            transition.addToBackStack(null)
            transition.commit()
        }

        fun goToWelcomeFragment(fm : FragmentManager){
            placeFragment(
                fm,
                WelcomeFragment()
            )
        }

        fun goToReactionFragment(fm : FragmentManager){
            placeFragment(
                fm,
                ReactionFragment()
            )
        }

        fun goToMemoryFragment(fm : FragmentManager){
            placeFragment(
                fm,
                MemoryFragment()
            )
        }

        fun goToWalkingFragment(fm : FragmentManager){
            placeFragment(
                fm,
                WalkingFragment()
            )
        }

        fun goToResultsFragment(fm : FragmentManager){
            placeFragment(
                fm,
                ResultsFragment()
            )
        }
    }
}