package com.android.f1.results.ui.circuits

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingComponent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.f1.results.MainActivity
import com.android.f1.results.R
import com.android.f1.results.binding.FragmentDataBindingComponent
import com.android.f1.results.databinding.CircuitsFragmentBinding
import com.android.f1.results.databinding.DriversFragmentBinding
import com.android.f1.results.databinding.ResultsFragmentBinding
import com.android.f1.results.di.Injectable
import com.android.f1.results.ui.circuits.CircuitsAdapter
import com.android.f1.results.ui.circuits.CircuitsViewModel
import com.android.f1.results.ui.common.BaseFragment
import com.android.f1.results.ui.common.viewmodels.FlagsViewModel
import com.android.f1.results.ui.common.adapters.ResultsAdapter
import com.android.f1.results.ui.result.ResultViewModel
import com.android.f1.results.util.SpinnerManager
import com.android.f1.results.vo.Status
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.DividerItemDecoration

class CircuitsFragment : BaseFragment<CircuitsAdapter, CircuitsFragmentBinding>(R.layout.circuits_fragment), Injectable,
    SpinnerManager {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val circuitsViewModel: CircuitsViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBinding()
        setUpObservers()
        (activity as MainActivity).setToolbarTitle(getString(R.string.circuits_title))
    }

    override fun setUpObservers() {
        circuitsViewModel.circuitsRequest.observe(viewLifecycleOwner, { response ->
            showLoading(response.status)
            if(response.status == Status.SUCCESS) {
                response.data?.data?.circuitTable?.circuits?.let {
                    adapter.submitList(it)
                }
            }
        })
    }

    override fun setUpBinding() {
        binding.apply {
            adapter = CircuitsAdapter(dataBindingComponent, appExecutors) {
                circuitsViewModel.circuit.value = it
                findNavController().navigate(R.id.action_circuitsFragment_to_circuitDetailFragment)
            }
            val dividerItemDecoration = DividerItemDecoration(
                rvCircuits.getContext(),
                DividerItemDecoration.VERTICAL
            )
            rvCircuits.addItemDecoration(dividerItemDecoration)
            rvCircuits.adapter = adapter
        }
    }

    override fun onSpinnerChangeItem(year: Int) {
        circuitsViewModel.year.value = year.toString()
        circuitsViewModel.getCircuitsInfo()
    }

    override fun setSpinnerVisibility() {
        (activity as MainActivity).setSpinnerToolbarVisibility(this, true)
    }
}