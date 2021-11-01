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
import com.android.f1.results.vo.Race
import com.android.f1.results.vo.Status
import com.bumptech.glide.Glide

class HomeFragment : BaseFragment<LastResultsAdapter, HomeFragmentBinding>(R.layout.home_fragment), Injectable {

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
        homeViewModel.getLastResultsInfo()
        showLoading(true)
    }

    override fun setUpObservers() {
        homeViewModel.lastRaceRequest.observe(viewLifecycleOwner, { response ->
            response.data?.data?.raceTable?.let {
                homeViewModel.lastRace.value = it
                homeViewModel.getRaceInfo()
            }
        })

        homeViewModel.raceRequest.observe(viewLifecycleOwner, { response ->
            response.data?.data?.raceTable?.let {
                homeViewModel.nextRace.value = it.races.get(0)
                homeViewModel.getFlagInfo()
            }
        })

        homeViewModel.flagRequest.observe(viewLifecycleOwner, { response ->
            if(response.status == Status.SUCCESS) {
                context?.let {
                    Glide.with(it)
                        .load(response.data?.get(0)?.flags?.png)
                        .into(binding.ivFlag)
                }
            }
        })

        homeViewModel.lastResultsRequest.observe(viewLifecycleOwner, { response ->
            response.data?.data?.raceTable?.let {
                val listReversed = it.races.asReversed()
                adapter.submitList(listReversed)
                homeViewModel.lastResultsRaces = listReversed.toMutableList()
                homeViewModel.currentIndexFlag = 0
                if(listReversed.size > 0) homeViewModel.getFlagInfo(listReversed.get(homeViewModel.currentIndexFlag).circuit.location.country)
            }
        })

        homeViewModel.flagLastResultsRequest.observe(viewLifecycleOwner, { response ->
            if(response.status == Status.SUCCESS) {
                context?.let {
                    response.data?.get(0)?.let {
                        adapter.setFlag(it, homeViewModel.currentIndexFlag)
                    }
                    homeViewModel.currentIndexFlag++
                    if(homeViewModel.lastResultsRaces.size > homeViewModel.currentIndexFlag) homeViewModel.getFlagInfo(homeViewModel.lastResultsRaces.get(homeViewModel.currentIndexFlag).circuit.location.country)
                    else showLoading(false)
                }
            }
        })
    }

    override fun setUpBinding() {
        binding.viewModel = homeViewModel
        context?.let {
            adapter = LastResultsAdapter(dataBindingComponent, it, appExecutors, {

            })
            binding.rvLastRace.adapter = adapter
        }
    }
}
