package com.android.f1.results.ui.circuits.detail

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingComponent
import androidx.fragment.app.viewModels
import com.android.f1.results.MainActivity
import com.android.f1.results.R
import com.android.f1.results.binding.FragmentDataBindingComponent
import com.android.f1.results.databinding.CircuitDetailFragmentBinding
import com.android.f1.results.di.Injectable
import com.android.f1.results.ui.circuits.CircuitsViewModel
import com.android.f1.results.ui.common.BaseFragment
import com.android.f1.results.ui.common.viewmodels.FlagsViewModel
import com.android.f1.results.vo.Driver
import com.android.f1.results.vo.DriverTotalWins
import com.android.f1.results.vo.Status
import com.bumptech.glide.Glide
import com.google.mlkit.nl.translate.Translator

class CircuitDetailFragment : BaseFragment<Any, CircuitDetailFragmentBinding>(R.layout.circuit_detail_fragment),
    Injectable {

    var translator: Translator? = null

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val circuitViewModel: CircuitsViewModel by viewModels {
        viewModelFactory
    }

    private val flagsViewModel: FlagsViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBinding()
        setUpObservers()
        (activity as MainActivity).setToolbarTitle(circuitViewModel.circuit.value?.circuitName?: "")
        flagsViewModel.getFlagInfo(circuitViewModel.circuit.value?.location?.country?: "")
        circuitViewModel.getCircuitInfo()
    }

    override fun setUpObservers() {
        flagsViewModel.flagRequest.observe(viewLifecycleOwner, { response ->
            if(response.status == Status.SUCCESS) {
                if (response.data?.size ?: 0 > 0) {
                    context?.let {
                        Glide.with(it)
                            .load(response.data?.get(0)?.flags?.png)
                            .into(binding.ivFlag)
                    }
                }
            }
            showLoading(
                flagsViewModel.flagRequest.value?.status?: Status.LOADING == Status.LOADING ||
                        circuitViewModel.circuitTotalGPRequest.value?.status?: Status.LOADING == Status.LOADING
            )
        })

        circuitViewModel.circuitTotalGPRequest.observe(viewLifecycleOwner, { response ->
            if(response.status == Status.SUCCESS) {
                binding.tvTotalGp.text = response.data?.data?.total
                response.data?.data?.raceTable?.races?.let { races ->
                    var mostWinnedDrivers = mutableMapOf<Driver, Int>()
                    (binding.rvRaces.adapter as LastWinnersAdapter).submitList(races.reversed())
                    races.forEach { race ->
                        var winneds = mostWinnedDrivers.get(race.getWinner())
                        if(winneds == null) {
                            mostWinnedDrivers.put(race.getWinner()!!, 1)
                        } else {
                            mostWinnedDrivers.set(race.getWinner()!!, winneds + 1)
                        }
                    }
                    val list = mutableListOf<DriverTotalWins>()
                    mostWinnedDrivers.keys.forEach { driver ->
                        list.add(DriverTotalWins(driver, mostWinnedDrivers.get(driver)!!))
                    }
                    list.sortByDescending { it.total }
                    (binding.rvWinners.adapter as TotalWinnsAdapter).submitList(list)
                }
            }
            showLoading(
                flagsViewModel.flagRequest.value?.status?: Status.LOADING == Status.LOADING ||
                        circuitViewModel.circuitTotalGPRequest.value?.status?: Status.LOADING == Status.LOADING
            )
        })
    }

    override fun setUpBinding() {
        binding.apply {
            viewModel = circuitViewModel
            rvRaces.adapter = LastWinnersAdapter(dataBindingComponent, appExecutors) {}
            rvWinners.adapter = TotalWinnsAdapter(dataBindingComponent, appExecutors) {}
        }
    }
}