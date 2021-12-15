package com.android.f1.results.ui.constructors

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingComponent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.f1.results.MainActivity
import com.android.f1.results.R
import com.android.f1.results.binding.FragmentDataBindingComponent
import com.android.f1.results.databinding.ConstructorsFragmentBinding
import com.android.f1.results.databinding.DriversFragmentBinding
import com.android.f1.results.di.Injectable
import com.android.f1.results.ui.common.BaseFragment
import com.android.f1.results.util.SpinnerManager
import com.android.f1.results.vo.Status

class ConstructorsFragment : BaseFragment<ConstructorsAdapter, ConstructorsFragmentBinding>(R.layout.constructors_fragment), Injectable,
    SpinnerManager {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val constructorsViewModel: ConstructorsViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBinding()
        setUpObservers()
        (activity as MainActivity).setToolbarTitle(getString(R.string.constructors_title))
    }

    override fun setUpObservers() {
        constructorsViewModel.constructorsRequest.observe(viewLifecycleOwner, { response ->
            showLoading(response.status)
            if(response.status == Status.SUCCESS) {
                response.data?.data?.constructorTable?.constructors?.let {
                    adapter.submitList(it)
                }
            }
        })
    }

    override fun setUpBinding() {
        adapter = ConstructorsAdapter(dataBindingComponent, appExecutors) {
            constructorsViewModel.constructor.value = it
            findNavController().navigate(R.id.action_ConstructorsFragment_to_ConstructorDetailFragment)
        }
        binding.apply {
            rvConstructors.adapter = adapter
        }
    }

    override fun onSpinnerChangeItem(year: Int) {
        constructorsViewModel.year.value = year.toString()
        constructorsViewModel.getConstructorsInfo()
    }

    override fun setSpinnerVisibility() {
        (activity as MainActivity).setSpinnerToolbarVisibility(this, true)
    }
}