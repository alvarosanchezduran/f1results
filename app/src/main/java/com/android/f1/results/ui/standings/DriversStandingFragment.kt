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
import com.android.f1.results.databinding.CurrentSeasonFragmentBinding
import com.android.f1.results.databinding.DriversClasificationFragmentBinding
import com.android.f1.results.db.preferences.F1ResultsPreferences
import com.android.f1.results.di.Injectable
import com.android.f1.results.ui.common.BaseFragment
import com.android.f1.results.ui.home.HomeViewModel
import com.android.f1.results.ui.result.RaceAdapter
import com.android.f1.results.util.autoCleared
import com.android.f1.results.vo.Status
import javax.inject.Inject

class DriversStandingFragment : BaseFragment<DriverStandingAdapter, DriversClasificationFragmentBinding>(R.layout.drivers_clasification_fragment), Injectable {

    @Inject
    lateinit var preferences: F1ResultsPreferences

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
            adapter = DriverStandingAdapter(dataBindingComponent, appExecutors, preferences.getFavoriteDriver() ,{
                adapter.setSelectedResult(it)
            }, {
                adapter.setAllSelected()
            })
            rvDriverStanding.adapter = adapter
        }
    }

    override fun setUpObservers() {
        standingsViewModel.driverStandingRequest.observe(viewLifecycleOwner, { response ->
            showLoading(response.status)
            if(response.status == Status.SUCCESS) {
                response.data?.data?.standingsTable?.standingsLists?.get(0)?.driverStanding?.let {
                    val list = it
                    list.first().selected = true
                    list.forEach {
                        it.calculateGap(list.first().points)
                        it.calculateTeammateGap(list)
                    }
                    adapter.submitList(list)
                }
            }
        })
    }

    override fun setSpinnerVisibility() {}
}