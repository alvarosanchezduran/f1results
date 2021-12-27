package com.android.f1.results.ui.currentseason

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.f1.results.AppExecutors
import com.android.f1.results.MainActivity
import com.android.f1.results.R
import com.android.f1.results.binding.FragmentDataBindingComponent
import com.android.f1.results.databinding.CurrentSeasonFragmentBinding
import com.android.f1.results.databinding.ResultFragmentBinding
import com.android.f1.results.di.Injectable
import com.android.f1.results.ui.common.BaseFragment
import com.android.f1.results.ui.home.HomeViewModel
import com.android.f1.results.ui.common.adapters.StandingsPagerAdapter
import com.android.f1.results.ui.result.ResultPagerAdapter
import com.android.f1.results.ui.result.ResultViewModel
import com.android.f1.results.ui.standings.StandingsViewModel
import com.android.f1.results.util.SpinnerManager
import com.android.f1.results.util.autoCleared
import javax.inject.Inject
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener


class HistoricalSeasonFragment : BaseFragment<Any, CurrentSeasonFragmentBinding>(R.layout.current_season_fragment), Injectable, SpinnerManager {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val standingsViewModel: StandingsViewModel by viewModels {
        viewModelFactory
    }

    private val resultsViewModel: ResultViewModel by viewModels {
        viewModelFactory
    }

    var pagerAdapter: StandingsPagerAdapter? = null

    var positionSelected = 0

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setToolbarTitle("Standings", R.drawable.ic_result_list, {
            resultsViewModel.year.value = standingsViewModel.yearSelected
            findNavController().navigate(R.id.action_HistoricalFragment_to_ResultsFragment)
        })
        if (pagerAdapter != null) {
            Handler().post({
                binding.pager.setAdapter(pagerAdapter)
                binding.pager.setCurrentItem(positionSelected)
            })
        }
    }

    private fun setUpTabs() {
        binding.tabs.addTab(binding.tabs.newTab().setText(R.string.drivers))
        binding.tabs.addTab(binding.tabs.newTab().setText(R.string.constructors))
        activity?.let {
            pagerAdapter = StandingsPagerAdapter(it, it.supportFragmentManager, binding.tabs.getTabCount())
            binding.pager.setAdapter(pagerAdapter)


            binding.pager.addOnPageChangeListener(TabLayoutOnPageChangeListener(binding.tabs))

            binding.tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    binding.pager.setCurrentItem(tab.position)
                    positionSelected = tab.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        (activity as MainActivity).setToolbarTitle(getString(R.string.current_season_title))
        setUpTabs()
        setUpBinding()
        setUpObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        val i = 0
    }

    override fun setUpBinding() {}

    override fun setUpObservers() {}
    override fun onSpinnerChangeItem(year: Int) {
        standingsViewModel.yearSelected = (year - 1).toString()
        standingsViewModel.getStandings()
    }

    override fun setSpinnerVisibility() {
        (activity as MainActivity).setSpinnerToolbarVisibility(this)
    }
}