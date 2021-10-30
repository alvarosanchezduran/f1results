package com.android.f1.results.ui.home

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingComponent
import androidx.fragment.app.viewModels
import com.android.f1.results.MainActivity
import com.android.f1.results.R
import com.android.f1.results.binding.FragmentDataBindingComponent
import com.android.f1.results.databinding.HomeFragmentBinding
import com.android.f1.results.di.Injectable
import com.android.f1.results.ui.common.BaseFragment
import com.android.f1.results.vo.Status
import com.bumptech.glide.Glide

class HomeFragment : BaseFragment<Any, HomeFragmentBinding>(R.layout.home_fragment), Injectable {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val homeViewModel: HomeViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBinding()
        setUpObservers()
        (activity as MainActivity).setToolbarTitle(getString(R.string.home_title))
        homeViewModel.getLastRaceInfo()
    }

    override fun setUpObservers() {
        homeViewModel.lastRaceRequest.observe(viewLifecycleOwner, { response ->
            response.data?.data?.raceTable?.let {
                homeViewModel.lastRace.value = it
                homeViewModel.getRaceInfo()
            }
        })

        homeViewModel.raceRequest.observe(viewLifecycleOwner, { response ->
            showLoading(response.status)
            response.data?.data?.raceTable?.let {
                homeViewModel.nextRace.value = it.races.get(0)
                homeViewModel.getFlagInfo()
            }
        })

        homeViewModel.flagRequest.observe(viewLifecycleOwner, { response ->
            showLoading(response.status)
            if(response.status == Status.SUCCESS) {
                context?.let {
                    Glide.with(it)
                        .load(response.data?.get(0)?.flags?.png)
                        .into(binding.ivFlag)
                }
            }
        })
    }

    override fun setUpBinding() {
        binding.viewModel = homeViewModel
    }
}
