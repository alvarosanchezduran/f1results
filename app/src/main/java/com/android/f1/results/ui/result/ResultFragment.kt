package com.android.f1.results.ui.result

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.databinding.DataBindingComponent
import androidx.fragment.app.viewModels
import com.android.f1.results.MainActivity
import com.android.f1.results.R
import com.android.f1.results.binding.FragmentDataBindingComponent
import com.android.f1.results.databinding.ResultFragmentBinding
import com.android.f1.results.di.Injectable
import com.android.f1.results.ui.common.BaseFragment
import com.google.android.material.tabs.TabLayout

class ResultFragment : BaseFragment<Any, ResultFragmentBinding>(R.layout.result_fragment), Injectable {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var positionSelected = 0

    private val resultViewModel: ResultViewModel by viewModels {
        viewModelFactory
    }

    var pagerAdapter: ResultPagerAdapter? = null

    override fun onResume() {
        super.onResume()
        if (pagerAdapter != null) {
            Handler().post({
                binding.resultsPager.setAdapter(pagerAdapter)
                binding.resultsPager.setCurrentItem(positionSelected)
            })
        }
    }

    private fun setUpTabs() {
        binding.tabs.addTab(binding.tabs.newTab().setText(R.string.qualifying_label))
        binding.tabs.addTab(binding.tabs.newTab().setText(R.string.race_label))
        activity?.let {
            pagerAdapter = ResultPagerAdapter(it, it.supportFragmentManager, binding.tabs.getTabCount())
            binding.resultsPager.setAdapter(pagerAdapter)


            binding.resultsPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabs))

            binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    binding.resultsPager.setCurrentItem(tab.position)
                    positionSelected = tab.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        (activity as MainActivity).setToolbarTitle(getString(R.string.result_title))
        setUpTabs()
        resultViewModel.getQualifying()
    }

    override fun setUpBinding() {}

    override fun setUpObservers() {}
}