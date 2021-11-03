package com.android.f1.results.ui.result

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
import com.android.f1.results.AppExecutors
import com.android.f1.results.MainActivity
import com.android.f1.results.R
import com.android.f1.results.binding.FragmentDataBindingComponent
import com.android.f1.results.databinding.CurrentSeasonFragmentBinding
import com.android.f1.results.databinding.HomeFragmentBinding
import com.android.f1.results.databinding.ResultFragmentBinding
import com.android.f1.results.di.Injectable
import com.android.f1.results.ui.common.BaseFragment
import com.android.f1.results.ui.home.HomeViewModel
import com.android.f1.results.ui.home.LastResultsAdapter
import com.android.f1.results.util.F1PagerAdapter
import com.android.f1.results.util.autoCleared
import com.google.android.material.tabs.TabLayout
import javax.inject.Inject

class ResultFragment : BaseFragment<Any, ResultFragmentBinding>(R.layout.result_fragment), Injectable {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val resultViewModel: ResultViewModel by viewModels {
        viewModelFactory
    }

    var pagerAdapter: ResultPagerAdapter? = null

    override fun onResume() {
        super.onResume()
        if (pagerAdapter != null) {
            Handler().post({ binding.pager.setAdapter(pagerAdapter) })
        }
    }

    private fun setUpTabs() {
        binding.tabs.addTab(binding.tabs.newTab().setText(R.string.qualifying_label))
        binding.tabs.addTab(binding.tabs.newTab().setText(R.string.race_label))
        activity?.let {
            pagerAdapter = ResultPagerAdapter(it, it.supportFragmentManager, binding.tabs.getTabCount())
            binding.pager.setAdapter(pagerAdapter)


            binding.pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabs))

            binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    binding.pager.setCurrentItem(tab.position)
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
        resultViewModel.getQualifying()
    }

    override fun setUpBinding() {}

    override fun setUpObservers() {}
}