package com.android.f1.results.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.f1.results.MainActivity
import com.android.f1.results.R
import com.android.f1.results.binding.FragmentDataBindingComponent
import com.android.f1.results.databinding.HomeFragmentBinding
import com.android.f1.results.di.Injectable
import com.android.f1.results.ui.common.BaseFragment
import com.android.f1.results.ui.common.viewmodels.FlagsViewModel
import com.android.f1.results.ui.result.ResultViewModel
import com.android.f1.results.vo.Race
import com.android.f1.results.vo.Status
import com.bumptech.glide.Glide

class HomeFragment : BaseFragment<LastResultsAdapter, HomeFragmentBinding>(R.layout.home_fragment), Injectable {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val homeViewModel: HomeViewModel by viewModels {
        viewModelFactory
    }

    private val flagsViewModel: FlagsViewModel by viewModels {
        viewModelFactory
    }

    private val resultViewModel: ResultViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBinding()
        setUpObservers()
        (activity as MainActivity).setToolbarTitle(getString(R.string.home_title))
        flagsViewModel.currentIndexFlag = 0
        homeViewModel.getLastRaceInfo()
        homeViewModel.getLastResultsInfo()
        showLoading(true)
    }

    override fun onResume() {
        super.onResume()
        flagsViewModel.currentIndexFlag = 0
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
                flagsViewModel.getFlagInfo(it.races.get(0).circuit.location.country)
            }
        })

        flagsViewModel.flagRequest.observe(viewLifecycleOwner, { response ->
            if (response.status == Status.SUCCESS) {
                if (response.data?.size ?: 0 > 0) {
                    context?.let {
                        Glide.with(it)
                            .load(response.data?.get(0)?.flags?.png)
                            .into(binding.ivFlag)
                    }
                }
            }
        })

        homeViewModel.lastResultsRequest.observe(viewLifecycleOwner, { response ->
            response.data?.data?.raceTable?.let {
                val listReversed = it.races.asReversed()
                adapter.submitList(listReversed)
                flagsViewModel.lastResultsRaces = listReversed.toMutableList()
                flagsViewModel.currentIndexFlag = 0
                if (listReversed.size > 0) flagsViewModel.getFlagInfoLastResults(
                    listReversed.get(
                        flagsViewModel.currentIndexFlag
                    ).circuit.location.country
                )
            }
        })

        flagsViewModel.flagLastResultsRequest.observe(viewLifecycleOwner, { response ->
            if (response.status == Status.SUCCESS) {
                context?.let {
                    if(adapter.currentList.size > 0) {
                        if (response.data?.size ?: 0 > 0) {
                            response.data?.get(0)?.let {
                                adapter.setFlag(it, flagsViewModel.currentIndexFlag)
                            }
                            flagsViewModel.currentIndexFlag++
                            if (flagsViewModel.lastResultsRaces.size > flagsViewModel.currentIndexFlag) flagsViewModel.getFlagInfoLastResults(
                                flagsViewModel.lastResultsRaces.get(flagsViewModel.currentIndexFlag).circuit.location.country
                            )
                            else showLoading(false)
                        }
                    }
                }
            }

        })
    }

    override fun setUpBinding() {
        binding.viewModel = homeViewModel
        context?.let {
            adapter = LastResultsAdapter(dataBindingComponent, it, appExecutors, {
                resultViewModel.race.value = it
                findNavController().navigate(R.id.action_HomeFragment_to_ResultFragment)
            })
            binding.rvLastRace.adapter = adapter
        }
    }
}
