package com.android.f1.results.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.android.f1.results.AppExecutors
import com.android.f1.results.MainActivity
import com.android.f1.results.R
import com.android.f1.results.binding.FragmentDataBindingComponent
import com.android.f1.results.databinding.ConstructorsClasificationFragmentBinding
import com.android.f1.results.databinding.DriversClasificationFragmentBinding
import com.android.f1.results.di.Injectable
import com.android.f1.results.ui.common.BaseFragment
import com.android.f1.results.ui.home.HomeViewModel
import com.android.f1.results.util.autoCleared
import com.android.f1.results.vo.Status
import javax.inject.Inject

class ConstructorsStandingFragment : BaseFragment<ConstructorStandingAdapter, ConstructorsClasificationFragmentBinding>(R.layout.constructors_clasification_fragment), Injectable {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val standingsViewModel: StandingsViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        setUpBinding()
        setUpObservers()
    }

    override fun setUpBinding() {
        binding.apply {
            adapter = ConstructorStandingAdapter(dataBindingComponent, appExecutors, {
                adapter.setSelectedResult(it)
            }, {
                adapter.setAllSelected()
            })
            rvConstructorStanding.adapter = adapter
        }
    }

    override fun setUpObservers() {
        standingsViewModel.constructorsStandingRequest.observe(viewLifecycleOwner, { response ->
            showLoading(response.status)
            if(response.status == Status.SUCCESS) {
                response.data?.data?.standingsTable?.standingsLists?.let {
                    if(it.size > 0) {
                        it.get(0)?.constructorStanding?.let {
                            val list = it
                            list.first().selected = true
                            list.forEach {
                                it.calculateGap(list.first().points)
                            }
                            binding.tvNoConstructorStanding.visibility = View.GONE
                            binding.rvConstructorStanding.visibility = View.VISIBLE
                            adapter.submitList(list)
                        }
                    } else  {
                        binding.tvNoConstructorStanding.visibility = View.VISIBLE
                        binding.rvConstructorStanding.visibility = View.GONE
                    }

                }
            }
        })
    }

    override fun setSpinnerVisibility() {}
}