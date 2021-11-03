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
import com.android.f1.results.di.Injectable
import com.android.f1.results.ui.home.HomeViewModel
import com.android.f1.results.util.F1PagerAdapter
import com.android.f1.results.util.autoCleared
import com.google.android.material.tabs.TabLayout
import javax.inject.Inject

class ResultFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var appExecutors: AppExecutors

    var binding by autoCleared<CurrentSeasonFragmentBinding>()
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val homeViewModel: HomeViewModel by viewModels {
        viewModelFactory
    }
    var adapter: F1PagerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<CurrentSeasonFragmentBinding>(
            inflater,
            R.layout.current_season_fragment,
            container,
            false,
            dataBindingComponent
        )

        binding = dataBinding

        return dataBinding.root
    }

    override fun onResume() {
        super.onResume()
        if (adapter != null) {
            Handler().post({ binding.pager.setAdapter(adapter) })
        }
    }

    private fun setUpTabs() {
        binding.tabs.addTab(binding.tabs.newTab().setText(R.string.qualifying_label))
        binding.tabs.addTab(binding.tabs.newTab().setText(R.string.race_label))
        activity?.let {
            adapter = F1PagerAdapter(it, it.supportFragmentManager, binding.tabs.getTabCount())
            binding.pager.setAdapter(adapter)


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
    }
}