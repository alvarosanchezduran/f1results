package com.android.f1.results.ui.result

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ResultPagerAdapter(context: Context, fm: FragmentManager, totalTabs: Int) : FragmentPagerAdapter(fm) {
    private val myContext: Context
    var totalTabs: Int

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                QualifyingFragment()
            }
            1 -> {
                RaceFragment()
            }
            else -> RaceFragment()
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }

    init {
        myContext = context
        this.totalTabs = totalTabs
    }
}