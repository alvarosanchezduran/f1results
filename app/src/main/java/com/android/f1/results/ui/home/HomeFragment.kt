package com.android.f1.results.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.f1.results.AppExecutors
import com.android.f1.results.MainActivity
import com.android.f1.results.R
import com.android.f1.results.binding.FragmentDataBindingComponent
import com.android.f1.results.databinding.HomeFragmentBinding
import com.android.f1.results.di.Injectable
import com.android.f1.results.ui.common.BaseFragment
import com.android.f1.results.ui.common.RetryCallback
import com.android.f1.results.util.autoCleared
import com.bumptech.glide.Glide
import javax.inject.Inject

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
        homeViewModel.getRaceInfo()
    }

    override fun setUpObservers() {
        homeViewModel.raceRequest.observe(viewLifecycleOwner, { response ->
            showLoading(response.status)
            response.data?.data?.raceTable?.races?.get(0)?.let {
                homeViewModel.nextRace.value = it
                context?.let {
                    Glide.with(it)
                        .load("https://flagcdn.com/w640/mx.png")
                        .into(binding.ivFlag)
                }
            }
        })
    }

    override fun setUpBinding() {
        binding.viewModel = homeViewModel
    }
}
