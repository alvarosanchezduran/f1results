package com.android.f1.results.ui.drivers

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingComponent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.f1.results.MainActivity
import com.android.f1.results.R
import com.android.f1.results.binding.FragmentDataBindingComponent
import com.android.f1.results.databinding.DriversFragmentBinding
import com.android.f1.results.databinding.ResultsFragmentBinding
import com.android.f1.results.di.Injectable
import com.android.f1.results.ui.common.BaseFragment
import com.android.f1.results.ui.common.viewmodels.FlagsViewModel
import com.android.f1.results.ui.common.adapters.ResultsAdapter
import com.android.f1.results.ui.result.ResultViewModel
import com.android.f1.results.util.SpinnerManager
import com.android.f1.results.vo.Status

class DriversFragment : BaseFragment<DriversAdapter, DriversFragmentBinding>(R.layout.drivers_fragment), Injectable,
    SpinnerManager {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val driversViewModel: DriversViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBinding()
        setUpObservers()
        (activity as MainActivity).setToolbarTitle(getString(R.string.drivers_title))
    }

    override fun setUpObservers() {
        driversViewModel.driversRequest.observe(viewLifecycleOwner, { response ->
            showLoading(response.status)
            if(response.status == Status.SUCCESS) {
                response.data?.data?.raceTable?.drivers?.let {
                    adapter.submitList(it)
                }
            }
        })
    }

    override fun setUpBinding() {
        adapter = DriversAdapter(dataBindingComponent, appExecutors, {})
        binding.apply {
            rvDrivers.adapter = adapter
        }
    }

    override fun onSpinnerChangeItem(year: Int) {
        driversViewModel.year.value = year.toString()
        driversViewModel.getDriversInfo()
    }

    override fun setSpinnerVisibility() {
        (activity as MainActivity).setSpinnerToolbarVisibility(this, true)
    }
}