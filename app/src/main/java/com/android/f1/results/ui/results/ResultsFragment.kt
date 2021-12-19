package com.android.f1.results.ui.results

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingComponent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.android.f1.results.MainActivity
import com.android.f1.results.R
import com.android.f1.results.binding.FragmentDataBindingComponent
import com.android.f1.results.databinding.ResultsFragmentBinding
import com.android.f1.results.di.Injectable
import com.android.f1.results.ui.common.BaseFragment
import com.android.f1.results.ui.common.viewmodels.FlagsViewModel
import com.android.f1.results.ui.common.adapters.ResultsAdapter
import com.android.f1.results.ui.result.ResultViewModel
import com.android.f1.results.vo.Status

class ResultsFragment : BaseFragment<ResultsAdapter, ResultsFragmentBinding>(R.layout.results_fragment), Injectable {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val flagsViewModel: FlagsViewModel by viewModels {
        viewModelFactory
    }

    private val resultViewModel: ResultViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBinding()
        setUpObservers()
        (activity as MainActivity).setToolbarTitle(getString(R.string.results_title) + " " + resultViewModel.getYearSelected())
        flagsViewModel.currentIndexFlag = 0
        resultViewModel.getResultsInfo()
        showLoading(true)
    }

    override fun onResume() {
        super.onResume()
        flagsViewModel.currentIndexFlag = 0
    }

    override fun setUpObservers() {
        resultViewModel.resultsRequest.observe(viewLifecycleOwner, { response ->
            response.data?.data?.raceTable?.let {
                val listReversed = it.races.asReversed()
                adapter.submitList(listReversed)
                flagsViewModel.lastResultsRaces = listReversed.toMutableList()
                flagsViewModel.currentIndexFlag = 0
                if (listReversed.size > 0) flagsViewModel.getFlagInfoLastResults(
                    listReversed.get(
                        flagsViewModel.currentIndexFlag
                    ).circuit.location.country
                )
            }
        })

        flagsViewModel.flagLastResultsRequest.observe(viewLifecycleOwner, { response ->
            if (response.status == Status.SUCCESS) {
                context?.let {
                    if(adapter.currentList.size > 0) {
                        if (response.data?.size ?: 0 > 0) {
                            response.data?.get(0)?.let {
                                adapter.setFlag(it, flagsViewModel.currentIndexFlag)
                            }
                            flagsViewModel.currentIndexFlag++
                            if (flagsViewModel.lastResultsRaces.size > flagsViewModel.currentIndexFlag) flagsViewModel.getFlagInfoLastResults(
                                flagsViewModel.lastResultsRaces.get(flagsViewModel.currentIndexFlag).circuit.location.country
                            )
                            else showLoading(false)
                        }
                    }
                }
            }

        })
    }

    override fun setUpBinding() {
        context?.let {
            adapter = ResultsAdapter(dataBindingComponent, it, appExecutors, {
                resultViewModel.race.value = it
                findNavController().navigate(R.id.action_ResultsFragment_to_ResultFragment)
            })
            binding.rvRaceResults.adapter = adapter
            val dividerItemDecoration = DividerItemDecoration(
                binding.rvRaceResults.getContext(),
                DividerItemDecoration.VERTICAL
            )
            binding.rvRaceResults.addItemDecoration(dividerItemDecoration)
        }
    }
}