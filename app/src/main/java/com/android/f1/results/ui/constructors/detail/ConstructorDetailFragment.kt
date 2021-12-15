package com.android.f1.results.ui.constructors.detail

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingComponent
import androidx.fragment.app.viewModels
import com.android.f1.results.MainActivity
import com.android.f1.results.R
import com.android.f1.results.binding.FragmentDataBindingComponent
import com.android.f1.results.di.Injectable
import com.android.f1.results.ui.common.BaseFragment
import java.util.*
import com.android.f1.results.databinding.ConstructorDetailFragmentBinding
import com.android.f1.results.ui.constructors.ConstructorsViewModel
import com.android.f1.results.util.ConstructorsColors
import com.android.f1.results.vo.Status


class ConstructorDetailFragment : BaseFragment<ConstructorDetailDriversAdapter, ConstructorDetailFragmentBinding>(R.layout.constructor_detail_fragment),
    Injectable {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val constructorViewModel: ConstructorsViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBinding()
        setUpObservers()
        (activity as MainActivity).setToolbarTitle(constructorViewModel.constructor.value?.name?: "")
        constructorViewModel.constructor.value?.let {
            ConstructorsColors.getConstructorColorSaved(it.constructorId)?.let {
                binding.clConstructorInfo.setBackgroundResource(it)
            }?: run {
                val color = ConstructorsColors.getConstructorColorProvisional(it.constructorId)
                binding.clConstructorInfo.setBackgroundColor(color)
            }
        }
        constructorViewModel.getConstructorTotalGP()
    }

    override fun setUpObservers() {
        constructorViewModel.totalGPWinnedRequest.observe(viewLifecycleOwner, { response ->
            if(response.status == Status.SUCCESS) {
                binding.tvTotalGpWinned.text = response.data?.data?.total?: ""
            }
            showLoading(
                constructorViewModel.totalGPWinnedRequest.value?.status?: Status.LOADING == Status.LOADING ||
                        constructorViewModel.totalGPChampionshipsRequest.value?.status?: Status.LOADING == Status.LOADING
            )
        })

        constructorViewModel.totalGPChampionshipsRequest.observe(viewLifecycleOwner, { response ->
            if(response.status == Status.SUCCESS) {
                binding.tvTotalNumberChampionship.text = response.data?.data?.total?: ""
            }
            showLoading(
                constructorViewModel.totalGPWinnedRequest.value?.status?: Status.LOADING == Status.LOADING ||
                        constructorViewModel.totalGPChampionshipsRequest.value?.status?: Status.LOADING == Status.LOADING
            )
        })
    }

    override fun setUpBinding() {
        binding.apply {
            viewModel = constructorViewModel
            adapter = ConstructorDetailDriversAdapter(dataBindingComponent, appExecutors) {

            }
            rvConstructors.adapter = adapter
        }
    }
}