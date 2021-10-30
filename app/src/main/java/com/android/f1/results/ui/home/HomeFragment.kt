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
import com.android.f1.results.ui.common.RetryCallback
import com.android.f1.results.util.autoCleared
import javax.inject.Inject

class HomeFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var appExecutors: AppExecutors

    var binding by autoCleared<HomeFragmentBinding>()
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val homeViewModel: HomeViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<HomeFragmentBinding>(
            inflater,
            R.layout.home_fragment,
            container,
            false,
            dataBindingComponent
        )
        dataBinding.retryCallback = object : RetryCallback {
            override fun retry() {
                homeViewModel.retry()
            }
        }
        binding = dataBinding

        return dataBinding.root
    }

    private fun setUpObservers() {
        homeViewModel.raceRequest.observe(viewLifecycleOwner, { response ->
            val responseFinal = response.data?.data?.raceTable?.season
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        (activity as MainActivity).setToolbarTitle(getString(R.string.home_title))
        setUpObservers()
        homeViewModel.getRaceInfo()
    }
}
