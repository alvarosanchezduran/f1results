package com.android.f1.results.ui.result

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
import com.android.f1.results.R
import com.android.f1.results.binding.FragmentDataBindingComponent
import com.android.f1.results.databinding.ConstructorsClasificationFragmentBinding
import com.android.f1.results.databinding.RaceFragmentBinding
import com.android.f1.results.databinding.ResultFragmentBinding
import com.android.f1.results.di.Injectable
import com.android.f1.results.ui.common.BaseFragment
import com.android.f1.results.ui.home.HomeViewModel
import com.android.f1.results.util.autoCleared
import javax.inject.Inject

class RaceFragment : BaseFragment<RaceAdapter, RaceFragmentBinding>(R.layout.race_fragment), Injectable {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val resultViewModel: ResultViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        setUpBinding()
        setUpObservers()
    }

    override fun setUpBinding() {
        binding.apply {
            adapter = RaceAdapter(dataBindingComponent, appExecutors, context, {
                adapter.setSelectedResult(it)
            }, {
                adapter.setAllSelected()
            })
            if(resultViewModel.race.value?.results?.size?: 0 > 0) resultViewModel.race.value?.results?.get(0)?.selected = true
            adapter.submitList(resultViewModel.race.value?.results)
            rvRaceResult.adapter = adapter
        }
    }

    override fun setUpObservers() {}
}