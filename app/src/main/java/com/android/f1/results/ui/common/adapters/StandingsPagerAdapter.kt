package com.android.f1.results.ui.common.adapters

import android.content.Context;
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.android.f1.results.ui.standings.ConstructorsStandingFragment
import com.android.f1.results.ui.standings.DriversStandingFragment


class StandingsPagerAdapter(context: Context, fm: FragmentManager, totalTabs: Int) : FragmentPagerAdapter(fm) {
    private val myContext: Context
    var totalTabs: Int

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                DriversStandingFragment()
            }
            1 -> {
                ConstructorsStandingFragment()
            }
            else -> DriversStandingFragment()
        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }

    init {
        myContext = context
        this.totalTabs = totalTabs
    }
}