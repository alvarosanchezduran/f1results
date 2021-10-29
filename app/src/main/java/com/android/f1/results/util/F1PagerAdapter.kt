package com.android.f1.results.util

import android.content.Context;
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.android.f1.results.ui.clasification.ConstructorsClasificationFragment
import com.android.f1.results.ui.clasification.DriversClasificationFragment


class F1PagerAdapter(context: Context, fm: FragmentManager, totalTabs: Int) : FragmentPagerAdapter(fm) {
    private val myContext: Context
    var totalTabs: Int

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                DriversClasificationFragment()
            }
            1 -> {
                ConstructorsClasificationFragment()
            }
            else -> DriversClasificationFragment()
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